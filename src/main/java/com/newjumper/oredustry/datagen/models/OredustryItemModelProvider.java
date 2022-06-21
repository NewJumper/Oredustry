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

public class OredustryItemModelProvider extends ItemModelProvider {
    private final ModelFile GENERATED = getExistingFile(mcLoc("item/generated"));

    public OredustryItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // BLOCKS
        blockModel(OredustryBlocks.HEAT_GENERATOR.get());

        // ITEMS
        itemModel(OredustryItems.RANDOM_ITEM.get(), GENERATED);
    }

    public void blockModel(Block block) {
        withExistingParent(block.getRegistryName().getPath(), modLoc("block/" + block.getRegistryName().getPath()));
    }

    public void itemModel(Item item, ModelFile modelFile) {
        getBuilder(item.getRegistryName().getPath()).parent(modelFile).texture("layer0", "item/" + item.getRegistryName().getPath());
    }
}
