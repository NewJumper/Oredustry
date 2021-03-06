package com.newjumper.oredustry.datagen;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.datagen.lang.ENLanguageProvider;
import com.newjumper.oredustry.datagen.loot.OredustryLootTableProvider;
import com.newjumper.oredustry.datagen.models.OredustryBlockStateProvider;
import com.newjumper.oredustry.datagen.models.OredustryItemModelProvider;
import com.newjumper.oredustry.datagen.recipes.CraftingRecipesProvider;
import com.newjumper.oredustry.datagen.recipes.SmeltingRecipesProvider;
import com.newjumper.oredustry.datagen.tags.OredustryBlockTagsProvider;
import com.newjumper.oredustry.datagen.tags.OredustryItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Oredustry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // languages
        generator.addProvider(new ENLanguageProvider(generator));

        // loot
        generator.addProvider(new OredustryLootTableProvider(generator));

        // models
        generator.addProvider(new OredustryBlockStateProvider(generator, fileHelper));
        generator.addProvider(new OredustryItemModelProvider(generator, fileHelper));

        // recipes
        generator.addProvider(new CraftingRecipesProvider(generator));
        generator.addProvider(new SmeltingRecipesProvider(generator));

        // tags
        OredustryBlockTagsProvider blockTags = new OredustryBlockTagsProvider(generator, fileHelper);
        generator.addProvider(blockTags);
        generator.addProvider(new OredustryItemTagsProvider(generator, blockTags, fileHelper));
    }
}
