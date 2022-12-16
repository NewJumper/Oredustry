package com.newjumper.oredustry.datagen.data.recipes;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.datagen.data.recipes.builders.MeltingRecipeBuilder;
import com.newjumper.oredustry.datagen.data.recipes.builders.SeparatingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class MachiningRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public MachiningRecipesProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        new MeltingRecipeBuilder(Tags.Items.ORES_COAL, Items.COAL, 5)
                .unlockedBy("has_coal_ore", has(Tags.Items.ORES_COAL)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COAL) + "_from_melting_coal_ore"));

        new MeltingRecipeBuilder(Tags.Items.ORES_IRON, Items.IRON_INGOT, 3)
                .unlockedBy("has_iron_ore", has(Tags.Items.ORES_IRON)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.IRON_INGOT) + "_from_melting_iron_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_IRON, Items.IRON_INGOT, 4)
                .unlockedBy("has_raw_iron", has(Tags.Items.RAW_MATERIALS_IRON)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.IRON_INGOT) + "_from_melting_raw_iron"));

        new MeltingRecipeBuilder(Tags.Items.ORES_COPPER, Items.COPPER_INGOT, 3)
                .unlockedBy("has_copper_ore", has(Tags.Items.ORES_COPPER)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COPPER_INGOT) + "_from_melting_copper_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_COPPER, Items.COPPER_INGOT, 4)
                .unlockedBy("has_raw_copper", has(Tags.Items.RAW_MATERIALS_COPPER)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COPPER_INGOT) + "_from_melting_raw_copper"));

        new MeltingRecipeBuilder(Tags.Items.ORES_GOLD, Items.GOLD_INGOT, 3)
                .unlockedBy("has_gold_ore", has(Tags.Items.ORES_GOLD)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.GOLD_INGOT) + "_from_melting_gold_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_GOLD, Items.GOLD_INGOT, 4)
                .unlockedBy("has_raw_gold", has(Tags.Items.RAW_MATERIALS_GOLD)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.GOLD_INGOT) + "_from_melting_raw_gold"));

        new MeltingRecipeBuilder(Tags.Items.ORES_REDSTONE, Items.REDSTONE, 5)
                .unlockedBy("has_redstone_ore", has(Tags.Items.ORES_REDSTONE)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.REDSTONE) + "_from_melting_redstone_ore"));

        new MeltingRecipeBuilder(Tags.Items.ORES_EMERALD, Items.EMERALD, 3)
                .unlockedBy("has_emerald_ore", has(Tags.Items.ORES_EMERALD)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.EMERALD) + "_from_melting_emerald_ore"));

        new MeltingRecipeBuilder(Tags.Items.ORES_LAPIS, Items.LAPIS_LAZULI, 5)
                .unlockedBy("has_lapis_ore", has(Tags.Items.ORES_LAPIS)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.LAPIS_LAZULI) + "_from_melting_lapis_ore"));

        new MeltingRecipeBuilder(Tags.Items.ORES_DIAMOND, Items.DIAMOND, 3)
                .unlockedBy("has_diamond_ore", has(Tags.Items.ORES_DIAMOND)).save(consumer, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.DIAMOND) + "_from_melting_diamond_ore"));

        new SeparatingRecipeBuilder(Blocks.COAL_ORE, Items.COAL, 4, 100).stone()
                .unlockedBy("has_coal_ore", has(Blocks.COAL_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COAL_ORE, Items.COAL, 4).deepslate()
                .unlockedBy("has_deepslate_coal_ore", has(Blocks.DEEPSLATE_COAL_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.IRON_ORE, Items.RAW_IRON, 3, 100).stone()
                .unlockedBy("has_iron_ore", has(Blocks.IRON_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_IRON_ORE, Items.RAW_IRON, 3).deepslate()
                .unlockedBy("has_deepslate_iron_ore", has(Blocks.DEEPSLATE_IRON_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.COPPER_ORE, Items.RAW_COPPER, 3, 100).stone()
                .unlockedBy("has_copper_ore", has(Blocks.COPPER_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER, 3).deepslate()
                .unlockedBy("has_deepslate_copper_ore", has(Blocks.DEEPSLATE_COPPER_ORE)).save(consumer);

        new SeparatingRecipeBuilder(Blocks.GOLD_ORE, Items.RAW_GOLD, 3, 100).stone()
                .unlockedBy("has_gold_ore", has(Blocks.GOLD_ORE)).save(consumer);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD, 3).deepslate()
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

    @Override
    public @NotNull String getName() {
        return "Machining Recipes";
    }
}
