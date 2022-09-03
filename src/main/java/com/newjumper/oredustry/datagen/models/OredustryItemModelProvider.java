package com.newjumper.oredustry.datagen.models;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.item.OredustryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class OredustryItemModelProvider extends ItemModelProvider {
    private final ModelFile GENERATED = getExistingFile(mcLoc("item/generated"));

    public OredustryItemModelProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // BLOCKS
        blockModel(OredustryBlocks.DENSE_COAL_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE);
        blockModel(OredustryBlocks.DENSE_IRON_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE);
        blockModel(OredustryBlocks.DENSE_COPPER_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE);
        blockModel(OredustryBlocks.DENSE_GOLD_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE);
        blockModel(OredustryBlocks.DENSE_REDSTONE_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE);
        blockModel(OredustryBlocks.DENSE_EMERALD_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE);
        blockModel(OredustryBlocks.DENSE_LAPIS_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE);
        blockModel(OredustryBlocks.DENSE_DIAMOND_ORE);
        blockModel(OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE);

        blockModel(OredustryBlocks.COMPRESSOR);
        blockModel(OredustryBlocks.CRUCIBLE);
        blockModel(OredustryBlocks.PURIFIER);
        blockModel(OredustryBlocks.SEPARATOR);
        blockModel(OredustryBlocks.ENERGY_GENERATOR);
        blockModel(OredustryBlocks.HEAT_GENERATOR);

        blockModel(OredustryBlocks.HEAT_CABLE);

        // ITEMS
        itemModel(OredustryItems.PURE_COPPER, GENERATED);
        itemModel(OredustryItems.PURE_GOLD, GENERATED);
        itemModel(OredustryItems.PURE_IRON, GENERATED);
        itemModel(OredustryItems.PURE_NETHERITE, GENERATED);
        itemModel(OredustryItems.ENERGY_CELL, GENERATED);
        itemModel(OredustryItems.FUEL_CELL, GENERATED);
        itemModel(OredustryItems.HEAT_CELL, GENERATED);
        itemModel(OredustryItems.RADIATOR, GENERATED);
    }

    public void blockModel(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    public void itemModel(RegistryObject<Item> item, ModelFile modelFile) {
        getBuilder(item.getId().getPath()).parent(modelFile).texture("layer0", "item/" + item.getId().getPath());
    }
}
