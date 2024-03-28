package com.newjumper.oredustry.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.recipes.CompressingRecipe;
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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("NullableProblems")
public class CompressingCategory implements IRecipeCategory<CompressingRecipe> {
    public final static ResourceLocation TEXTURE = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/compressor.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> progress;
    private final int time;

    public CompressingCategory(IGuiHelper guiHelper, int time) {
        this.background = guiHelper.createDrawable(TEXTURE, 59, 18, 82, 50);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(OredustryBlocks.COMPRESSOR.get()));

        this.progress = CacheBuilder.newBuilder().maximumSize(21).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer time) {
                return guiHelper.drawableBuilder(TEXTURE, 176, 36, 20, 13).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
            }
        });
        this.time = time;
    }

    @Override
    public RecipeType<CompressingRecipe> getRecipeType() {
        return RecipeType.create(Oredustry.MOD_ID, "compressing", CompressingRecipe.class);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.COMPRESSOR.getId().getPath());
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
    public void setRecipe(IRecipeLayoutBuilder builder, CompressingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 17).addIngredients(recipe.getIngredient());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 59, 17).addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public void draw(CompressingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.progress.getUnchecked(time / 2).draw(guiGraphics, 28, 19);
        Font font = Minecraft.getInstance().font;
        int stringWidth = font.width(time / 20 + "s");
        guiGraphics.drawString(font, time / 20 + "s", background.getWidth() - stringWidth, background.getHeight() - 7, 0xff808080);
    }
}
