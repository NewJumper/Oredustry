package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.CrucibleBlockEntity;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.screen.slot.CrucibleResultSlot;
import com.newjumper.oredustry.screen.slot.UpgradeSlot;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class CrucibleMenu extends AbstractContainerMenu {
    private static final int INV_SLOTS = 36;
    public static final int MENU_SLOTS = 6;
    public final CrucibleBlockEntity blockEntity;
    private final ContainerData data;
    private final Level level;
    public List<Slot> upgradeSlots = new ArrayList<>();

    public CrucibleMenu(int containerId, Inventory inventory, FriendlyByteBuf buffer) {
        this(containerId, inventory, inventory.player.level().getBlockEntity(buffer.readBlockPos()), new SimpleContainerData(7));
    }

    public CrucibleMenu(int pContainerId, Inventory pInventory, BlockEntity pBlockEntity, ContainerData pContainerData) {
        super(OredustryMenuTypes.CRUCIBLE_MENU.get(), pContainerId);
        this.blockEntity = (CrucibleBlockEntity) pBlockEntity;
        this.level = pInventory.player.level();
        this.data = pContainerData;

        checkContainerSize(pInventory, MENU_SLOTS);
        addInventorySlots(pInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 22, 50) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return ForgeHooks.getBurnTime(stack, null) > 0;
                }
            });
            this.addSlot(new SlotItemHandler(handler, 1, 43, 50) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.is(Tags.Items.ORES) || stack.is(Tags.Items.RAW_MATERIALS) || stack.is(OredustryTags.Items.DENSE_MATERIALS);
                }
            });
            this.addSlot(new SlotItemHandler(handler, 2, 86, 50) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return stack.is(Items.WATER_BUCKET);
                }
            });
            this.addSlot(new CrucibleResultSlot(blockEntity, pInventory.player, handler, 3, 133, 35));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 4, 176, 25, OredustryItems.FUEL_UPGRADE.get())));
            upgradeSlots.add(this.addSlot(new UpgradeSlot(handler, 5, 176, 43, OredustryItems.SPEED_UPGRADE.get())));
        });

        addDataSlots(pContainerData);
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
                blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(fluidHandler -> {
                    int waterStored = ((FluidTank)fluidHandler).getFluidAmount() & 0xffff0000;
                    fluidHandler.drain(CrucibleBlockEntity.WATER_CAPACITY, IFluidHandler.FluidAction.EXECUTE);
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
                blockEntity.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(fluidHandler -> {
                    int waterStored = ((FluidTank)fluidHandler).getFluidAmount() & 0x0000ffff;
                    fluidHandler.drain(CrucibleBlockEntity.WATER_CAPACITY, IFluidHandler.FluidAction.EXECUTE);
                    fluidHandler.fill(new FluidStack(Fluids.WATER, waterStored | (pValue << 16)), IFluidHandler.FluidAction.EXECUTE);
                });
            }
        });
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
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer, OredustryBlocks.CRUCIBLE.get());
    }

    public int getLiquid() {
        return this.data.get(6);
    }

    public int getWater() {
        return this.blockEntity.fluidTank.getFluidAmount();
    }

    public int drawFuel() {
        int fuel = this.data.get(0);
        int max = this.data.get(1);

        return fuel == 0 ? -1 : Math.max(14 * (max - fuel) / max, 0);
    }

    public int drawProgress() {
        int progress = this.data.get(2);
        int max = this.data.get(3);

        return progress == 0 ? 0 : 39 * progress / max % 13 + 1;
    }

    public int drawCoolingProgress() {
        int progress = this.data.get(4);
        int max = this.data.get(5);

        return progress == 0 || max == 0 ? 0 : 37 * progress / max;
    }

    public int drawLiquid() {
        return getLiquid() == 0 ? 0 : Math.max(44 * getLiquid() / CrucibleBlockEntity.LIQUID_CAPACITY, 1);
    }

    public int drawWater() {
        return getWater() == 0 ? 0 : Math.max(16 * getWater() / blockEntity.fluidTank.getCapacity(), 1);
    }
}
