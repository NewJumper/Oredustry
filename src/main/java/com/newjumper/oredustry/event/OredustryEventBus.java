package com.newjumper.oredustry.event;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Oredustry.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OredustryEventBus {
    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> pRegistryEvent) {
        Registry.register(Registry.RECIPE_TYPE, "separating", SeparatingRecipe.Type.INSTANCE);
    }
}
