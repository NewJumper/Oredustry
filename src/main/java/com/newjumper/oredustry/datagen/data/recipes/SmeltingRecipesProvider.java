package com.newjumper.oredustry.datagen.data.recipes;

import com.google.common.collect.ImmutableList;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class SmeltingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    private static final ImmutableList<ItemLike> DENSE_COAL_ORES = ImmutableList.of(OredustryBlocks.DENSE_COAL_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_IRON_ORES = ImmutableList.of(OredustryBlocks.DENSE_IRON_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_COPPER_ORES = ImmutableList.of(OredustryBlocks.DENSE_COPPER_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_GOLD_ORES = ImmutableList.of(OredustryBlocks.DENSE_GOLD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_REDSTONE_ORES = ImmutableList.of(OredustryBlocks.DENSE_REDSTONE_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_EMERALD_ORES = ImmutableList.of(OredustryBlocks.DENSE_EMERALD_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_LAPIS_ORES = ImmutableList.of(OredustryBlocks.DENSE_LAPIS_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get());
    private static final ImmutableList<ItemLike> DENSE_DIAMOND_ORES = ImmutableList.of(OredustryBlocks.DENSE_DIAMOND_ORE.get(), OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get());

    public SmeltingRecipesProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        oreSmelting(DENSE_COAL_ORES, Items.COAL, 0.4f, "coal", consumer);
        oreSmelting(DENSE_IRON_ORES, Items.IRON_INGOT, 2.8f, "iron_ingot", consumer);
        oreSmelting(DENSE_COPPER_ORES, Items.COPPER_INGOT, 2.8f, "copper_ingot", consumer);
        oreSmelting(DENSE_GOLD_ORES, Items.GOLD_INGOT, 4f, "gold_ingot", consumer);
        oreSmelting(DENSE_REDSTONE_ORES, Items.REDSTONE, 2.8f, "redstone", consumer);
        oreSmelting(DENSE_EMERALD_ORES, Items.EMERALD, 4f, "emerald", consumer);
        oreSmelting(DENSE_LAPIS_ORES, Items.LAPIS_LAZULI, 0.8f, "lapis_lazuli", consumer);
        oreSmelting(DENSE_DIAMOND_ORES, Items.DIAMOND, 4f, "diamond", consumer);

        oreBlasting(DENSE_COAL_ORES, Items.COAL, 0.4f, "coal", consumer);
        oreBlasting(DENSE_IRON_ORES, Items.IRON_INGOT, 2.8f, "iron_ingot", consumer);
        oreBlasting(DENSE_COPPER_ORES, Items.COPPER_INGOT, 2.8f, "copper_ingot", consumer);
        oreBlasting(DENSE_GOLD_ORES, Items.GOLD_INGOT, 4f, "gold_ingot", consumer);
        oreBlasting(DENSE_REDSTONE_ORES, Items.REDSTONE, 2.8f, "redstone", consumer);
        oreBlasting(DENSE_EMERALD_ORES, Items.EMERALD, 4f, "emerald", consumer);
        oreBlasting(DENSE_LAPIS_ORES, Items.LAPIS_LAZULI, 0.8f, "lapis_lazuli", consumer);
        oreBlasting(DENSE_DIAMOND_ORES, Items.DIAMOND, 4f, "diamond", consumer);
    }

    private void oreSmelting(List<ItemLike> ingredients, ItemLike result, float experience, String group, Consumer<FinishedRecipe> consumer) {
        for(ItemLike item : ingredients) SimpleCookingRecipeBuilder.smelting(Ingredient.of(item), result, experience, 200).group(group).unlockedBy(getHasName(item), has(item)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(result) + "_from_smelting_" + getItemName(item)));
    }

    private void oreBlasting(List<ItemLike> ingredients, ItemLike result, float experience, String group, Consumer<FinishedRecipe> consumer) {
        for(ItemLike item : ingredients) SimpleCookingRecipeBuilder.blasting(Ingredient.of(item), result, experience, 100).group(group).unlockedBy(getHasName(item), has(item)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(result) + "_from_blasting_" + getItemName(item)));
    }

    @NotNull
    @Override
    public String getName() {
        return "Smelting Recipes";
    }
}
