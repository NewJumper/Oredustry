package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.screen.CrucibleMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class CrucibleBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> CrucibleBlockEntity.this.fuel;
                case 1 -> CrucibleBlockEntity.this.maxFuel;
                case 2 -> CrucibleBlockEntity.this.progress;
                case 3 -> CrucibleBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> CrucibleBlockEntity.this.fuel = value;
                case 1 -> CrucibleBlockEntity.this.maxFuel = value;
                case 2 -> CrucibleBlockEntity.this.progress = value;
                case 3 -> CrucibleBlockEntity.this.maxProgress = value;
            }
        }

        public int getCount() {
            return 4;
        }
    };
    private final LazyOptional<IItemHandler> lazyItemHandler;
    public final ItemStackHandler itemHandler;
    private int fuel;
    private int maxFuel;
    private int progress;
    private int maxProgress;

    public CrucibleBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.CRUCIBLE.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.CRUCIBLE.getId().getPath());
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrucibleMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fuel", this.fuel);
        pTag.putInt("maxFuel", this.maxFuel);
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.fuel = pTag.getInt("fuel");
        this.maxFuel = pTag.getInt("maxFuel");
        this.progress = pTag.getInt("progress");
        this.maxProgress = pTag.getInt("maxProgress");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();

        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<BlastingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.BLASTING, inventory, level);
        recipe.ifPresent(blastingRecipe -> blockEntity.maxProgress = blastingRecipe.getCookingTime());

        if(blockEntity.isActive()) blockEntity.fuel--;

        if(canSmelt(inventory, recipe) && !blockEntity.isActive()) {
            double constant = blockEntity.getFuelCapacity(blockEntity.itemHandler.getStackInSlot(0)) / 200.0;
            blockEntity.maxFuel = (int) (blockEntity.maxProgress * constant);
            blockEntity.fuel = blockEntity.maxFuel;
            blockEntity.itemHandler.extractItem(0, 1, false);
        }

        if(canSmelt(inventory, recipe) && blockEntity.isActive()) {
            blockEntity.progress++;
            if(blockEntity.progress == blockEntity.maxProgress) {
                blockEntity.itemHandler.extractItem(1,1, false);
                blockEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(), blockEntity.itemHandler.getStackInSlot(2).getCount() + recipe.get().getResultItem().getCount()));

                blockEntity.progress = 0;
            }
        }

        if(blockEntity.itemHandler.getStackInSlot(1).isEmpty()) blockEntity.progress = 0;
        setChanged(level, pos, state);
    }

    private static boolean canSmelt(SimpleContainer inventory, Optional<BlastingRecipe> recipe) {
        int output = inventory.getContainerSize() - 1;
        return recipe.isPresent() && validOutput(inventory, recipe.get().getResultItem(), output - 1);
    }

    private static boolean validOutput(SimpleContainer container, ItemStack stack, int slot) {
        return (container.getItem(slot).getItem() == stack.getItem() || container.getItem(slot).isEmpty()) && (container.getItem(slot).getCount() < container.getItem(slot).getMaxStackSize());
    }

    private boolean isActive() {
        return this.fuel > 0;
    }

    private int getFuelCapacity(ItemStack stack) {
        return stack.isEmpty() ? 0 : ForgeHooks.getBurnTime(stack, null);
    }
}