package com.newjumper.oredustry.datagen.lang;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.item.OredustryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class ENLanguageProvider extends LanguageProvider {
    public ENLanguageProvider(DataGenerator gen) {
        super(gen, Oredustry.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        OredustryBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(this::addBlock);
        OredustryItems.ITEMS.getEntries().stream().map(RegistryObject::get).filter(item -> !(item instanceof BlockItem)).forEach(this::addItem);

        add("itemGroup.oredustry", "Oredustry");
    }

    private void addBlock(Block block) {
        String key = block.getRegistryName().getPath();
        add("block.oredustry." + key, convertToName(key));
    }

    private void addItem(Item item) {
        String key = item.getRegistryName().getPath();
        add("item.oredustry." + key, convertToName(key));
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
