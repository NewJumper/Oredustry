package com.newjumper.oredustry.datagen.data.recipes;

import com.google.gson.JsonObject;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.OredustryRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

@SuppressWarnings("NullableProblems")
public class SeparatingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ore;
    private final Item resultItem;
    private Item resultBlock;
    private final int count;
    private final float experience;
    private final int time;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public SeparatingRecipeBuilder(ItemLike ore, ItemLike resultItem, int count, float experience) {
        this(ore, resultItem, count, experience, 200);
    }

    public SeparatingRecipeBuilder(ItemLike ore, ItemLike resultItem, int count, float experience, int time) {
        this.ore = Ingredient.of(ore);
        this.resultItem = resultItem.asItem();
        this.count = count;
        this.experience = experience;
        this.time = time;
    }

    public SeparatingRecipeBuilder stone() {
        this.resultBlock = Blocks.STONE.asItem();
        return this;
    }

    public SeparatingRecipeBuilder deepslate() {
        this.resultBlock = Blocks.DEEPSLATE.asItem();
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    @Override
    public RecipeBuilder group(String pGroupName) {
        return this;
    }

    @Override
    public Item getResult() {
        return resultItem;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer) {
        String name = this.ore.getItems()[0].toString().replace("1 ", "");
        this.save(consumer, new ResourceLocation(Oredustry.MOD_ID, name + "_separating"));
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        consumer.accept(new SeparatingRecipeBuilder.Result(recipeId, this.ore, this.resultItem, this.resultBlock, this.count, this.experience, this.time, this.advancement, new ResourceLocation(recipeId.getNamespace(), "recipes/" + RecipeCategory.MISC.getFolderName() + "/" + recipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ore;
        private final Item resultItem;
        private final Item resultBlock;
        private final int count;
        private final float experience;
        private final int time;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient ore, Item resultItem, Item resultBlock, int count, float experience, int time, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.ore = ore;
            this.resultItem = resultItem;
            this.resultBlock = resultBlock;
            this.count = count;
            this.experience = experience;
            this.time = time;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ore", this.ore.toJson());

            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", String.valueOf(ForgeRegistries.ITEMS.getKey(resultItem)));
            pJson.add("resultitem", itemJson);
            itemJson.addProperty("count", count);

            JsonObject blockJson = new JsonObject();
            blockJson.addProperty("item", String.valueOf(ForgeRegistries.ITEMS.getKey(resultBlock)));
            pJson.add("resultblock", blockJson);

            if(experience != 0) pJson.addProperty("experience", experience);
            if(time != 200) pJson.addProperty("time", time);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return OredustryRecipes.SEPARATING.get();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
