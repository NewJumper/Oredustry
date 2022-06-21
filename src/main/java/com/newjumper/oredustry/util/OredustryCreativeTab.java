package com.newjumper.oredustry.util;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class OredustryCreativeTab {
    public static final CreativeModeTab OREDUSTRY = new CreativeModeTab("oredustry") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(OredustryBlocks.HEAT_GENERATOR.get());
        }
    };
}
