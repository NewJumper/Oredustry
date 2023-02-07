package com.newjumper.oredustry.datagen.data.recipes;

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
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CraftingRecipesProvider extends RecipeProvider implements IConditionBuilder {
    public CraftingRecipesProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(OredustryBlocks.MACHINE_FRAME.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.GLASS)
                .define('O', Tags.Items.OBSIDIAN)
                .pattern("IGI")
                .pattern("GOG")
                .pattern("IGI")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN)).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.COMPRESSOR.get(), 1)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .define('A', Blocks.ANVIL)
                .define('C', OredustryItems.CONDUCTION_CELL.get())
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .define('O', Tags.Items.OBSIDIAN)
                .pattern("IAI")
                .pattern("CFC")
                .pattern("OOO")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get()))
                .unlockedBy("has_conduction_cell", has(OredustryItems.CONDUCTION_CELL.get()))
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.CRUCIBLE.get(), 1)
                .define('L', Items.LAVA_BUCKET)
                .define('C', OredustryItems.CONDUCTION_CELL.get())
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .define('B', Blocks.BRICKS)
                .pattern(" L ")
                .pattern("CFC")
                .pattern("BBB")
                .unlockedBy("has_conduction_cell", has(OredustryItems.CONDUCTION_CELL.get()))
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryBlocks.SEPARATOR.get(), 1)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('P', Items.IRON_PICKAXE)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("RPR")
                .pattern("IFI")
                .pattern("SSS")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get()))
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(consumer);

        ShapedRecipeBuilder.shaped(OredustryItems.CONDUCTION_CELL.get(), 1)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .pattern(" C ")
                .pattern("CDC")
                .pattern(" I ")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get())).save(consumer);
        ShapedRecipeBuilder.shaped(OredustryItems.INDUCTION_CELL.get(), 1)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .pattern(" R ")
                .pattern("RDR")
                .pattern(" R ")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(consumer);
    }

    @Override
    public @NotNull String getName() {
        return "Crafting Recipes";
    }
}
