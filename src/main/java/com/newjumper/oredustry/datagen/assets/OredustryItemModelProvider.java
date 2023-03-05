package com.newjumper.oredustry.datagen.assets;

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

    public OredustryItemModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        blockModel(OredustryBlocks.MACHINE_FRAME);
        blockModel(OredustryBlocks.COMPRESSOR);
        blockModel(OredustryBlocks.CRUCIBLE);
        blockModel(OredustryBlocks.SEPARATOR);
        blockModel(OredustryBlocks.MINER);

        itemModel(OredustryItems.DENSE_COAL, GENERATED);
        itemModel(OredustryItems.DENSE_RAW_COPPER, GENERATED);
        itemModel(OredustryItems.DENSE_RAW_GOLD, GENERATED);
        itemModel(OredustryItems.DENSE_RAW_IRON, GENERATED);
        itemModel(OredustryItems.DENSE_REDSTONE, GENERATED);
        itemModel(OredustryItems.DENSE_EMERALD, GENERATED);
        itemModel(OredustryItems.DENSE_LAPIS, GENERATED);
        itemModel(OredustryItems.DENSE_DIAMOND, GENERATED);

        itemModel(OredustryItems.CONDUCTION_CELL, GENERATED);
        itemModel(OredustryItems.INDUCTION_CELL, GENERATED);

        itemModel(OredustryItems.COMPRESSOR_UPGRADE, GENERATED);
        itemModel(OredustryItems.CRUCIBLE_UPGRADE, GENERATED);
        itemModel(OredustryItems.SEPARATOR_UPGRADE, GENERATED);
    }

    public void blockModel(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    public void itemModel(RegistryObject<Item> item, ModelFile modelFile) {
        getBuilder(item.getId().getPath()).parent(modelFile).texture("layer0", "item/" + item.getId().getPath());
    }
}
