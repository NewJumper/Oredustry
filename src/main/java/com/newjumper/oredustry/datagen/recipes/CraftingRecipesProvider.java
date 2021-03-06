package com.newjumper.oredustry.datagen.recipes;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.item.OredustryItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class CraftingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public CraftingRecipesProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(OredustryBlocks.COMPRESSOR.get(), 1)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', OredustryItems.RADIATOR.get())
                .define('E', OredustryItems.ENERGY_CELL.get())
                .define('F', OredustryItems.FUEL_CELL.get())
                .define('H', OredustryItems.HEAT_CELL.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("CRC")
                .pattern("EFH")
                .pattern("SSS")
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_energy_cell", has(OredustryItems.ENERGY_CELL.get()))
                .unlockedBy("has_fuel_cell", has(OredustryItems.FUEL_CELL.get()))
                .unlockedBy("has_heat_cell", has(OredustryItems.HEAT_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.CRUCIBLE.get(), 1)
                .define('O', Tags.Items.OBSIDIAN)
                .define('A', Items.WATER_BUCKET)
                .define('G', Tags.Items.GLASS)
                .define('H', OredustryItems.HEAT_CELL.get())
                .define('B', Blocks.BRICKS)
                .pattern("OAO")
                .pattern("GHG")
                .pattern("BBB")
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN))
                .unlockedBy("has_heat_cell", has(OredustryItems.HEAT_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.PURIFIER.get(), 1)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', OredustryItems.RADIATOR.get())
                .define('E', OredustryItems.ENERGY_CELL.get())
                .define('G', Tags.Items.GLASS)
                .define('F', OredustryItems.FUEL_CELL.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("CRC")
                .pattern("EGF")
                .pattern("SSS")
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_energy_cell", has(OredustryItems.ENERGY_CELL.get()))
                .unlockedBy("has_fuel_cell", has(OredustryItems.FUEL_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.SEPARATOR.get(), 1)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Items.IRON_PICKAXE)
                .define('E', OredustryItems.ENERGY_CELL.get())
                .define('G', Tags.Items.GLASS)
                .define('F', OredustryItems.FUEL_CELL.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("CIC")
                .pattern("EGF")
                .pattern("SSS")
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_energy_cell", has(OredustryItems.ENERGY_CELL.get()))
                .unlockedBy("has_fuel_cell", has(OredustryItems.FUEL_CELL.get())).save(consumer);

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
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.GLASS)
                .define('B', Tags.Items.STORAGE_BLOCKS_COAL)
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('R', OredustryItems.RADIATOR.get())
                .pattern("CIC")
                .pattern("GBG")
                .pattern("SRS")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_coal", has(Tags.Items.STORAGE_BLOCKS_COAL)).save(consumer);

        ShapedRecipeBuilder.shaped(OredustryItems.ENERGY_CELL.get(), 1)
                .define('P', ItemTags.PLANKS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('S', Blocks.STONE)
                .pattern(" P ")
                .pattern("IRI")
                .pattern(" S ")
                .unlockedBy("has_redstone", has(Tags.Items.STORAGE_BLOCKS_REDSTONE)).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryItems.FUEL_CELL.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Items.BLAZE_POWDER)
                .define('L', Items.LAVA_BUCKET)
                .pattern(" I ")
                .pattern("BLB")
                .pattern(" I ")
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryItems.HEAT_CELL.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('C', Tags.Items.STORAGE_BLOCKS_COPPER)
                .pattern(" I ")
                .pattern("RCR")
                .pattern(" I ")
                .unlockedBy("has_copper", has(Tags.Items.STORAGE_BLOCKS_COPPER)).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryItems.RADIATOR.get(), 1)
                .define('G', Tags.Items.GLASS)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('E', OredustryItems.ENERGY_CELL.get())
                .pattern("GIG")
                .pattern("CEC")
                .pattern("GIG")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_copper", has(Tags.Items.INGOTS_COPPER))
                .unlockedBy("has_energy_cell", has(OredustryItems.ENERGY_CELL.get())).save(consumer);
    }
}
