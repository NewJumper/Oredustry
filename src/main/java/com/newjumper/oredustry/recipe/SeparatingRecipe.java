package com.newjumper.oredustry.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

@SuppressWarnings("NullableProblems")
public class SeparatingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ore;
    private final ItemStack resultItem;
    private final ItemStack resultBlock;
    private final float experience;
    private final int time;

    public SeparatingRecipe(ResourceLocation id, Ingredient ore, ItemStack resultItem, ItemStack resultOre, float experience, int time) {
        this.id = id;
        this.ore = ore;
        this.resultItem = resultItem;
        this.resultBlock = resultOre;
        this.experience = experience;
        this.time = time;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return ore.test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return resultItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getOre() {
        return ore;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return resultItem;
    }

    public ItemStack getResultBlock() {
        return resultBlock.copy();
    }

    public float getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return OredustryRecipes.SEPARATING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SeparatingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<SeparatingRecipe> {
        @Override
        public SeparatingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ore = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ore"));
            ItemStack resultItem = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "resultitem"));
            ItemStack resultBlock = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "resultblock"));
            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0);
            int time = GsonHelper.getAsInt(pSerializedRecipe, "time", 200);

            return new SeparatingRecipe(pRecipeId, ore, resultItem, resultBlock, experience, time);
        }

        @Override
        public SeparatingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ore = Ingredient.fromNetwork(pBuffer);
            ItemStack resultItem = pBuffer.readItem();
            ItemStack resultBlock = pBuffer.readItem();
            float experience = pBuffer.readFloat();
            int time = pBuffer.readVarInt();

            return new SeparatingRecipe(pRecipeId, ore, resultItem, resultBlock, experience, time);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SeparatingRecipe pRecipe) {
            pRecipe.ore.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.resultItem);
            pBuffer.writeItem(pRecipe.resultBlock);
            pBuffer.writeFloat(pRecipe.experience);
            pBuffer.writeVarInt(pRecipe.time);
        }
    }
}
