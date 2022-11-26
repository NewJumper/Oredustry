package com.newjumper.oredustry.datagen.data.recipes;

import com.newjumper.oredustry.datagen.data.recipes.builders.SeparatingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MachiningRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public MachiningRecipesProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        new SeparatingRecipeBuilder(Blocks.COAL_ORE, Items.COAL, 3, 100).stone()
                .unlockedBy("has_coal_ore", has(Blocks.COAL_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COAL_ORE, Items.COAL, 3).deepslate()
                .unlockedBy("has_deepslate_coal_ore", has(Blocks.DEEPSLATE_COAL_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.IRON_ORE, Items.RAW_IRON, 2, 100).stone()
                .unlockedBy("has_iron_ore", has(Blocks.IRON_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_IRON_ORE, Items.RAW_IRON, 2).deepslate()
                .unlockedBy("has_deepslate_iron_ore", has(Blocks.DEEPSLATE_IRON_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.COPPER_ORE, Items.RAW_COPPER, 2, 100).stone()
                .unlockedBy("has_copper_ore", has(Blocks.COPPER_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER, 2).deepslate()
                .unlockedBy("has_deepslate_copper_ore", has(Blocks.DEEPSLATE_COPPER_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.GOLD_ORE, Items.RAW_GOLD, 2, 100).stone()
                .unlockedBy("has_gold_ore", has(Blocks.GOLD_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD, 2).deepslate()
                .unlockedBy("has_deepslate_gold_ore", has(Blocks.DEEPSLATE_GOLD_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.REDSTONE_ORE, Items.REDSTONE, 4, 100).stone()
                .unlockedBy("has_redstone_ore", has(Blocks.REDSTONE_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 4).deepslate()
                .unlockedBy("has_deepslate_redstone_ore", has(Blocks.DEEPSLATE_REDSTONE_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.EMERALD_ORE, Items.EMERALD, 2, 100).stone()
                .unlockedBy("has_emerald_ore", has(Blocks.EMERALD_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 2).deepslate()
                .unlockedBy("has_deepslate_emerald_ore", has(Blocks.DEEPSLATE_EMERALD_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.LAPIS_ORE, Items.LAPIS_LAZULI, 4, 100).stone()
                .unlockedBy("has_lapis_ore", has(Blocks.LAPIS_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 4).deepslate()
                .unlockedBy("has_deepslate_lapis_ore", has(Blocks.DEEPSLATE_LAPIS_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.DIAMOND_ORE, Items.DIAMOND, 2, 100).stone()
                .unlockedBy("has_diamond_ore", has(Blocks.DIAMOND_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 2).deepslate()
                .unlockedBy("has_deepslate_diamond_ore", has(Blocks.DEEPSLATE_DIAMOND_ORE)).save(consumer);
    }
}
