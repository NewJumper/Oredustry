package com.newjumper.oredustry;

import com.mojang.logging.LogUtils;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.heat.IHeatStorage;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.recipe.OredustryRecipes;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import com.newjumper.oredustry.screen.EnergyGeneratorScreen;
import com.newjumper.oredustry.screen.HeatGeneratorScreen;
import com.newjumper.oredustry.screen.OredustryMenuTypes;
import com.newjumper.oredustry.screen.SeparatorScreen;
import com.newjumper.oredustry.world.OreConfiguredFeatures;
import com.newjumper.oredustry.world.OrePlacedFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

@Mod("oredustry")
public class Oredustry {
    public static final String MOD_ID = "oredustry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Oredustry() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        OredustryBlocks.BLOCKS.register(eventBus);
        OreConfiguredFeatures.CONFIGURED_FEATURES.register(eventBus);
        OrePlacedFeatures.PLACED_FEATURES.register(eventBus);
        OredustryItems.ITEMS.register(eventBus);
        OredustryBlockEntities.BLOCK_ENTITIES.register(eventBus);
        OredustryMenuTypes.MENUS.register(eventBus);
        OredustryRecipes.RECIPE_SERIALIZERS.register(eventBus);

        eventBus.addListener(this::registerCapabilities);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IHeatStorage.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class OredustryClient {
        @SubscribeEvent
        public static void clientSetup(final FMLClientSetupEvent event) {
            MenuScreens.register(OredustryMenuTypes.SEPARATOR_MENU.get(), SeparatorScreen::new);
            MenuScreens.register(OredustryMenuTypes.ENERGY_GENERATOR_MENU.get(), EnergyGeneratorScreen::new);
            MenuScreens.register(OredustryMenuTypes.HEAT_GENERATOR_MENU.get(), HeatGeneratorScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = Oredustry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class OredustryEventBus {
        @SubscribeEvent
        public static void registerRecipeTypes(final RegisterEvent event) {
            event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
                helper.register(new ResourceLocation(MOD_ID, "separating"), SeparatingRecipe.Type.INSTANCE);
            });
        }
    }
}
