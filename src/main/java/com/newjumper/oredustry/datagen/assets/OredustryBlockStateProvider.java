package com.newjumper.oredustry.datagen.assets;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class OredustryBlockStateProvider extends BlockStateProvider {
    public OredustryBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlock(OredustryBlocks.COMPRESSOR.get(), models().orientable("compressor", modLoc("block/machine_side"), blockLoc(OredustryBlocks.COMPRESSOR), modLoc("block/machine_top")));
        horizontalBlock(OredustryBlocks.SEPARATOR.get(), models().orientable("separator", modLoc("block/machine_side"), blockLoc(OredustryBlocks.SEPARATOR), modLoc("block/machine_top")));
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block) {
        return modLoc("block/" + block.getId().getPath());
    }
}
