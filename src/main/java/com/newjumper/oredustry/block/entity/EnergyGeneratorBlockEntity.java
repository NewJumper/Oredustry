package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.block.CustomBlockStateProperties;
import com.newjumper.oredustry.screen.EnergyGeneratorMenu;
import com.newjumper.oredustry.util.OredustryEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class EnergyGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    public static final int WATER_CAPACITY = 42000;
    public static final int WATER_DRAIN_RATE = 50;

    public static final int ENERGY_CAPACITY = 54000;
    public static final int ENERGY_RATE = 50;
    public static final int ENERGY_OUTPUT = 240;

    private final LazyOptional<IItemHandler> lazyItemHandler;
    private final LazyOptional<IFluidHandler> lazyFluidHandler;
    private final LazyOptional<IEnergyStorage> lazyEnergyStorage;
    public final ItemStackHandler itemHandler;
    public final FluidTank fluidTank;
    public final OredustryEnergyStorage energyStorage;

    public EnergyGeneratorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.ENERGY_GENERATOR.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        this.fluidTank = new FluidTank(WATER_CAPACITY) {
            @Override
            protected void onContentsChanged() {
                setChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };
        this.energyStorage = new OredustryEnergyStorage(ENERGY_CAPACITY, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyFluidHandler = LazyOptional.of(() -> fluidTank);
        this.lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.oredustry.energy_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EnergyGeneratorMenu(pContainerId, this.getBlockPos(), pPlayerInventory, pPlayer);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag = fluidTank.writeToNBT(pTag);
        pTag.put("energy", energyStorage.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        fluidTank.readFromNBT(pTag);
        energyStorage.deserializeNBT(pTag.get("energy"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        if(cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();
        if(cap == ForgeCapabilities.ENERGY) return lazyEnergyStorage.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
        lazyEnergyStorage.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void addWater(int pAmount) {
        fluidTank.fill(new FluidStack(Fluids.WATER, pAmount), IFluidHandler.FluidAction.EXECUTE);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyGeneratorBlockEntity pBlockEntity) {
        if(pBlockEntity.itemHandler.getStackInSlot(0).is(Items.WATER_BUCKET)) {
            pBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
            pBlockEntity.fluidTank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
        }

        if(pBlockEntity.canGenerate()) {
            pBlockEntity.energyStorage.addEnergy(ENERGY_RATE);
            pBlockEntity.fluidTank.drain(WATER_DRAIN_RATE, IFluidHandler.FluidAction.EXECUTE);
        }

        sendOutPower(pBlockEntity, pLevel);
        setChanged(pLevel, pPos, pState);
    }

    private static void sendOutPower(EnergyGeneratorBlockEntity blockEntity, Level level) {
        if(blockEntity.energyStorage.getEnergyStored() > 1) {
            BlockEntity test = level.getBlockEntity(blockEntity.getBlockPos().above());
            if (test instanceof EnergyCableBlockEntity) {
                if(((EnergyCableBlockEntity) test).energyStorage.getEnergyStored() < EnergyCableBlockEntity.ENERGY_CAPACITY) {
                    ((EnergyCableBlockEntity) test).energyStorage.addEnergy(1);
                    blockEntity.energyStorage.consumeEnergy(1);
                }
            }
        }
    }

    private boolean canGenerate() {
        return this.getBlockState().getValue(CustomBlockStateProperties.ACTIVE) && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored() && this.fluidTank.getFluid().getAmount() > 0;
    }
}
