package com.newjumper.oredustry.recipe;

import com.google.gson.JsonObject;
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
    private final Ingredient material;
    private final ItemStack result;
    private final float experience;
    private final int time;

    public CompressingRecipe(ResourceLocation pId, Ingredient pMaterial, ItemStack pResult, float pExperience, int pTime) {
        this.id = pId;
        this.material = pMaterial;
        this.result = pResult;
        this.experience = pExperience;
        this.time = pTime;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return material.test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getMaterial() {
        return material;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
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
            Ingredient material = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "material"));
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0);
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time", 200);

            return new CompressingRecipe(pRecipeId, material, result, experience, time);
        }

        @Override
        public CompressingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient material = Ingredient.fromNetwork(pBuffer);
            ItemStack result = pBuffer.readItem();
            float experience = pBuffer.readFloat();
            int time = pBuffer.readVarInt();

            return new CompressingRecipe(pRecipeId, material, result, experience, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CompressingRecipe pRecipe) {
            pRecipe.material.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.getResultItem());
            pBuffer.writeFloat(pRecipe.getExperience());
            pBuffer.writeVarInt(pRecipe.getTime());
        }
    }
}
