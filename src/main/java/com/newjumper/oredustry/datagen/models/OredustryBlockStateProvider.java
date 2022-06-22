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
        simpleBlock(OredustryBlocks.COMPRESSOR.get(), models().cubeColumn("compressor", modLoc("block/compressor"), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.CRUCIBLE.get(), models().cubeBottomTop("crucible", modLoc("block/crucible"), modLoc("block/crucible_bottom"), modLoc("block/crucible_top")));
        simpleBlock(OredustryBlocks.PURIFIER.get(), models().cubeColumn("purifier", modLoc("block/purifier"), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.SEPARATOR.get(), models().cubeColumn("separator", modLoc("block/separator"), modLoc("block/machine_top")));

        simpleBlock(OredustryBlocks.ENERGY_GENERATOR.get(), models().cubeBottomTop("energy_generator", modLoc("block/energy_generator"), modLoc("block/energy_generator_bottom"), modLoc("block/energy_generator_top")));
        simpleBlock(OredustryBlocks.HEAT_GENERATOR.get(), models().cubeBottomTop("heat_generator", modLoc("block/heat_generator"), modLoc("block/heat_generator_bottom"), modLoc("block/heat_generator_top")));
    }
}
