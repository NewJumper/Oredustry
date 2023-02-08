package com.newjumper.oredustry.util;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class OredustryTags {
    public static class Blocks {
        public static final TagKey<Block> MACHINES = BlockTags.create(new ResourceLocation(Oredustry.MOD_ID, "machines"));
    }

    public static class Items {
        public static final TagKey<Item> DENSE_MATERIALS = modTag("dense_materials");
        public static final TagKey<Item> CELLS = modTag("cells");

        private static TagKey<Item> modTag(String name) {
            return ItemTags.create(new ResourceLocation(Oredustry.MOD_ID, name));
        }
    }
}
