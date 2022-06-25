package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.screen.EnergyGeneratorMenu;
import com.newjumper.oredustry.util.CustomEnergyStorage;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class EnergyGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyStorage = LazyOptional.empty();
    private final ItemStackHandler itemHandler;
    private final CustomEnergyStorage energyStorage;
    private int generate;

    public EnergyGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.ENERGY_GENERATOR.get(), pPos, pBlockState);

        this.itemHandler = new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        this.energyStorage = new CustomEnergyStorage(50000, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.oredustry.energy_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new EnergyGeneratorMenu(pContainerId, this.getBlockPos(), pPlayerInventory, pPlayer);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("energy", energyStorage.serializeNBT());
        pTag.putInt("storage", generate);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        energyStorage.deserializeNBT(pTag.getCompound("energy"));
        generate = pTag.getInt("storage");
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return lazyItemHandler.cast();
        if (cap == CapabilityEnergy.ENERGY) return lazyEnergyStorage.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
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

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyGeneratorBlockEntity pBlockEntity) {
        if(pBlockEntity.generate > 0) {
            pBlockEntity.energyStorage.addEnergy(60);
            pBlockEntity.generate--;
            setChanged(pLevel, pPos, pState);
        }

        if(pBlockEntity.generate < 1) {
            if(pLevel.hasNeighborSignal(pPos)) {
                pBlockEntity.itemHandler.extractItem(0, 1, false);
                pBlockEntity.generate = 80;
                setChanged(pLevel, pPos, pState);
            }
        }

        sendOutPower(pBlockEntity);
    }

    private static void sendOutPower(EnergyGeneratorBlockEntity pBlockEntity) {
        AtomicInteger capacity = new AtomicInteger(pBlockEntity.energyStorage.getEnergyStored());

        if (capacity.get() > 0) {
            for (Direction direction : Direction.values()) {
                BlockEntity be = pBlockEntity.level.getBlockEntity(pBlockEntity.worldPosition.relative(direction));

                if (be != null) {
                    boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                        if (handler.canReceive()) {
                            int received = handler.receiveEnergy(Math.min(capacity.get(), 200), false);
                            capacity.addAndGet(-received);
                            pBlockEntity.energyStorage.consumeEnergy(received);
                            setChanged(pBlockEntity.level, pBlockEntity.getBlockPos(), pBlockEntity.getBlockState());
                            return capacity.get() > 0;
                        } else {
                            return true;
                        }
                    }).orElse(true);

                    if (!doContinue) return;
                }
            }
        }
    }
}
