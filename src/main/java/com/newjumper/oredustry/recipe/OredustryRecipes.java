package com.newjumper.oredustry.recipe;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Oredustry.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CompressingRecipe>> COMPRESSING = RECIPE_SERIALIZERS.register("compressing", CompressingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<MeltingRecipe>> MELTING = RECIPE_SERIALIZERS.register("melting", MeltingRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<SeparatingRecipe>> SEPARATING = RECIPE_SERIALIZERS.register("separating", SeparatingRecipe.Serializer::new);
}
