package com.newjumper.oredustry.datagen.assets;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.MoltenLiquids;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;

public class ENLanguageProvider extends LanguageProvider {
    public ENLanguageProvider(DataGenerator gen) {
        super(gen, Oredustry.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        OredustryBlocks.BLOCKS.getEntries().forEach(block -> add(block, "block"));
        OredustryItems.ITEMS.getEntries().stream().filter(item -> !(item.get() instanceof BlockItem)).forEach(item -> add(item, "item"));
        OredustryBlockEntities.BLOCK_ENTITIES.getEntries().forEach(container -> add(container, "container"));

        add("itemGroup." + Oredustry.MOD_ID, "Oredustry");
        Arrays.stream(MoltenLiquids.values()).map(MoltenLiquids::getName).forEach(name -> add("liquid." + Oredustry.MOD_ID + "." + name, convertToName(name)));
    }

    private void add(RegistryObject<?> entry, String prefix) {
        String key = entry.getId().getPath();
        add(prefix + "." + Oredustry.MOD_ID + "." + key, convertToName(key));
    }

    private String convertToName(String key) {
        StringBuilder builder = new StringBuilder(key.substring(0, 1).toUpperCase() + key.substring(1));
        for(int i = 1; i < builder.length(); i++) {
            if(builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, " " + Character.toUpperCase(builder.charAt(i)));
            }
        }

        return builder.toString().replace("Lapis", "Lapis Lazuli");
    }
}
