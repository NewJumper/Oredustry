package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.CustomBlockStateProperties;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.EnergyGeneratorBlockEntity;
import com.newjumper.oredustry.util.OredustryEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class EnergyGeneratorMenu extends AbstractContainerMenu {
    private static final int SLOTS = 3;
    public final EnergyGeneratorBlockEntity blockEntity;
    private final Level level;
    private Slot waterSlot;
    private Slot gasSlot;
    private Slot energySlot;

    public EnergyGeneratorMenu(int pContainerId, BlockPos pPos, Inventory pInventory, Player pPlayer) {
        super(OredustryMenuTypes.ENERGY_GENERATOR_MENU.get(), pContainerId);
        this.blockEntity = (EnergyGeneratorBlockEntity) pPlayer.level.getBlockEntity(pPos);
        this.level = pPlayer.getLevel();

        checkContainerSize(pInventory, SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
            this.waterSlot = this.addSlot(new SlotItemHandler(itemHandler, 0, 21, 55) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(Items.WATER_BUCKET);
                }
            });
            this.gasSlot = this.addSlot(new SlotItemHandler(itemHandler, 1, 39, 55));
            this.energySlot = this.addSlot(new SlotItemHandler(itemHandler, 2, 57, 55));
        });

        saveData();
    }

    private void saveData() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getWater() & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(fluidHandler -> {
                    int waterStored = ((FluidTank)fluidHandler).getFluidAmount() & 0xffff0000;
                    fluidHandler.drain(EnergyGeneratorBlockEntity.WATER_CAPACITY, IFluidHandler.FluidAction.EXECUTE);
                    fluidHandler.fill(new FluidStack(Fluids.WATER, waterStored + (pValue & 0xffff)), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getWater() >> 16) & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(fluidHandler -> {
                    int waterStored = ((FluidTank)fluidHandler).getFluidAmount() & 0x0000ffff;
                    fluidHandler.drain(EnergyGeneratorBlockEntity.WATER_CAPACITY, IFluidHandler.FluidAction.EXECUTE);
                    fluidHandler.fill(new FluidStack(Fluids.WATER, waterStored | (pValue << 16)), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        });

        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
                    int energyStored = energyStorage.getEnergyStored() & 0xffff0000;
                    ((OredustryEnergyStorage)energyStorage).setEnergy(energyStored + (pValue & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyStorage -> {
                    int energyStored = energyStorage.getEnergyStored() & 0x0000ffff;
                    ((OredustryEnergyStorage)energyStorage).setEnergy(energyStored | (pValue << 16));
                });
            }
        });
    }

    private void addInventorySlots(Inventory pInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(pInventory, i, 16 + i * 18, 156));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(pInventory, j + i * 9 + 9, 16 + j * 18, 98 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if(sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        if(pIndex < 36) {
            if(!moveItemStackTo(sourceStack, 36, 36 + SLOTS, false)) return ItemStack.EMPTY;
        } else if(pIndex < 36 + SLOTS) {
            if(!moveItemStackTo(sourceStack, 0, 36, false)) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;

        if(sourceStack.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(pPlayer, sourceStack);
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.ENERGY_GENERATOR.get());
    }

    private int getWater() {
        return this.blockEntity.fluidTank.getFluidAmount();
    }
    private int getEnergy() {
        return blockEntity.energyStorage.getEnergyStored();
    }

    public boolean isActive() {
        return this.blockEntity.getBlockState().getValue(CustomBlockStateProperties.ACTIVE);
    }
    public int drawWater() {
        return getWater() == 0 ? 0 : Math.max((int) (getWater() / (double) blockEntity.fluidTank.getCapacity() * 42), 1);
    }
    public int drawEnergy() {
        return getEnergy() == 0 ? 0 : Math.max((int) (getEnergy() / (double) blockEntity.energyStorage.getMaxEnergyStored() * 54), 1);
    }

    public Slot getSlotAt(int index) {
        return switch(index) {
            case 0 -> waterSlot;
            case 1 -> gasSlot;
            case 2 -> energySlot;
            default -> throw new IllegalStateException("Unexpected index at slot " + index);
        };
    }
}
