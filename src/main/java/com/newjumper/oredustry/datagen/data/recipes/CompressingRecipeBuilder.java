package com.newjumper.oredustry.datagen.data.recipes;

import com.google.gson.JsonObject;
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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

@SuppressWarnings("NullableProblems")
public class CompressingRecipeBuilder implements RecipeBuilder {
    private final Ingredient ingredient;
    private final Item result;
    private final int count;
    private final float experience;
    private final int time;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();

    public CompressingRecipeBuilder(TagKey<Item> ingredient, ItemLike result, int count, float experience) {
        this(ingredient, result, count, experience, 200);
    }

    public CompressingRecipeBuilder(TagKey<Item> ingredient, ItemLike result, int count, float experience, int time) {
        this.ingredient = Ingredient.of(ingredient);
        this.result = result.asItem();
        this.count = count;
        this.experience = experience;
        this.time = time;
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
        return result;
    }

    @Override
    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation recipeId) {
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId)).rewards(AdvancementRewards.Builder.recipe(recipeId)).requirements(RequirementsStrategy.OR);
        consumer.accept(new CompressingRecipeBuilder.Result(recipeId, this.ingredient, this.result, this.count, this.experience, this.time, this.advancement, new ResourceLocation(recipeId.getNamespace(), "recipes/" + RecipeCategory.MISC.getFolderName() + "/" + recipeId.getPath())));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient ingredient;
        private final Item result;
        private final int count;
        private final float experience;
        private final int time;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient ingredient, Item result, int count, float experience, int time, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.count = count;
            this.experience = experience;
            this.time = time;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredient", this.ingredient.toJson());

            JsonObject itemJson = new JsonObject();
            itemJson.addProperty("item", String.valueOf(ForgeRegistries.ITEMS.getKey(result)));
            pJson.add("result", itemJson);
            itemJson.addProperty("count", count);

            if(experience != 0) pJson.addProperty("experience", experience);
            if(time != 200) pJson.addProperty("time", time);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return OredustryRecipes.COMPRESSING.get();
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
