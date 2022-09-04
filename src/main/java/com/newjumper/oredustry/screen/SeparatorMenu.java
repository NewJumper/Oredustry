package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.SeparatorBlockEntity;
import com.newjumper.oredustry.screen.slot.ResultSlot;
import com.newjumper.oredustry.util.OredustryEnergyStorage;
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

public class SeparatorMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    private static final int MENU_SLOTS = 4;
    public final SeparatorBlockEntity blockEntity;
    private final ContainerData containerData;
    private final Level level;

    public SeparatorMenu(int pContainerId, Inventory pInventory, FriendlyByteBuf pBuffer) {
        this(pContainerId, pInventory, pInventory.player.level.getBlockEntity(pBuffer.readBlockPos()), new SimpleContainerData(4));
    }

    public SeparatorMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.SEPARATOR_MENU.get(), pContainerId);
        this.blockEntity = (SeparatorBlockEntity) pBlockEntity;
        this.level = pInventory.player.level;
        this.containerData = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 30, 63) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return ForgeHooks.getBurnTime(stack, null) > 0;
                }
            });
//            this.addSlot(new SlotItemHandler(handler, 1, 146, 63));
            this.addSlot(new SlotItemHandler(handler, 1, 58, 42) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.ORES);
                }
            });
            this.addSlot(new ResultSlot(handler, 2, 114, 29));
            this.addSlot(new ResultSlot(handler, 3, 114, 55));
        });

        addDataSlots(pContainerData);
        saveData();
    }

    private void saveData() {
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int pValue) {
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage -> {
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
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(energyStorage -> {
                    int energyStored = energyStorage.getEnergyStored() & 0x0000ffff;
                    ((OredustryEnergyStorage)energyStorage).setEnergy(energyStored | (pValue << 16));
                });
            }
        });
    }

    private void addInventorySlots(Inventory pInventory) {
        for(int i = 0; i < 9; i++) {
            this.addSlot(new Slot(pInventory, i, 16 + i * 18, 156));
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.SEPARATOR.get());
    }

    private int getEnergy() {
        return blockEntity.energyStorage.getEnergyStored();
    }

    public int drawProgress() {
        double current = this.containerData.get(2);
        int max = this.containerData.get(3);

        return current == 0 ? 0 : (int) (current / max * 24);
    }
    public int drawFuel() {
        int amount = this.containerData.get(0);
        int max = this.containerData.get(1);

        return amount == 0 ? -1 : Math.max((-54 * (amount - max)) / max, 0);
    }
    public int drawEnergy() {
        return getEnergy() == 0 ? 0 : Math.max((int) (getEnergy() / (double) blockEntity.energyStorage.getMaxEnergyStored() * 54), 1);
    }
}
