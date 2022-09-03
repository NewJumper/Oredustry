package com.newjumper.oredustry.datagen.models;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

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

        simpleBlock(OredustryBlocks.COMPRESSOR.get(), models().cubeColumn("compressor", blockLoc(OredustryBlocks.COMPRESSOR), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.CRUCIBLE.get(), models().cubeBottomTop("crucible", blockLoc(OredustryBlocks.CRUCIBLE), blockLoc(OredustryBlocks.CRUCIBLE, "bottom"), blockLoc(OredustryBlocks.CRUCIBLE, "top")));
        simpleBlock(OredustryBlocks.PURIFIER.get(), models().cubeColumn("purifier", blockLoc(OredustryBlocks.PURIFIER), modLoc("block/machine_top")));
        simpleBlock(OredustryBlocks.SEPARATOR.get(), models().cubeColumn("separator", blockLoc(OredustryBlocks.SEPARATOR), modLoc("block/machine_top")));

        horizontalBlock(OredustryBlocks.ENERGY_GENERATOR.get(), models().orientableWithBottom("energy_generator", blockLoc(OredustryBlocks.ENERGY_GENERATOR, "side"), blockLoc(OredustryBlocks.ENERGY_GENERATOR, "on"), blockLoc(OredustryBlocks.ENERGY_GENERATOR, "bottom"), blockLoc(OredustryBlocks.ENERGY_GENERATOR, "top")));
        simpleBlock(OredustryBlocks.HEAT_GENERATOR.get(), models().cubeBottomTop("heat_generator", blockLoc(OredustryBlocks.HEAT_GENERATOR), blockLoc(OredustryBlocks.HEAT_GENERATOR, "bottom"), blockLoc(OredustryBlocks.HEAT_GENERATOR, "top")));

        cableBlock(OredustryBlocks.ENERGY_CABLE);
        cableBlock(OredustryBlocks.HEAT_CABLE);
    }

    public void cableBlock(RegistryObject<? extends Block> block) {
        ModelFile cable = models().withExistingParent(block.getId().getPath(), modLoc("block/template_cable")).texture("cable", blockLoc(block));
        ModelFile horizontal = models().withExistingParent(block.getId().getPath() + "_horizontal", modLoc("block/template_cable_horizontal")).texture("cable", blockLoc(block));
        ModelFile vertical = models().withExistingParent(block.getId().getPath() + "_vertical", modLoc("block/template_cable_vertical")).texture("cable", blockLoc(block));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get()).part().modelFile(cable).addModel().end();
        PipeBlock.PROPERTY_BY_DIRECTION.forEach((direction, value) -> {
            if (direction.getAxis().isHorizontal()) builder.part().modelFile(horizontal).rotationY((((int) direction.toYRot()) + 270) % 360).uvLock(true).addModel().condition(value, true);
            if (direction == Direction.UP) builder.part().modelFile(vertical).rotationX(0).uvLock(true).addModel().condition(value, true);
            if (direction == Direction.DOWN) builder.part().modelFile(vertical).rotationX(180).uvLock(true).addModel().condition(value, true);
        });
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block) {
        return modLoc("block/" + block.getId().getPath());
    }

    public ResourceLocation blockLoc(RegistryObject<? extends Block> block, String suffix) {
        return modLoc("block/" + block.getId().getPath() + "_" + suffix);
    }
}
