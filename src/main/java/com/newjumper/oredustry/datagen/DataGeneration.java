package com.newjumper.oredustry.datagen;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.datagen.assets.ENLanguageProvider;
import com.newjumper.oredustry.datagen.assets.OredustryBlockStateProvider;
import com.newjumper.oredustry.datagen.assets.OredustryItemModelProvider;
import com.newjumper.oredustry.datagen.data.OredustryLootTableProvider;
import com.newjumper.oredustry.datagen.data.recipes.CraftingRecipesProvider;
import com.newjumper.oredustry.datagen.data.recipes.MachiningRecipesProvider;
import com.newjumper.oredustry.datagen.data.recipes.SmeltingRecipesProvider;
import com.newjumper.oredustry.datagen.data.tags.OredustryBlockTagsProvider;
import com.newjumper.oredustry.datagen.data.tags.OredustryItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oredustry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneration {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // assets
        generator.addProvider(event.includeClient(), new ENLanguageProvider(generator));

        generator.addProvider(event.includeClient(), new OredustryBlockStateProvider(generator, fileHelper));
        generator.addProvider(event.includeClient(), new OredustryItemModelProvider(generator, fileHelper));

        // data
        generator.addProvider(event.includeServer(), new CraftingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new SmeltingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new MachiningRecipesProvider(generator));

        OredustryBlockTagsProvider blockTags = new OredustryBlockTagsProvider(generator, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new OredustryItemTagsProvider(generator, blockTags, fileHelper));

        generator.addProvider(event.includeServer(), new OredustryLootTableProvider(generator));
    }
}
