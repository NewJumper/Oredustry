package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.SeparatorBlockEntity;
import com.newjumper.oredustry.screen.slot.ResultSlot;
import com.newjumper.oredustry.screen.slot.SeparatorResultSlot;
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

@SuppressWarnings("NullableProblems")
public class SeparatorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    private static final int MENU_SLOTS = 4;
    public final SeparatorBlockEntity blockEntity;
    private final ContainerData data;
    private final Level level;
    private final Player player;

    public SeparatorMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, inventory.player.level.getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(4));
    }

    public SeparatorMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.SEPARATOR_MENU.get(), pContainerId);
        this.blockEntity = (SeparatorBlockEntity) pBlockEntity;
        this.level = pInventory.player.level;
        this.player = pInventory.player;
        this.data = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 39, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return ForgeHooks.getBurnTime(stack, null) > 0;
                }
            });
            this.addSlot(new SlotItemHandler(handler, 1, 60, 35) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(Tags.Items.ORES);
                }
            });
            this.addSlot(new SeparatorResultSlot(blockEntity, player, handler, 2, 116, 21));
            this.addSlot(new ResultSlot(handler, 3, 116, 49));
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.SEPARATOR.get());
    }

    public int drawFuel() {
        int fuel = this.data.get(0);
        int max = this.data.get(1);

        return fuel == 0 ? -1 : Math.max(14 * (max - fuel) / max, 0);
    }

    public int drawProgress() {
        int progress = this.data.get(2);
        int max = this.data.get(3);

        return progress == 0 ? 0 : 22 * progress / max;
    }
}
