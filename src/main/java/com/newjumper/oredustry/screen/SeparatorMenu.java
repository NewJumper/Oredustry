package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.screen.slot.ResultSlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SeparatorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    private static final int MENU_SLOTS = 4;
    private final BlockEntity blockEntity;
    private final ContainerData containerData;
    private final Level level;

    public SeparatorMenu(int pContainerId, Inventory pInventory, FriendlyByteBuf pBuffer) {
        this(pContainerId, pInventory, pInventory.player.level.getBlockEntity(pBuffer.readBlockPos()), new SimpleContainerData(4));
    }

    public SeparatorMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.SEPARATOR_MENU.get(), pContainerId);
        this.blockEntity = pBlockEntity;
        this.level = pInventory.player.level;
        this.containerData = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 50, 17) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return AbstractFurnaceBlockEntity.isFuel(stack);
                }
            });
            this.addSlot(new SlotItemHandler(handler, 1, 50, 35) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.ORES);
                }
            });
            this.addSlot(new ResultSlot(handler, 2, 106, 22));
            this.addSlot(new ResultSlot(handler, 3, 106, 48));
        });

        addDataSlots(pContainerData);
    }

    private void addInventorySlots(Inventory pInventory) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(pInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(pInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        if (pIndex < INV_SLOTS) {
            if (!moveItemStackTo(sourceStack, INV_SLOTS, INV_SLOTS + MENU_SLOTS, false)) return ItemStack.EMPTY;
        } else if (pIndex < INV_SLOTS + MENU_SLOTS) {
            if (!moveItemStackTo(sourceStack, 0, INV_SLOTS, false)) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;

        if (sourceStack.getCount() == 0) sourceSlot.set(ItemStack.EMPTY);
        else sourceSlot.setChanged();

        sourceSlot.onTake(pPlayer, sourceStack);
        return sourceStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.SEPARATOR.get());
    }

    public boolean isLit() {
        return containerData.get(0) > 0;
    }
    public boolean hasIngredients() {
        return containerData.get(2) > 0;
    }
    public int getProgress() {
        int currentProgress = this.containerData.get(2);
        int maxProgress = this.containerData.get(3);
        int progressBarLength = 23;

        return maxProgress != 0 && currentProgress != 0 ? currentProgress * progressBarLength / maxProgress : 0;
    }
    public int getFuelProgress() {
        int litTime = this.containerData.get(0);
        int litDuration = this.containerData.get(1);

        return litDuration != 0 && litTime != 0 ? (-53 * (litTime - litDuration)) / litDuration : 0;
    }
}
