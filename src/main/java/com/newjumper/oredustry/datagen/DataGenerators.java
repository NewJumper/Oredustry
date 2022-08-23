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
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oredustry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // languages
        generator.addProvider(event.includeClient(), new ENLanguageProvider(generator));

        // loot tables
        generator.addProvider(event.includeServer(), new OredustryLootTableProvider(generator));

        // models
        generator.addProvider(event.includeClient(), new OredustryBlockStateProvider(generator, fileHelper));
        generator.addProvider(event.includeClient(), new OredustryItemModelProvider(generator, fileHelper));

        // recipes
        generator.addProvider(event.includeServer(), new CraftingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new SmeltingRecipesProvider(generator));

        // tags
        OredustryBlockTagsProvider blockTags = new OredustryBlockTagsProvider(generator, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new OredustryItemTagsProvider(generator, blockTags, fileHelper));
    }
}
