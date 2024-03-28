package com.newjumper.oredustry;

import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.OredustryItems;
import com.newjumper.oredustry.content.OredustryMenuTypes;
import com.newjumper.oredustry.content.OredustryRecipes;
import com.newjumper.oredustry.content.blocks.entity.OredustryBlockEntities;
import com.newjumper.oredustry.content.gui.screen.CompressorScreen;
import com.newjumper.oredustry.content.gui.screen.CrucibleScreen;
import com.newjumper.oredustry.content.gui.screen.MinerScreen;
import com.newjumper.oredustry.content.gui.screen.SeparatorScreen;
import com.newjumper.oredustry.content.recipes.CompressingRecipe;
import com.newjumper.oredustry.content.recipes.MeltingRecipe;
import com.newjumper.oredustry.content.recipes.SeparatingRecipe;
import com.newjumper.oredustry.datagen.assets.ENLanguageProvider;
import com.newjumper.oredustry.datagen.assets.OredustryBlockStateProvider;
import com.newjumper.oredustry.datagen.assets.OredustryItemModelProvider;
import com.newjumper.oredustry.datagen.data.OredustryBlockTagsProvider;
import com.newjumper.oredustry.datagen.data.OredustryItemTagsProvider;
import com.newjumper.oredustry.datagen.data.OredustryLootTables;
import com.newjumper.oredustry.datagen.data.recipes.OredustryRecipeProvider;
import com.newjumper.oredustry.network.Messages;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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

        OredustryCreativeTab.CREATIVE_MODE_TABS.register(eventBus);
        OredustryBlocks.BLOCKS.register(eventBus);
        OredustryItems.ITEMS.register(eventBus);
        OredustryBlockEntities.BLOCK_ENTITIES.register(eventBus);
        OredustryMenuTypes.MENUS.register(eventBus);
        OredustryRecipes.RECIPE_SERIALIZERS.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        eventBus.addListener(OredustryCreativeTab::buildCreativeTab);
        eventBus.addListener(this::registerRecipeTypes);
        eventBus.addListener(this::generateData);
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
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        // assets
        generator.addProvider(event.includeClient(), new ENLanguageProvider(packOutput));
        generator.addProvider(event.includeClient(), new OredustryBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new OredustryItemModelProvider(packOutput, fileHelper));

        // data
        OredustryBlockTagsProvider blockTags = new OredustryBlockTagsProvider(packOutput, event.getLookupProvider(), fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new OredustryItemTagsProvider(packOutput, event.getLookupProvider(), blockTags, fileHelper));

        generator.addProvider(event.includeServer(), new OredustryLootTables(packOutput));
        generator.addProvider(event.includeServer(), new OredustryRecipeProvider(packOutput));
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
