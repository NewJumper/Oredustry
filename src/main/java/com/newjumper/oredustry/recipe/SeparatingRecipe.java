package com.newjumper.oredustry.recipe;

import com.google.gson.JsonObject;
import com.newjumper.oredustry.Oredustry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class SeparatingRecipe implements Recipe<SimpleContainer> {
    private final ResourceLocation id;
    private final Ingredient ore;
    private final ItemStack resultOre;
    private final ItemStack resultRaw;
    protected final float experience;

    public SeparatingRecipe(ResourceLocation pId, Ingredient pOre, ItemStack pResultOre, ItemStack pResultRaw, float pExperience) {
        this.id = pId;
        this.ore = pOre;
        this.resultOre = pResultOre;
        this.resultRaw = pResultRaw;
        this.experience = pExperience;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        return ore.test(pContainer.getItem(1));
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return resultOre;
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
    public ItemStack getResultItem() {
        return resultRaw.copy();
    }
    public ItemStack getResultOre() {
        return resultOre.copy();
    }

    public float getExperience() {
        return experience;
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
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Oredustry.MOD_ID,"separating");

        @Override
        public SeparatingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ore = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "ore"));
            ItemStack resultOre = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "resultore"));
            ItemStack resultRaw = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "resultraw"));
            float experience = GsonHelper.getAsFloat(pSerializedRecipe, "experience", 0);

            return new SeparatingRecipe(pRecipeId, ore, resultOre, resultRaw, experience);
        }

        @Override
        public SeparatingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ore = Ingredient.fromNetwork(pBuffer);
            ItemStack resultOre = pBuffer.readItem();
            ItemStack resultRaw = pBuffer.readItem();
            float experience = pBuffer.readFloat();

            return new SeparatingRecipe(pRecipeId, ore, resultOre, resultRaw, experience);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, SeparatingRecipe pRecipe) {
            pRecipe.ore.toNetwork(pBuffer);
            pBuffer.writeItemStack(pRecipe.getResultItem(), false);
            pBuffer.writeItemStack(pRecipe.getResultOre(), false);
            pBuffer.writeFloat(pRecipe.getExperience());
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked")
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>) cls;
        }
    }
}
