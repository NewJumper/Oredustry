package com.newjumper.oredustry.util;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OredustryCreativeTab {
    public static final CreativeModeTab OREDUSTRY = new CreativeModeTab("oredustry") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(OredustryBlocks.CRUCIBLE.get());
        }
    };
}
