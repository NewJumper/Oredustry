package com.newjumper.oredustry.datagen.data.recipes;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.OredustryItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class OredustryRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public OredustryRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryBlocks.MACHINE_FRAME.get(), 1)
                .define('I', Tags.Items.INGOTS_IRON)
                .define('G', Tags.Items.GLASS)
                .define('O', Tags.Items.OBSIDIAN)
                .pattern("IGI")
                .pattern("GOG")
                .pattern("IGI")
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .unlockedBy("has_obsidian", has(Tags.Items.OBSIDIAN)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryBlocks.COMPRESSOR.get(), 1)
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
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryBlocks.CRUCIBLE.get(), 1)
                .define('L', Items.LAVA_BUCKET)
                .define('C', OredustryItems.CONDUCTION_CELL.get())
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .define('B', Blocks.BRICKS)
                .pattern(" L ")
                .pattern("CFC")
                .pattern("BBB")
                .unlockedBy("has_conduction_cell", has(OredustryItems.CONDUCTION_CELL.get()))
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryBlocks.MINER.get(), 1)
                .define('R', Tags.Items.STORAGE_BLOCKS_REDSTONE)
                .define('D', Tags.Items.STORAGE_BLOCKS_DIAMOND)
                .define('N', Tags.Items.INGOTS_NETHERITE)
                .define('S', OredustryBlocks.SEPARATOR.get())
                .define('O', Blocks.OBSIDIAN)
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .pattern("RDR")
                .pattern("NSN")
                .pattern("OFO")
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryBlocks.SEPARATOR.get(), 1)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('P', Items.IRON_PICKAXE)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .define('F', OredustryBlocks.MACHINE_FRAME.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("RPR")
                .pattern("IFI")
                .pattern("SSS")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get()))
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryItems.CONDUCTION_CELL.get(), 1)
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .pattern(" C ")
                .pattern("CDC")
                .pattern(" I ")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, OredustryItems.INDUCTION_CELL.get(), 1)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .pattern(" R ")
                .pattern("RDR")
                .pattern(" R ")
                .unlockedBy("has_redstone", has(Tags.Items.DUSTS_REDSTONE))
                .unlockedBy("has_diamond", has(Tags.Items.GEMS_DIAMOND)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OredustryItems.COMPRESSOR_UPGRADE.get(), 1)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .define('A', Blocks.ANVIL)
                .define('C', OredustryItems.CONDUCTION_CELL.get())
                .define('O', Tags.Items.OBSIDIAN)
                .pattern("IAI")
                .pattern("C C")
                .pattern("OOO")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get()))
                .unlockedBy("has_conduction_cell", has(OredustryItems.CONDUCTION_CELL.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OredustryItems.CRUCIBLE_UPGRADE.get(), 1)
                .define('L', Items.LAVA_BUCKET)
                .define('C', OredustryItems.CONDUCTION_CELL.get())
                .define('B', Blocks.BRICKS)
                .pattern(" L ")
                .pattern("C C")
                .pattern("BBB")
                .unlockedBy("has_conduction_cell", has(OredustryItems.CONDUCTION_CELL.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, OredustryItems.SEPARATOR_UPGRADE.get(), 1)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('P', Items.IRON_PICKAXE)
                .define('I', OredustryItems.INDUCTION_CELL.get())
                .define('S', ItemTags.STONE_CRAFTING_MATERIALS)
                .pattern("RPR")
                .pattern("I I")
                .pattern("SSS")
                .unlockedBy("has_induction_cell", has(OredustryItems.INDUCTION_CELL.get())).save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OredustryItems.UPGRADE_BASE.get(), 1)
                .requires(OredustryBlocks.MACHINE_FRAME.get(), 1)
                .requires(Ingredient.of(Tags.Items.GEMS_DIAMOND), 4)
                .unlockedBy("has_machine_frame", has(OredustryBlocks.MACHINE_FRAME.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OredustryItems.FUEL_UPGRADE.get(), 1)
                .requires(OredustryItems.UPGRADE_BASE.get(), 1)
                .requires(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1)
                .requires(Ingredient.of(Tags.Items.STORAGE_BLOCKS_COAL), 1)
                .requires(Items.LAVA_BUCKET, 1)
                .unlockedBy("has_upgrade_base", has(OredustryItems.UPGRADE_BASE.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OredustryItems.RANGE_UPGRADE.get(), 1)
                .requires(OredustryItems.UPGRADE_BASE.get(), 1)
                .requires(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1)
                .requires(Ingredient.of(Tags.Items.STORAGE_BLOCKS_EMERALD), 1)
                .requires(Items.ECHO_SHARD, 1)
                .unlockedBy("has_upgrade_base", has(OredustryItems.UPGRADE_BASE.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OredustryItems.SPEED_UPGRADE.get(), 1)
                .requires(OredustryItems.UPGRADE_BASE.get(), 1)
                .requires(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1)
                .requires(Ingredient.of(Tags.Items.STORAGE_BLOCKS_DIAMOND), 1)
                .requires(Items.SUGAR, 1)
                .unlockedBy("has_upgrade_base", has(OredustryItems.UPGRADE_BASE.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, OredustryItems.STORAGE_UPGRADE.get(), 1)
                .requires(OredustryItems.UPGRADE_BASE.get(), 1)
                .requires(Ingredient.of(Tags.Items.DUSTS_REDSTONE), 1)
                .requires(Ingredient.of(Tags.Items.STORAGE_BLOCKS_IRON), 1)
                .requires(Blocks.CHEST, 1)
                .unlockedBy("has_upgrade_base", has(OredustryItems.UPGRADE_BASE.get())).save(pWriter);

        new CompressingRecipeBuilder(Tags.Items.ORES_COAL, OredustryItems.DENSE_COAL.get(), 2, 0.2f).unlockedBy("has_coal_ore", has(Tags.Items.ORES_COAL))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_COAL.get()) + "_from_compressing_" + getItemName(Blocks.COAL_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_IRON, OredustryItems.DENSE_RAW_IRON.get(), 2, 1.4f).unlockedBy("has_iron_ore", has(Tags.Items.ORES_IRON))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_RAW_IRON.get()) + "_from_compressing_" + getItemName(Blocks.IRON_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_COPPER, OredustryItems.DENSE_RAW_COPPER.get(), 2, 1.4f).unlockedBy("has_copper_ore", has(Tags.Items.ORES_COPPER))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_RAW_COPPER.get()) + "_from_compressing_" + getItemName(Blocks.COPPER_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_GOLD, OredustryItems.DENSE_RAW_GOLD.get(), 2, 2f).unlockedBy("has_gold_ore", has(Tags.Items.ORES_GOLD))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_RAW_GOLD.get()) + "_from_compressing_" + getItemName(Blocks.GOLD_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_REDSTONE, OredustryItems.DENSE_REDSTONE.get(), 2, 1f).unlockedBy("has_redstone_ore", has(Tags.Items.ORES_REDSTONE))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_REDSTONE.get()) + "_from_compressing_" + getItemName(Blocks.REDSTONE_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_EMERALD, OredustryItems.DENSE_EMERALD.get(), 2, 2.7f).unlockedBy("has_emerald_ore", has(Tags.Items.ORES_EMERALD))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_EMERALD.get()) + "_from_compressing_" + getItemName(Blocks.EMERALD_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_LAPIS, OredustryItems.DENSE_LAPIS.get(), 2, 1f).unlockedBy("has_lapis_ore", has(Tags.Items.ORES_LAPIS))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_LAPIS.get()) + "_from_compressing_" + getItemName(Blocks.LAPIS_ORE)));
        new CompressingRecipeBuilder(Tags.Items.ORES_DIAMOND, OredustryItems.DENSE_DIAMOND.get(), 2, 2.7f).unlockedBy("has_diamond_ore", has(Tags.Items.ORES_DIAMOND))
                .save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(OredustryItems.DENSE_DIAMOND.get()) + "_from_compressing_" + getItemName(Blocks.DIAMOND_ORE)));

        new MeltingRecipeBuilder(Tags.Items.ORES_COAL, Items.COAL, 5, 0.5f)
                .unlockedBy("has_coal_ore", has(Tags.Items.ORES_COAL)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COAL) + "_from_melting_coal_ore"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_COAL.get(), Items.COAL, 5, 0.6f)
                .unlockedBy("has_dense_coal", has(OredustryItems.DENSE_COAL.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COAL) + "_from_melting_" + getItemName(OredustryItems.DENSE_COAL.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_IRON, Items.IRON_INGOT, 3, 3.5f)
                .unlockedBy("has_iron_ore", has(Tags.Items.ORES_IRON)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.IRON_INGOT) + "_from_melting_iron_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_IRON, Items.IRON_INGOT, 4, 3.5f)
                .unlockedBy("has_raw_iron", has(Tags.Items.RAW_MATERIALS_IRON)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.IRON_INGOT) + "_from_melting_raw_iron"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_RAW_IRON.get(), Items.IRON_INGOT, 4, 3.7f)
                .unlockedBy("has_dense_raw_iron", has(OredustryItems.DENSE_RAW_IRON.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.IRON_INGOT) + "_from_melting_" + getItemName(OredustryItems.DENSE_RAW_IRON.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_COPPER, Items.COPPER_INGOT, 3, 3.5f)
                .unlockedBy("has_copper_ore", has(Tags.Items.ORES_COPPER)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COPPER_INGOT) + "_from_melting_copper_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_COPPER, Items.COPPER_INGOT, 4, 3.5f)
                .unlockedBy("has_raw_copper", has(Tags.Items.RAW_MATERIALS_COPPER)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COPPER_INGOT) + "_from_melting_raw_copper"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_RAW_COPPER.get(), Items.COPPER_INGOT, 4, 3.7f)
                .unlockedBy("has_dense_raw_copper", has(OredustryItems.DENSE_RAW_COPPER.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.COPPER_INGOT) + "_from_melting_" + getItemName(OredustryItems.DENSE_RAW_COPPER.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_GOLD, Items.GOLD_INGOT, 3, 5f)
                .unlockedBy("has_gold_ore", has(Tags.Items.ORES_GOLD)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.GOLD_INGOT) + "_from_melting_gold_ore"));
        new MeltingRecipeBuilder(Tags.Items.RAW_MATERIALS_GOLD, Items.GOLD_INGOT, 4, 5f)
                .unlockedBy("has_raw_gold", has(Tags.Items.RAW_MATERIALS_GOLD)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.GOLD_INGOT) + "_from_melting_raw_gold"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_RAW_GOLD.get(), Items.GOLD_INGOT, 4, 5.3f)
                .unlockedBy("has_dense_raw_gold", has(OredustryItems.DENSE_RAW_GOLD.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.GOLD_INGOT) + "_from_melting_" + getItemName(OredustryItems.DENSE_RAW_GOLD.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_REDSTONE, Items.REDSTONE, 5, 2.5f)
                .unlockedBy("has_redstone_ore", has(Tags.Items.ORES_REDSTONE)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.REDSTONE) + "_from_melting_redstone_ore"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_REDSTONE.get(), Items.REDSTONE, 5, 2.6f)
                .unlockedBy("has_dense_redstone", has(OredustryItems.DENSE_REDSTONE.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.REDSTONE) + "_from_melting_" + getItemName(OredustryItems.DENSE_REDSTONE.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_EMERALD, Items.EMERALD, 3, 6.8f)
                .unlockedBy("has_emerald_ore", has(Tags.Items.ORES_EMERALD)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.EMERALD) + "_from_melting_emerald_ore"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_EMERALD.get(), Items.EMERALD, 3, 7.1f)
                .unlockedBy("has_dense_emerald", has(OredustryItems.DENSE_EMERALD.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.EMERALD) + "_from_melting_" + getItemName(OredustryItems.DENSE_EMERALD.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_LAPIS, Items.LAPIS_LAZULI, 5, 2.5f)
                .unlockedBy("has_lapis_ore", has(Tags.Items.ORES_LAPIS)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.LAPIS_LAZULI) + "_from_melting_lapis_ore"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_LAPIS.get(), Items.LAPIS_LAZULI, 5, 2.6f)
                .unlockedBy("has_dense_lapis", has(OredustryItems.DENSE_LAPIS.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.LAPIS_LAZULI) + "_from_melting_" + getItemName(OredustryItems.DENSE_LAPIS.get())));

        new MeltingRecipeBuilder(Tags.Items.ORES_DIAMOND, Items.DIAMOND, 3, 6.8f)
                .unlockedBy("has_diamond_ore", has(Tags.Items.ORES_DIAMOND)).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.DIAMOND) + "_from_melting_diamond_ore"));
        new MeltingRecipeBuilder(OredustryItems.DENSE_DIAMOND.get(), Items.DIAMOND, 3, 7.1f)
                .unlockedBy("has_dense_diamond", has(OredustryItems.DENSE_DIAMOND.get())).save(pWriter, new ResourceLocation(Oredustry.MOD_ID, getItemName(Items.DIAMOND) + "_from_melting_" + getItemName(OredustryItems.DENSE_DIAMOND.get())));

        new SeparatingRecipeBuilder(Blocks.COAL_ORE, Items.COAL, 4, 1f, 100).stone().unlockedBy("has_coal_ore", has(Blocks.COAL_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COAL_ORE, Items.COAL, 4, 1f).deepslate().unlockedBy("has_deepslate_coal_ore", has(Blocks.DEEPSLATE_COAL_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.IRON_ORE, Items.RAW_IRON, 3, 3f, 100).stone().unlockedBy("has_iron_ore", has(Blocks.IRON_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_IRON_ORE, Items.RAW_IRON, 3, 3f).deepslate().unlockedBy("has_deepslate_iron_ore", has(Blocks.DEEPSLATE_IRON_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.COPPER_ORE, Items.RAW_COPPER, 3, 3f, 100).stone().unlockedBy("has_copper_ore", has(Blocks.COPPER_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_COPPER_ORE, Items.RAW_COPPER, 3, 3f).deepslate().unlockedBy("has_deepslate_copper_ore", has(Blocks.DEEPSLATE_COPPER_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.GOLD_ORE, Items.RAW_GOLD, 3, 3.6f, 100).stone().unlockedBy("has_gold_ore", has(Blocks.GOLD_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_GOLD_ORE, Items.RAW_GOLD, 3, 3.6f).deepslate().unlockedBy("has_deepslate_gold_ore", has(Blocks.DEEPSLATE_GOLD_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.REDSTONE_ORE, Items.REDSTONE, 4, 2f, 100).stone().unlockedBy("has_redstone_ore", has(Blocks.REDSTONE_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_REDSTONE_ORE, Items.REDSTONE, 4, 2f).deepslate().unlockedBy("has_deepslate_redstone_ore", has(Blocks.DEEPSLATE_REDSTONE_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.EMERALD_ORE, Items.EMERALD, 2, 5f, 100).stone().unlockedBy("has_emerald_ore", has(Blocks.EMERALD_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_EMERALD_ORE, Items.EMERALD, 2, 5f).deepslate().unlockedBy("has_deepslate_emerald_ore", has(Blocks.DEEPSLATE_EMERALD_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.LAPIS_ORE, Items.LAPIS_LAZULI, 4, 2f, 100).stone().unlockedBy("has_lapis_ore", has(Blocks.LAPIS_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_LAPIS_ORE, Items.LAPIS_LAZULI, 4, 2f).deepslate().unlockedBy("has_deepslate_lapis_ore", has(Blocks.DEEPSLATE_LAPIS_ORE)).save(pWriter);

        new SeparatingRecipeBuilder(Blocks.DIAMOND_ORE, Items.DIAMOND, 2, 5f, 100).stone().unlockedBy("has_diamond_ore", has(Blocks.DIAMOND_ORE)).save(pWriter);
        new SeparatingRecipeBuilder(Blocks.DEEPSLATE_DIAMOND_ORE, Items.DIAMOND, 2, 5f).deepslate().unlockedBy("has_deepslate_diamond_ore", has(Blocks.DEEPSLATE_DIAMOND_ORE)).save(pWriter);
    }
}
