package com.newjumper.oredustry.integration;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeUtil {
    public static ItemStack getResultItem(Recipe<?> recipe) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) throw new NullPointerException("level must not be null.");
        return recipe.getResultItem(level.registryAccess());
    }
}
