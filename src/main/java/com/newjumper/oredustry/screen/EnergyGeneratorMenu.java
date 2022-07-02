package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.CustomBlockStateProperties;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.CustomEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class EnergyGeneratorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    private static final int SLOTS = 3;
    private final BlockEntity blockEntity;
    private final Level level;

    public EnergyGeneratorMenu(int pContainerId, BlockPos pPos, Inventory pInventory, Player pPlayer) {
        super(OredustryMenuTypes.ENERGY_GENERATOR_MENU.get(), pContainerId);
        this.blockEntity = pPlayer.level.getBlockEntity(pPos);
        this.level = pPlayer.getLevel();

        checkContainerSize(pInventory, SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 17, 52));
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 35, 52));
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 53, 52));
        });

        trackPower();
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);

        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();

        if (pIndex < INV_SLOTS) {
            if (!moveItemStackTo(sourceStack, INV_SLOTS, INV_SLOTS + SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        } else if (pIndex < INV_SLOTS + SLOTS) {
            if (!moveItemStackTo(sourceStack, 0, INV_SLOTS, false)) {
                return ItemStack.EMPTY;
            }
        } else return ItemStack.EMPTY;

        if (sourceStack.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(pPlayer, sourceStack);
        return sourceStack;
    }

    private void addInventorySlots(Inventory pInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(pInventory, j + i * 9 + 9, 8 + j * 18, 98 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(pInventory, i, 8 + i * 18, 156));
        }
    }


    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), playerIn, OredustryBlocks.ENERGY_GENERATOR.get());
    }

    private void trackPower() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(storage -> {
                    int energyStored = storage.getEnergyStored() & 0xffff0000;
                    ((CustomEnergyStorage)storage).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(storage -> {
                    int energyStored = storage.getEnergyStored() & 0x0000ffff;
                    ((CustomEnergyStorage)storage).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }
    public int drawEnergy() {
        return (int) (getEnergy() / 50000.0 * 44);
    }
    public boolean isLit() {
        return this.blockEntity.getBlockState().getValue(CustomBlockStateProperties.ACTIVE);
    }
}
