package com.newjumper.oredustry.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum MoltenLiquids {
    COAL("molten_coal", Items.COAL, 70),
    IRON("molten_iron", Items.IRON_INGOT, 120),
    COPPER("molten_copper", Items.COPPER_INGOT, 120),
    GOLD("molten_gold", Items.GOLD_INGOT, 120),
    REDSTONE("molten_redstone", Items.REDSTONE, 90),
    EMERALD("molten_emerald", Items.EMERALD, 90),
    LAPIS("molten_lapis", Items.LAPIS_LAZULI, 90),
    DIAMOND("molten_diamond", Items.DIAMOND, 150);

    private final String name;
    private final Item solidItem;
    private final int capacity;

    MoltenLiquids(String name, Item solidItem, int capacity) {
        this.name = name;
        this.solidItem = solidItem;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public ItemStack getSolidItem(int count) {
        return new ItemStack(solidItem, count);
    }

    public int getCapacity() {
        return capacity;
    }

    public static MoltenLiquids getLiquid(ItemStack stack) {
        for(MoltenLiquids liquids : values()) {
            if(liquids.getSolidItem(1).is(stack.getItem())) return liquids;
        }

        return COAL;
    }
}
