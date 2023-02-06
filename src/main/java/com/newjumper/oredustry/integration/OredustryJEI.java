package com.newjumper.oredustry.integration;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.CompressingRecipe;
import com.newjumper.oredustry.recipe.MeltingRecipe;
import com.newjumper.oredustry.recipe.SeparatingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class OredustryJEI implements IModPlugin {
    private final RecipeType<CompressingRecipe> COMPRESSING = RecipeType.create(Oredustry.MOD_ID, "compressing", CompressingRecipe.class);
    private final RecipeType<MeltingRecipe> MELTING = RecipeType.create(Oredustry.MOD_ID, "melting", MeltingRecipe.class);
    private final RecipeType<SeparatingRecipe> SEPARATING = RecipeType.create(Oredustry.MOD_ID, "separating", SeparatingRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(Oredustry.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new CompressingCategory(guiHelper, 200), new MeltingCategory(guiHelper, 150), new SeparatingCategory(guiHelper, 200));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipes = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<CompressingRecipe> compressingRecipes = recipes.getAllRecipesFor(CompressingRecipe.Type.INSTANCE);
        List<MeltingRecipe> meltingRecipes = recipes.getAllRecipesFor(MeltingRecipe.Type.INSTANCE);
        List<SeparatingRecipe> separatingRecipes = recipes.getAllRecipesFor(SeparatingRecipe.Type.INSTANCE);

        registration.addRecipes(COMPRESSING, compressingRecipes);
        registration.addRecipes(MELTING, meltingRecipes);
        registration.addRecipes(SEPARATING, separatingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(OredustryBlocks.COMPRESSOR.get()), COMPRESSING);
        registration.addRecipeCatalyst(new ItemStack(OredustryBlocks.CRUCIBLE.get()), MELTING);
        registration.addRecipeCatalyst(new ItemStack(OredustryBlocks.SEPARATOR.get()), SEPARATING);
    }
}
