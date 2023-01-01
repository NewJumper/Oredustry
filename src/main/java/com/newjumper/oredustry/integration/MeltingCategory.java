package com.newjumper.oredustry.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.MeltingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("NullableProblems")
public class MeltingCategory implements IRecipeCategory<MeltingRecipe> {
    public final static ResourceLocation TEXTURE = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/crucible.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> meltingProgress;
    private final LoadingCache<Integer, IDrawableAnimated> coolingProgress;
    private final int time;

    public MeltingCategory(IGuiHelper guiHelper, int time) {
        this.background = guiHelper.createDrawable(TEXTURE, 20, 18, 136, 50);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(OredustryBlocks.CRUCIBLE.get()));

        this.meltingProgress = CacheBuilder.newBuilder().maximumSize(15).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer time) {
                return guiHelper.drawableBuilder(TEXTURE, 176, 14, 14, 14).buildAnimated(time, IDrawableAnimated.StartDirection.BOTTOM, false);
            }
        });
        this.coolingProgress = CacheBuilder.newBuilder().maximumSize(38).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer time) {
                return guiHelper.drawableBuilder(TEXTURE, 176, 28, 37, 13).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
            }
        });
        this.time = time;
    }

    @Override
    public RecipeType<MeltingRecipe> getRecipeType() {
        return RecipeType.create(Oredustry.MOD_ID, "melting", MeltingRecipe.class);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.CRUCIBLE.getId().getPath());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MeltingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 32).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.INPUT, 66, 32).addIngredients(Ingredient.of(Items.WATER_BUCKET));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 113, 17).addItemStack(recipe.getResultItem());
    }

    @Override
    public void draw(MeltingRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.meltingProgress.getUnchecked(time / 3).draw(stack, 24, 16);
        this.coolingProgress.getUnchecked(time / 2).draw(stack, 65, 15);

        Font fontRenderer = Minecraft.getInstance().font;
        int stringWidth = fontRenderer.width(time / 20.0 + "s");
        fontRenderer.draw(stack, time / 20.0 + "s", background.getWidth() - stringWidth, background.getHeight() - 7, 0xff808080);
    }
}
