package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.MachineBlock;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import com.newjumper.oredustry.screen.SeparatorMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class SeparatorBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> SeparatorBlockEntity.this.fuel;
                case 1 -> SeparatorBlockEntity.this.maxFuel;
                case 2 -> SeparatorBlockEntity.this.progress;
                case 3 -> SeparatorBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> SeparatorBlockEntity.this.fuel = value;
                case 1 -> SeparatorBlockEntity.this.maxFuel = value;
                case 2 -> SeparatorBlockEntity.this.progress = value;
                case 3 -> SeparatorBlockEntity.this.maxProgress = value;
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

    public SeparatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.SEPARATOR.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.SEPARATOR.getId().getPath());
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new SeparatorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fuel", this.fuel);
        pTag.putInt("maxFuel", this.maxFuel);
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.fuel = pTag.getInt("fuel");
        this.maxFuel = pTag.getInt("maxFuel");
        this.progress = pTag.getInt("progress");
        this.maxProgress = pTag.getInt("maxProgress");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
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

    public static void tick(Level level, BlockPos pos, BlockState state, SeparatorBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<SeparatingRecipe> recipe = level.getRecipeManager().getRecipeFor(SeparatingRecipe.Type.INSTANCE, inventory, level);
        recipe.ifPresent(separatingRecipe -> blockEntity.maxProgress = separatingRecipe.getTime());

        if(blockEntity.isActive()) {
            if(!blockEntity.getBlockState().getValue(MachineBlock.ACTIVE)) {
                state = state.setValue(MachineBlock.ACTIVE, true);
                level.setBlock(pos, state, 3);
            }
            blockEntity.fuel--;
        } else {
            if(blockEntity.getBlockState().getValue(MachineBlock.ACTIVE)) {
                state = state.setValue(MachineBlock.ACTIVE, false);
                level.setBlock(pos, state, 3);
            }
        }

        if(canSeparate(inventory, recipe) && !blockEntity.isActive()) {
            double constant = ForgeHooks.getBurnTime(blockEntity.itemHandler.getStackInSlot(0), null) / 200.0;
            blockEntity.maxFuel = (int) (blockEntity.maxProgress * constant);
            blockEntity.fuel = blockEntity.maxFuel;
            ItemStack fuelRemainder = blockEntity.itemHandler.extractItem(0, 1, false).getCraftingRemainingItem();
            if(!fuelRemainder.isEmpty()) blockEntity.itemHandler.setStackInSlot(0, fuelRemainder);
        }

        if(canSeparate(inventory, recipe) && blockEntity.isActive()) {
            blockEntity.progress++;
            if(blockEntity.progress == blockEntity.maxProgress) {
                blockEntity.itemHandler.extractItem(1, 1, false);
                blockEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(), blockEntity.itemHandler.getStackInSlot(2).getCount() + recipe.get().getResultItem().getCount()));
                blockEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultBlock().getItem(), blockEntity.itemHandler.getStackInSlot(3).getCount() + recipe.get().getResultBlock().getCount()));

                blockEntity.progress = 0;
            }
        }

        if(blockEntity.itemHandler.getStackInSlot(1).isEmpty()) blockEntity.progress = 0;
        setChanged(level, pos, state);
    }

    private static boolean canSeparate(SimpleContainer inventory, Optional<SeparatingRecipe> recipe) {
        int output = inventory.getContainerSize() - 1;
        return recipe.isPresent() && validOutput(inventory, recipe.get().getResultItem(), output - 1) && validOutput(inventory, recipe.get().getResultBlock(), output);
    }

    private static boolean validOutput(SimpleContainer container, ItemStack stack, int slot) {
        return (container.getItem(slot).sameItem(stack) || container.getItem(slot).isEmpty()) && container.getItem(slot).getCount() < container.getItem(slot).getMaxStackSize();
    }

    private boolean isActive() {
        return this.fuel > 0;
    }
}
