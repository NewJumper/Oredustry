package com.newjumper.oredustry.datagen.assets;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class OredustryBlockStateProvider extends BlockStateProvider {
    public OredustryBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(OredustryBlocks.COMPRESSOR.get(), models().cubeColumn("compressor", blockLoc(OredustryBlocks.COMPRESSOR), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.SEPARATOR.get(), models().cubeColumn("separator", blockLoc(OredustryBlocks.SEPARATOR), modLoc("block/machine_top")));
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block) {
        return modLoc("block/" + block.getId().getPath());
    }
}
