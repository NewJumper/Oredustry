package com.newjumper.oredustry.content.gui.menu;

import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.OredustryItems;
import com.newjumper.oredustry.content.OredustryMenuTypes;
import com.newjumper.oredustry.content.blocks.entity.MinerBlockEntity;
import com.newjumper.oredustry.content.gui.slot.UpgradeSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class MinerMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    public static final int MENU_SLOTS = 30;
    public final MinerBlockEntity blockEntity;
    public final ContainerData data;
    private final Level level;
    public List<Slot> upgradeSlots = new ArrayList<>();

    public MinerMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(5));
    }

    public MinerMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.MINER_MENU.get(), pContainerId);
        this.blockEntity = (MinerBlockEntity) pBlockEntity;
        this.level = pInventory.player.level();
        this.data = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 0, 204, 25, OredustryItems.RANGE_UPGRADE.get())));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 1, 204, 43, OredustryItems.SPEED_UPGRADE.get())));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 2, 204, 61, OredustryItems.STORAGE_UPGRADE.get())));
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < (MENU_SLOTS - 3) / 3; j++) {
                    this.addSlot(new SlotItemHandler(handler, i * 9 + j + 3, 22 + j * 18, 96 + i * 18));
                }
            }
        });

        addDataSlots(pContainerData);
    }

    private void addInventorySlots(Inventory inventory) {
        for(int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 22 + i * 18, 222));
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 22 + j * 18, 164 + i * 18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if(!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceItem = sourceSlot.getItem();
        if(pIndex < INV_SLOTS) {
            if(!moveItemStackTo(sourceItem, INV_SLOTS, INV_SLOTS + MENU_SLOTS, false)) return ItemStack.EMPTY;
        } else if(pIndex < INV_SLOTS + MENU_SLOTS) {
            if(!moveItemStackTo(sourceItem, 0, INV_SLOTS, false)) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;

        if(sourceItem.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(pPlayer, sourceItem);
        return sourceItem;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.MINER.get());
    }

    public void setState(int value) {
        this.data.set(0, value);
    }

    public int drawProgress() {
        int progress = this.data.get(1);
        int bar = 90 * progress / this.data.get(3);

        return progress == 0 ? 0 : bar;
    }
}
