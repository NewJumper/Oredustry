package com.newjumper.oredustry.content.recipes;

import com.google.gson.JsonObject;
import com.newjumper.oredustry.content.OredustryRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

@SuppressWarnings("NullableProblems")
public class CompressingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final ItemStack result;
    private final float experience;
    private final int time;

    public CompressingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result, float experience, int time) {
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.time = time;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return ingredient.test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result;
    }

    public float getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return OredustryRecipes.COMPRESSING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CompressingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<CompressingRecipe> {
        @Override
        public CompressingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredient"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0);
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time", 200);

            return new CompressingRecipe(pRecipeId, ingredient, result, experience, time);
        }

        @Override
        public CompressingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            float experience = pBuffer.readFloat();
            int time = pBuffer.readVarInt();

            return new CompressingRecipe(pRecipeId, ingredient, result, experience, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CompressingRecipe pRecipe) {
            pRecipe.ingredient.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pBuffer.writeFloat(pRecipe.experience);
            pBuffer.writeVarInt(pRecipe.time);
        }
    }
}
