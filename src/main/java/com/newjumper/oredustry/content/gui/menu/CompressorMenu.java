package com.newjumper.oredustry.content.gui.menu;

import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.OredustryItems;
import com.newjumper.oredustry.content.OredustryMenuTypes;
import com.newjumper.oredustry.content.blocks.entity.CompressorBlockEntity;
import com.newjumper.oredustry.content.gui.slot.CompressorResultSlot;
import com.newjumper.oredustry.content.gui.slot.UpgradeSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class CompressorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    public static final int MENU_SLOTS = 5;
    public final CompressorBlockEntity blockEntity;
    private final ContainerData data;
    private final Level level;
    public List<Slot> upgradeSlots = new ArrayList<>();

    public CompressorMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(4));
    }

    public CompressorMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.COMPRESSOR_MENU.get(), pContainerId);
        this.blockEntity = (CompressorBlockEntity) pBlockEntity;
        this.level = pInventory.player.level();
        this.data = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 37, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return ForgeHooks.getBurnTime(stack, null) > 0;
                }
            });
            this.addSlot(new SlotItemHandler(handler, 1, 64, 35) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.ORES);
                }
            });
            this.addSlot(new CompressorResultSlot(blockEntity, pInventory.player, handler, 2, 118, 35));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 3, 176, 25, OredustryItems.FUEL_UPGRADE.get())));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 4, 176, 43, OredustryItems.SPEED_UPGRADE.get())));
        });

        addDataSlots(pContainerData);
    }

    private void addInventorySlots(Inventory inventory) {
        for(int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.COMPRESSOR.get());
    }

    public int drawFuel() {
        int fuel = this.data.get(0);
        int max = this.data.get(1);

        return fuel == 0 ? -1 : Math.max(14 * (max - fuel) / max, 0);
    }

    public int drawProgress() {
        int progress = this.data.get(2);
        int max = this.data.get(3);

        return progress == 0 ? 0 : 20 * progress / max;
    }

    public int drawPress() {
        int progress = this.data.get(2);
        return progress == 0 ? 0 : 11 * (progress % 20 + 1) / 20;
    }
}
