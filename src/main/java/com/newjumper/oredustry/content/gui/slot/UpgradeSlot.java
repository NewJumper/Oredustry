package com.newjumper.oredustry.content.gui.slot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class UpgradeSlot extends SlotItemHandler {
    private final Item upgradeItem;
    private boolean active;

    public UpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item upgradeItem) {
        super(itemHandler, index, xPosition, yPosition);
        this.upgradeItem = upgradeItem;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return stack.is(upgradeItem);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
