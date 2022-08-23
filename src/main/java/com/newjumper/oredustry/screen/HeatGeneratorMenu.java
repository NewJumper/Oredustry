package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.HeatGeneratorBlockEntity;
import com.newjumper.oredustry.capabilities.OredustryCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class HeatGeneratorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    private static final int MENU_SLOTS = 3;
    public final HeatGeneratorBlockEntity blockEntity;
    private final Level level;
    private Slot fuelSlot;
    private Slot radiatorSlot;
    private Slot upgradeSlot;

    public HeatGeneratorMenu(int pContainerId, BlockPos pPos, Inventory pInventory, Player pPlayer) {
        super(OredustryMenuTypes.HEAT_GENERATOR_MENU.get(), pContainerId);
        this.blockEntity = (HeatGeneratorBlockEntity) pPlayer.level.getBlockEntity(pPos);
        this.level = pPlayer.getLevel();

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.fuelSlot = this.addSlot(new SlotItemHandler(itemHandler, 0, 35, 23));
            this.radiatorSlot = this.addSlot(new SlotItemHandler(itemHandler, 1, 26, 41));
            this.upgradeSlot = this.addSlot(new SlotItemHandler(itemHandler, 2, 44, 41));
        });

        saveData();
    }

    private void saveData() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getHeat() & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(OredustryCapabilities.HEAT).ifPresent(heatStorage -> {
                    int heatStored = heatStorage.getHeatStored() & 0xffff0000;
                    heatStorage.setHeat(heatStored + (pValue & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getHeat() >> 16) & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(OredustryCapabilities.HEAT).ifPresent(heatStorage -> {
                    int heatStored = heatStorage.getHeatStored() & 0x0000ffff;
                    heatStorage.setHeat(heatStored | (pValue << 16));
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
        if(pIndex < INV_SLOTS) {
            if(!moveItemStackTo(sourceStack, INV_SLOTS, INV_SLOTS + MENU_SLOTS, false)) return ItemStack.EMPTY;
        } else if(pIndex < INV_SLOTS + MENU_SLOTS) {
            if(!moveItemStackTo(sourceStack, 0, INV_SLOTS, false)) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;

        if(sourceStack.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(pPlayer, sourceStack);
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.HEAT_GENERATOR.get());
    }

    public int getHeat() {
        return this.blockEntity.heatStorage.getHeatStored();
    }

    public Slot getSlotAt(int index) {
        return switch(index) {
            case 0 -> fuelSlot;
            case 1 -> radiatorSlot;
            case 2 -> upgradeSlot;
            default -> throw new IllegalStateException("Unexpected index at slot " + index);
        };
    }
}
