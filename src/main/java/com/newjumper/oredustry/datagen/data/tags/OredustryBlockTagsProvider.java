package com.newjumper.oredustry.datagen.data.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class OredustryBlockTagsProvider extends BlockTagsProvider {
    public OredustryBlockTagsProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Oredustry.MOD_ID, exFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTags(OredustryTags.Blocks.MACHINES, OredustryTags.Blocks.DENSE_ORES, OredustryTags.Blocks.DENSE_DEEPSLATE_ORES);
        tag(BlockTags.NEEDS_STONE_TOOL).add(OredustryBlocks.DENSE_IRON_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get(), OredustryBlocks.DENSE_COPPER_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get(), OredustryBlocks.DENSE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get());
        tag(BlockTags.NEEDS_IRON_TOOL).add(OredustryBlocks.DENSE_GOLD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get(), OredustryBlocks.DENSE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DIAMOND_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());

        tag(BlockTags.COAL_ORES).add(OredustryBlocks.DENSE_COAL_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get());
        tag(BlockTags.IRON_ORES).add(OredustryBlocks.DENSE_IRON_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get());
        tag(BlockTags.COPPER_ORES).add(OredustryBlocks.DENSE_COPPER_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get());
        tag(BlockTags.GOLD_ORES).add(OredustryBlocks.DENSE_GOLD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get());
        tag(BlockTags.REDSTONE_ORES).add(OredustryBlocks.DENSE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get());
        tag(BlockTags.EMERALD_ORES).add(OredustryBlocks.DENSE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get());
        tag(BlockTags.LAPIS_ORES).add(OredustryBlocks.DENSE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get());
        tag(BlockTags.DIAMOND_ORES).add(OredustryBlocks.DENSE_DIAMOND_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());

        tag(Tags.Blocks.ORES).addTags(OredustryTags.Blocks.DENSE_ORES, OredustryTags.Blocks.DENSE_DEEPSLATE_ORES);
        tag(Tags.Blocks.ORES_COAL).add(OredustryBlocks.DENSE_COAL_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get());
        tag(Tags.Blocks.ORES_IRON).add(OredustryBlocks.DENSE_IRON_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get());
        tag(Tags.Blocks.ORES_COPPER).add(OredustryBlocks.DENSE_COPPER_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get());
        tag(Tags.Blocks.ORES_GOLD).add(OredustryBlocks.DENSE_GOLD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get());
        tag(Tags.Blocks.ORES_REDSTONE).add(OredustryBlocks.DENSE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get());
        tag(Tags.Blocks.ORES_EMERALD).add(OredustryBlocks.DENSE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get());
        tag(Tags.Blocks.ORES_LAPIS).add(OredustryBlocks.DENSE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get());
        tag(Tags.Blocks.ORES_DIAMOND).add(OredustryBlocks.DENSE_DIAMOND_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());
        tag(Tags.Blocks.ORE_RATES_DENSE).addTags(OredustryTags.Blocks.DENSE_ORES, OredustryTags.Blocks.DENSE_DEEPSLATE_ORES);
        tag(Tags.Blocks.ORES_IN_GROUND_STONE).addTag(OredustryTags.Blocks.DENSE_ORES);
        tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).addTag(OredustryTags.Blocks.DENSE_DEEPSLATE_ORES);

        tag(OredustryTags.Blocks.MACHINES).add(OredustryBlocks.COMPRESSOR.get(), OredustryBlocks.CRUCIBLE.get(), OredustryBlocks.PURIFIER.get(), OredustryBlocks.SEPARATOR.get());
        tag(OredustryTags.Blocks.DENSE_ORES).add(OredustryBlocks.DENSE_COAL_ORE.get(), OredustryBlocks.DENSE_IRON_ORE.get(), OredustryBlocks.DENSE_COPPER_ORE.get(), OredustryBlocks.DENSE_GOLD_ORE.get(), OredustryBlocks.DENSE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_EMERALD_ORE.get(), OredustryBlocks.DENSE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DIAMOND_ORE.get());
        tag(OredustryTags.Blocks.DENSE_DEEPSLATE_ORES).add(OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}
