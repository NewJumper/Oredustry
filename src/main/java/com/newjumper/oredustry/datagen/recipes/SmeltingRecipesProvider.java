package com.newjumper.oredustry.datagen.recipes;

import com.google.common.collect.ImmutableList;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

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

    public SmeltingRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        oreSmelting(consumer, DENSE_COAL_ORES, Items.COAL, 0.4f, 200, "coal");
        oreSmelting(consumer, DENSE_IRON_ORES, Items.IRON_INGOT, 2.8f, 200, "iron_ingot");
        oreSmelting(consumer, DENSE_COPPER_ORES, Items.COPPER_INGOT, 2.8f, 200, "copper_ingot");
        oreSmelting(consumer, DENSE_GOLD_ORES, Items.GOLD_INGOT, 4f, 200, "gold_ingot");
        oreSmelting(consumer, DENSE_REDSTONE_ORES, Items.REDSTONE, 2.8f, 200, "redstone");
        oreSmelting(consumer, DENSE_EMERALD_ORES, Items.EMERALD, 4f, 200, "emerald");
        oreSmelting(consumer, DENSE_LAPIS_ORES, Items.LAPIS_LAZULI, 0.8f, 200, "lapis_lazuli");
        oreSmelting(consumer, DENSE_DIAMOND_ORES, Items.DIAMOND, 4f, 200, "diamond");

        oreBlasting(consumer, DENSE_COAL_ORES, Items.COAL, 0.4f, 200, "coal");
        oreBlasting(consumer, DENSE_IRON_ORES, Items.IRON_INGOT, 2.8f, 200, "iron_ingot");
        oreBlasting(consumer, DENSE_COPPER_ORES, Items.COPPER_INGOT, 2.8f, 200, "copper_ingot");
        oreBlasting(consumer, DENSE_GOLD_ORES, Items.GOLD_INGOT, 4f, 200, "gold_ingot");
        oreBlasting(consumer, DENSE_REDSTONE_ORES, Items.REDSTONE, 2.8f, 200, "redstone");
        oreBlasting(consumer, DENSE_EMERALD_ORES, Items.EMERALD, 4f, 200, "emerald");
        oreBlasting(consumer, DENSE_LAPIS_ORES, Items.LAPIS_LAZULI, 0.8f, 200, "lapis_lazuli");
        oreBlasting(consumer, DENSE_DIAMOND_ORES, Items.DIAMOND, 4f, 200, "diamond");
    }
}
