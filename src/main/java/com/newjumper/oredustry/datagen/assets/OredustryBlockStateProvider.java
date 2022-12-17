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

        simpleBlock(OredustryBlocks.COMPRESSOR.get(), models().cubeColumn("compressor", blockLoc(OredustryBlocks.COMPRESSOR), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.PURIFIER.get(), models().cubeColumn("purifier", blockLoc(OredustryBlocks.PURIFIER), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.SEPARATOR.get(), models().cubeColumn("separator", blockLoc(OredustryBlocks.SEPARATOR), modLoc("block/machine_top")));
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block) {
        return modLoc("block/" + block.getId().getPath());
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block, String suffix) {
        return modLoc("block/" + block.getId().getPath() + "_" + suffix);
    }
}
