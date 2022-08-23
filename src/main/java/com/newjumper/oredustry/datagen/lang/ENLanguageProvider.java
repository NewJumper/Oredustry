package com.newjumper.oredustry.datagen.lang;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.item.OredustryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ENLanguageProvider extends LanguageProvider {
    public ENLanguageProvider(DataGenerator pGenerator) {
        super(pGenerator, Oredustry.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        OredustryBlocks.BLOCKS.getEntries().forEach(this::addBlock);
        OredustryItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof BlockItem)).forEach(this::addItem);
        OredustryBlockEntities.BLOCK_ENTITIES.getEntries().forEach(this::addContainer);

        add("itemGroup.oredustry", "Oredustry");
    }

    private void addBlock(RegistryObject<Block> block) {
        String key = block.getId().getPath();
        add("block.oredustry." + key, convertToName(key));
    }

    private void addItem(RegistryObject<Item> item) {
        String key = item.getId().getPath();
        add("item.oredustry." + key, convertToName(key));
    }

    private void addContainer(RegistryObject<BlockEntityType<?>> blockEntity) {
        String key = blockEntity.getId().getPath();
        add("container.oredustry." + key, convertToName(key));
    }

    private String convertToName(String key) {
        StringBuilder builder = new StringBuilder(key.substring(0, 1).toUpperCase() + key.substring(1));
        for(int i = 1; i < builder.length(); i++) {
            if(builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, " " + Character.toUpperCase(builder.charAt(i)));
            }
        }

        return builder.toString();
    }
}
