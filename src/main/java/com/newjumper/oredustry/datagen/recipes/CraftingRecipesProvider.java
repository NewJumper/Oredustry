package com.newjumper.oredustry.datagen.recipes;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class CraftingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public CraftingRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(OredustryBlocks.ENERGY_GENERATOR.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('G', Tags.Items.GLASS)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("ICI")
                .pattern("GRG")
                .pattern("SSS")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_redstone", has(Tags.Items.STORAGE_BLOCKS_REDSTONE)).save(consumer);

        ShapedRecipeBuilder.shaped(OredustryBlocks.HEAT_GENERATOR.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('G', Tags.Items.GLASS)
                .define('B', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("ICI")
                .pattern("GBG")
                .pattern("SSS")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_coal", has(Tags.Items.STORAGE_BLOCKS_COAL)).save(consumer);
    }
}
