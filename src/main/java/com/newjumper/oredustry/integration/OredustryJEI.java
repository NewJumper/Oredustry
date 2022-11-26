package com.newjumper.oredustry.integration;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class OredustryJEI implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Oredustry.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new SeparatingCategory(guiHelper, 200));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipes = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<SeparatingRecipe> separating = recipes.getAllRecipesFor(SeparatingRecipe.Type.INSTANCE);

        registration.addRecipes(new RecipeType<>(new ResourceLocation(Oredustry.MOD_ID, "separating"), SeparatingRecipe.class), separating);
    }
}
