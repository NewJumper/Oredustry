package com.newjumper.oredustry.integration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.recipes.SeparatingRecipe;
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
public class SeparatingCategory implements IRecipeCategory<SeparatingRecipe> {
    public final static ResourceLocation TEXTURE = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/separator.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> progress;
    private final int time;

    public SeparatingCategory(IGuiHelper guiHelper, int time) {
        this.background = guiHelper.createDrawable(TEXTURE, 59, 14, 80, 58);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(OredustryBlocks.SEPARATOR.get()));

        this.progress = CacheBuilder.newBuilder().maximumSize(23).build(new CacheLoader<>() {
            @Override
            public IDrawableAnimated load(Integer time) {
                return guiHelper.drawableBuilder(TEXTURE, 176, 14, 22, 21).buildAnimated(time, IDrawableAnimated.StartDirection.LEFT, false);
            }
        });
        this.time = time;
    }

    @Override
    public RecipeType<SeparatingRecipe> getRecipeType() {
        return RecipeType.create(Oredustry.MOD_ID, "separating", SeparatingRecipe.class);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.SEPARATOR.getId().getPath());
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
    public void setRecipe(IRecipeLayoutBuilder builder, SeparatingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 21).addIngredients(recipe.getOre());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 7).addItemStack(RecipeUtil.getResultItem(recipe));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 57, 35).addItemStack(recipe.getResultBlock());
    }

    @Override
    public void draw(SeparatingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.progress.getUnchecked(time / 2).draw(guiGraphics, 23, 19);

        Font font = Minecraft.getInstance().font;
        int stringWidth = font.width(time / 20 + "s");
        guiGraphics.drawString(font, time / 20 + "s", background.getWidth() - stringWidth - 32, background.getHeight() - 8, 0x808080, false);
    }
}
