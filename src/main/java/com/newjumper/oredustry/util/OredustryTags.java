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
        public static final TagKey<Block> MACHINES = modTag("machines");
        public static final TagKey<Block> DENSE_ORES = modTag("dense_ores");
        public static final TagKey<Block> DENSE_DEEPSLATE_ORES = modTag("dense_deepslate_ores");

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }

        private static TagKey<Block> modTag(String name) {
            return BlockTags.create(new ResourceLocation(Oredustry.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> PURE_MATERIALS = modTag("pure_materials");

        public static final TagKey<Item> DENSE_ORES = modTag("dense_ores");
        public static final TagKey<Item> DENSE_DEEPSLATE_ORES = modTag("dense_deepslate_ores");

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }

        private static TagKey<Item> modTag(String name) {
            return ItemTags.create(new ResourceLocation(Oredustry.MOD_ID, name));
        }
    }
}
