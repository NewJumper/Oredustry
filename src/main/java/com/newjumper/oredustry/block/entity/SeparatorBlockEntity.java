package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.recipe.SeparatingRecipe;
import com.newjumper.oredustry.screen.SeparatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SeparatorBlockEntity extends BlockEntity implements MenuProvider {
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final ItemStackHandler itemHandler;
    private int litTime;
    private int maxLitTime;
    private int currentProgress;
    private int maxProgress;
    private final RecipeType<SeparatingRecipe> recipeType;
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> SeparatorBlockEntity.this.litTime;
                case 1 -> SeparatorBlockEntity.this.maxLitTime;
                case 2 -> SeparatorBlockEntity.this.currentProgress;
                case 3 -> SeparatorBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> SeparatorBlockEntity.this.litTime = value;
                case 1 -> SeparatorBlockEntity.this.maxLitTime = value;
                case 2 -> SeparatorBlockEntity.this.currentProgress = value;
                case 3 -> SeparatorBlockEntity.this.maxProgress = value;
            }
        }

        public int getCount() {
            return 4;
        }
    };

    public SeparatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.SEPARATOR.get(), pWorldPosition, pBlockState);

        this.recipeType = SeparatingRecipe.Type.INSTANCE;
        this.itemHandler = new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.data.set(3, 200);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.oredustry.separator");
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory, Player pPlayer) {
        return new SeparatorMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("separator.litTime", this.litTime);
        nbt.putInt("separator.maxLitTime", this.maxLitTime);
        nbt.putInt("separator.currentProgress", this.currentProgress);
        nbt.putInt("separator.maxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        this.litTime = nbt.getInt("separator.litTime");
        this.maxLitTime = nbt.getInt("separator.maxLitTime");
        this.currentProgress = nbt.getInt("separator.currentProgress");
        this.maxProgress = nbt.getInt("separator.maxProgress");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @javax.annotation.Nullable Direction facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SeparatorBlockEntity pBlockEntity) {
        if(pBlockEntity.isLit()) pBlockEntity.litTime--;
        if(canSeparate(pBlockEntity) && !pBlockEntity.isLit()) {
            int constant = pBlockEntity.getBurnDuration(pBlockEntity.itemHandler.getStackInSlot(0)) / 200;
            pBlockEntity.maxLitTime = pBlockEntity.maxProgress * constant;
            pBlockEntity.litTime = pBlockEntity.maxLitTime;
            pBlockEntity.itemHandler.extractItem(0, 1, false);
        }

        if(canSeparate(pBlockEntity) && pBlockEntity.isLit()) {
            pBlockEntity.currentProgress++;
            setChanged(pLevel, pPos, pState);

            if(pBlockEntity.currentProgress >= pBlockEntity.maxProgress) {
                Level level = pBlockEntity.level;
                SimpleContainer inventory = new SimpleContainer(pBlockEntity.itemHandler.getSlots());
                for (int i = 0; i < pBlockEntity.itemHandler.getSlots(); i++) {
                    inventory.setItem(i, pBlockEntity.itemHandler.getStackInSlot(i));
                }

                Optional<SeparatingRecipe> match = level.getRecipeManager().getRecipeFor(pBlockEntity.recipeType, inventory, level);
                if(match.isPresent()) {
                    pBlockEntity.itemHandler.extractItem(1,1, false);
                    pBlockEntity.itemHandler.setStackInSlot(2, new ItemStack(match.get().getResultItem().getItem(), pBlockEntity.itemHandler.getStackInSlot(2).getCount() + 2));
                    pBlockEntity.itemHandler.setStackInSlot(3, new ItemStack(match.get().getResultOre().getItem(), pBlockEntity.itemHandler.getStackInSlot(3).getCount() + 1));
                    pBlockEntity.currentProgress = 0;
                }
            }
        } else {
            pBlockEntity.currentProgress = 0;
            setChanged(pLevel, pPos, pState);
        }
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private static boolean canSeparate(SeparatorBlockEntity pBlockEntity) {
        Level level = pBlockEntity.level;
        SimpleContainer inventory = new SimpleContainer(pBlockEntity.itemHandler.getSlots());
        for (int i = 0; i < pBlockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pBlockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<SeparatingRecipe> match = level.getRecipeManager().getRecipeFor(pBlockEntity.recipeType, inventory, level);
        return match.isPresent() && hasValidOutput(inventory, match.get().getResultItem(), match.get().getResultOre());
    }

    private static boolean hasValidOutput(SimpleContainer pContainer, ItemStack pResultOre, ItemStack pResultRaw) {
        return ((pContainer.getItem(2).getItem() == pResultOre.getItem() || pContainer.getItem(2).isEmpty()) && (pContainer.getItem(3).getItem() == pResultRaw.getItem() || pContainer.getItem(3).isEmpty())) &&
                ((pContainer.getItem(2).getCount() < pContainer.getItem(2).getMaxStackSize()) && (pContainer.getItem(3).getCount() < pContainer.getItem(3).getMaxStackSize()));
    }

    private int getBurnDuration(ItemStack pFuel) {
        if (pFuel.isEmpty()) return 0;
        else return ForgeHooks.getBurnTime(pFuel, this.recipeType);
    }
}
