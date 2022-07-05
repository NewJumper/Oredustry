package com.newjumper.oredustry.datagen.models;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class OredustryBlockStateProvider extends BlockStateProvider {
    public OredustryBlockStateProvider(DataGenerator pGenerator, ExistingFileHelper exFileHelper) {
        super(pGenerator, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(OredustryBlocks.DENSE_COAL_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_IRON_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_COPPER_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_GOLD_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_REDSTONE_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_EMERALD_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_LAPIS_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DIAMOND_ORE.get());
        simpleBlock(OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());

        simpleBlock(OredustryBlocks.COMPRESSOR.get(), models().cubeColumn("compressor", modLoc("block/compressor"), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.CRUCIBLE.get(), models().cubeBottomTop("crucible", modLoc("block/crucible"), modLoc("block/crucible_bottom"), modLoc("block/crucible_top")));
        simpleBlock(OredustryBlocks.PURIFIER.get(), models().cubeColumn("purifier", modLoc("block/purifier"), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.SEPARATOR.get(), models().cubeColumn("separator", modLoc("block/separator"), modLoc("block/machine_top")));

        horizontalBlock(OredustryBlocks.ENERGY_GENERATOR.get(), models().orientableWithBottom("energy_generator", modLoc("block/energy_generator_side"), modLoc("block/energy_generator_on"), modLoc("block/energy_generator_bottom"), modLoc("block/energy_generator_top")));
        simpleBlock(OredustryBlocks.HEAT_GENERATOR.get(), models().cubeBottomTop("heat_generator", modLoc("block/heat_generator"), modLoc("block/heat_generator_bottom"), modLoc("block/heat_generator_top")));
    }
}
