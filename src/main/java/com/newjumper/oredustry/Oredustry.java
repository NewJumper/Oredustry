package com.newjumper.oredustry;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.datagen.assets.ENLanguageProvider;
import com.newjumper.oredustry.datagen.assets.OredustryBlockStateProvider;
import com.newjumper.oredustry.datagen.assets.OredustryItemModelProvider;
import com.newjumper.oredustry.datagen.data.OredustryLootTableProvider;
import com.newjumper.oredustry.datagen.data.recipes.CraftingRecipesProvider;
import com.newjumper.oredustry.datagen.data.recipes.MachiningRecipesProvider;
import com.newjumper.oredustry.datagen.data.tags.OredustryBlockTagsProvider;
import com.newjumper.oredustry.datagen.data.tags.OredustryItemTagsProvider;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.network.Messages;
import com.newjumper.oredustry.recipe.CompressingRecipe;
import com.newjumper.oredustry.recipe.MeltingRecipe;
import com.newjumper.oredustry.recipe.OredustryRecipes;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import com.newjumper.oredustry.screen.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod("oredustry")
public class Oredustry {
    public static final String MOD_ID = "oredustry";

    public Oredustry() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Messages.registerMessages(MOD_ID + "_network");

        OredustryBlocks.BLOCKS.register(eventBus);
        OredustryItems.ITEMS.register(eventBus);
        OredustryBlockEntities.BLOCK_ENTITIES.register(eventBus);
        OredustryMenuTypes.MENUS.register(eventBus);
        OredustryRecipes.RECIPE_SERIALIZERS.register(eventBus);

        eventBus.addListener(this::registerRecipeTypes);
        eventBus.addListener(this::generateData);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void registerRecipeTypes(final RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(MOD_ID, "compressing"), CompressingRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(MOD_ID, "melting"), MeltingRecipe.Type.INSTANCE);
            helper.register(new ResourceLocation(MOD_ID, "separating"), SeparatingRecipe.Type.INSTANCE);
        });
    }

    public void generateData(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // assets
        generator.addProvider(event.includeClient(), new ENLanguageProvider(generator));

        generator.addProvider(event.includeClient(), new OredustryBlockStateProvider(generator, fileHelper));
        generator.addProvider(event.includeClient(), new OredustryItemModelProvider(generator, fileHelper));

        // data
        generator.addProvider(event.includeServer(), new CraftingRecipesProvider(generator));
        generator.addProvider(event.includeServer(), new MachiningRecipesProvider(generator));

        OredustryBlockTagsProvider blockTags = new OredustryBlockTagsProvider(generator, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new OredustryItemTagsProvider(generator, blockTags, fileHelper));

        generator.addProvider(event.includeServer(), new OredustryLootTableProvider(generator));
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class OredustryClient {
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            MenuScreens.register(OredustryMenuTypes.COMPRESSOR_MENU.get(), CompressorScreen::new);
            MenuScreens.register(OredustryMenuTypes.CRUCIBLE_MENU.get(), CrucibleScreen::new);
            MenuScreens.register(OredustryMenuTypes.SEPARATOR_MENU.get(), SeparatorScreen::new);
            MenuScreens.register(OredustryMenuTypes.MINER_MENU.get(), MinerScreen::new);
        }
    }
}
