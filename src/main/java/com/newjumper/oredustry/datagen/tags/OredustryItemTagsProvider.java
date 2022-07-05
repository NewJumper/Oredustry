package com.newjumper.oredustry.datagen.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class OredustryItemTagsProvider extends ItemTagsProvider {
    public OredustryItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.ORES_COAL, Tags.Items.ORES_COAL);
        copy(Tags.Blocks.ORES_IRON, Tags.Items.ORES_IRON);
        copy(Tags.Blocks.ORES_COPPER, Tags.Items.ORES_COPPER);
        copy(Tags.Blocks.ORES_GOLD, Tags.Items.ORES_GOLD);
        copy(Tags.Blocks.ORES_REDSTONE, Tags.Items.ORES_REDSTONE);
        copy(Tags.Blocks.ORES_EMERALD, Tags.Items.ORES_EMERALD);
        copy(Tags.Blocks.ORES_LAPIS, Tags.Items.ORES_LAPIS);
        copy(Tags.Blocks.ORES_DIAMOND, Tags.Items.ORES_DIAMOND);
        copy(Tags.Blocks.ORE_RATES_DENSE, Tags.Items.ORE_RATES_DENSE);
        copy(Tags.Blocks.ORES_IN_GROUND_STONE, Tags.Items.ORES_IN_GROUND_STONE);
        copy(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE, Tags.Items.ORES_IN_GROUND_DEEPSLATE);

        copy(OredustryTags.Blocks.DENSE_ORES, OredustryTags.Items.DENSE_ORES);
        copy(OredustryTags.Blocks.DENSE_DEEPSLATE_ORES, OredustryTags.Items.DENSE_DEEPSLATE_ORES);

        tag(OredustryTags.Items.PURE_MATERIALS).add(OredustryItems.PURE_COPPER.get(), OredustryItems.PURE_GOLD.get(), OredustryItems.PURE_IRON.get(), OredustryItems.PURE_NETHERITE.get());
    }
}
