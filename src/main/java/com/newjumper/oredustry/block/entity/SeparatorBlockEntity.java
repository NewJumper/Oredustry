package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.block.CustomBlockStateProperties;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import com.newjumper.oredustry.screen.SeparatorMenu;
import com.newjumper.oredustry.util.OredustryEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SeparatorBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    public static final int ENERGY_CAPACITY = 1000;

    private final LazyOptional<IItemHandler> lazyItemHandler;
    private final LazyOptional<IEnergyStorage> lazyEnergyStorage;
    public final ItemStackHandler itemHandler;
    public final OredustryEnergyStorage energyStorage;
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
    private int fuel;
    private int maxFuel;
    private int progress;
    private int maxProgress;
    private final RecipeType<SeparatingRecipe> recipeType;

    public SeparatorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.SEPARATOR.get(), pWorldPosition, pBlockState);

        this.recipeType = SeparatingRecipe.Type.INSTANCE;
        this.itemHandler = new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        this.energyStorage = new OredustryEnergyStorage(ENERGY_CAPACITY, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public Component getName() {
        return null;
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
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("energy", energyStorage.serializeNBT());
        pTag.putInt("separator.fuel", this.fuel);
        pTag.putInt("separator.maxFuel", this.maxFuel);
        pTag.putInt("separator.progress", this.progress);
        pTag.putInt("separator.maxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energyStorage.deserializeNBT(pTag.get("energy"));
        this.fuel = pTag.getInt("separator.fuel");
        this.maxFuel = pTag.getInt("separator.maxFuel");
        this.progress = pTag.getInt("separator.progress");
        this.maxProgress = pTag.getInt("separator.maxProgress");
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return lazyItemHandler.cast();
        if(cap == CapabilityEnergy.ENERGY) return lazyEnergyStorage.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SeparatorBlockEntity pBlockEntity) {
        SimpleContainer container = new SimpleContainer(pBlockEntity.itemHandler.getSlots());
        for (int i = 0; i < pBlockEntity.itemHandler.getSlots(); i++) {
            container.setItem(i, pBlockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<SeparatingRecipe> recipe = pLevel.getRecipeManager().getRecipeFor(pBlockEntity.recipeType, container, pLevel);
        recipe.ifPresent(separatingRecipe -> pBlockEntity.maxProgress = separatingRecipe.getTime());

        if(pBlockEntity.energyStorage.getEnergyStored() < pBlockEntity.energyStorage.getMaxEnergyStored()) {
            pBlockEntity.energyStorage.addEnergy(1);
        }

        if(pBlockEntity.isActive()) pBlockEntity.fuel--;
        if(canSeparate(container, recipe) && !pBlockEntity.isActive()) {
            int constant = pBlockEntity.getFuelCapacity(pBlockEntity.itemHandler.getStackInSlot(0)) / 200;
            pBlockEntity.maxFuel = pBlockEntity.maxProgress * constant;
            pBlockEntity.fuel = pBlockEntity.maxFuel;
            pBlockEntity.itemHandler.extractItem(0, 1, false);
        }

        if(canSeparate(container, recipe) && pBlockEntity.isActive() && pBlockEntity.energyStorage.getEnergyStored() > 2) {
            pBlockEntity.progress++;
            pBlockEntity.energyStorage.consumeEnergy(2);

            if(pBlockEntity.progress == pBlockEntity.maxProgress) {
                pBlockEntity.itemHandler.extractItem(1,1, false);
                // TODO: WHEN CHANGING RESULT SLOT INDEX, REMEMBER TO CHANGE THESE VALUES TOO ------> ----------> ---------------------> ------------------------> V
                pBlockEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem().getItem(), pBlockEntity.itemHandler.getStackInSlot(2).getCount() + recipe.get().getResultItem().getCount()));
                pBlockEntity.itemHandler.setStackInSlot(3, new ItemStack(recipe.get().getResultBlock().getItem(), pBlockEntity.itemHandler.getStackInSlot(3).getCount() + recipe.get().getResultBlock().getCount()));

                pBlockEntity.progress = 0;
            }
        }

        if(pBlockEntity.itemHandler.getStackInSlot(1).isEmpty()) pBlockEntity.progress = 0;

        setChanged(pLevel, pPos, pState);
    }

    private boolean isActive() {
        return this.fuel > 0;
    }

    private static boolean canSeparate(SimpleContainer container, Optional<SeparatingRecipe> recipe) {
        return recipe.isPresent() && validOutput(container, recipe.get().getResultItem(), 2) && validOutput(container, recipe.get().getResultBlock(), 3);
    }

    private static boolean validOutput(SimpleContainer container, ItemStack stack, int slot) {
        return (container.getItem(slot).getItem() == stack.getItem() || container.getItem(slot).isEmpty()) && (container.getItem(slot).getCount() < container.getItem(slot).getMaxStackSize());
    }

    private int getFuelCapacity(ItemStack stack) {
        if(stack.isEmpty()) return 0;
        else return ForgeHooks.getBurnTime(stack, this.recipeType);
    }
}
