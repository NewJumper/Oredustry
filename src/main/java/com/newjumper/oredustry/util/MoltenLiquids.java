package com.newjumper.oredustry.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum MoltenLiquids {
    COAL(Items.COAL, 70),
    IRON(Items.IRON_INGOT, 120),
    COPPER(Items.COPPER_INGOT, 120),
    GOLD(Items.GOLD_INGOT, 120),
    REDSTONE(Items.REDSTONE, 90),
    EMERALD(Items.EMERALD, 90),
    LAPIS(Items.LAPIS_LAZULI, 90),
    DIAMOND(Items.DIAMOND, 150);

    private final Item solidItem;
    private final int coolRate;

    MoltenLiquids(Item solidItem, int coolRate) {
        this.solidItem = solidItem;
        this.coolRate = coolRate;
    }

    public ItemStack getSolidItem(int count) {
        return new ItemStack(solidItem, count);
    }

    public int getCoolRate() {
        return coolRate;
    }

    public static MoltenLiquids getLiquid(ItemStack stack) {
        for(MoltenLiquids liquids : values()) {
            if(liquids.getSolidItem(1).sameItem(stack)) return liquids;
        }

        return COAL;
    }
}
