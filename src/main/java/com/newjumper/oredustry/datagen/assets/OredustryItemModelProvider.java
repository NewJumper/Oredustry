package com.newjumper.oredustry.datagen.assets;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.OredustryItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class OredustryItemModelProvider extends ItemModelProvider {
    private final ModelFile GENERATED = getExistingFile(mcLoc("item/generated"));

    public OredustryItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockModel(OredustryBlocks.MACHINE_FRAME);
        blockModel(OredustryBlocks.COMPRESSOR);
        blockModel(OredustryBlocks.CRUCIBLE);
        blockModel(OredustryBlocks.SEPARATOR);
        blockModel(OredustryBlocks.MINER);

        itemModel(OredustryItems.DENSE_COAL);
        itemModel(OredustryItems.DENSE_RAW_COPPER);
        itemModel(OredustryItems.DENSE_RAW_GOLD);
        itemModel(OredustryItems.DENSE_RAW_IRON);
        itemModel(OredustryItems.DENSE_REDSTONE);
        itemModel(OredustryItems.DENSE_EMERALD);
        itemModel(OredustryItems.DENSE_LAPIS);
        itemModel(OredustryItems.DENSE_DIAMOND);

        itemModel(OredustryItems.CONDUCTION_CELL);
        itemModel(OredustryItems.INDUCTION_CELL);

        itemModel(OredustryItems.COMPRESSOR_UPGRADE);
        itemModel(OredustryItems.CRUCIBLE_UPGRADE);
        itemModel(OredustryItems.SEPARATOR_UPGRADE);

        itemModel(OredustryItems.FUEL_UPGRADE);
        itemModel(OredustryItems.RANGE_UPGRADE);
        itemModel(OredustryItems.SPEED_UPGRADE);
        itemModel(OredustryItems.STORAGE_UPGRADE);
        itemModel(OredustryItems.UPGRADE_BASE);

        getBuilder("oredustry_guide").parent(GENERATED).texture("layer0", "item/oredustry_guide");
    }

    public void blockModel(RegistryObject<Block> block) {
        withExistingParent(block.getId().getPath(), modLoc("block/" + block.getId().getPath()));
    }

    public void itemModel(RegistryObject<Item> item) {
        getBuilder(item.getId().getPath()).parent(GENERATED).texture("layer0", "item/" + item.getId().getPath());
    }
}
