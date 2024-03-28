package com.newjumper.oredustry.content.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.gui.menu.SeparatorMenu;
import com.newjumper.oredustry.content.gui.slot.UpgradeSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

@SuppressWarnings("NullableProblems")
public class SeparatorScreen extends AbstractContainerScreen<SeparatorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/separator.png");
    public static final ResourceLocation UPGRADES = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/upgrades.png");

    private boolean upgradesGUI;

    public SeparatorScreen(SeparatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        pGuiGraphics.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.drawFuel() > -1) pGuiGraphics.blit(GUI, x + 39, y + 17 + menu.drawFuel(), 176, menu.drawFuel(), 14, 14 - menu.drawFuel());
        if(menu.drawProgress() > 0) pGuiGraphics.blit(GUI, x + 82, y + 33, 176, 14, menu.drawProgress(), 21);

        RenderSystem.setShaderTexture(0, UPGRADES);
        if(upgradesGUI) {
            pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y, 0, 0, 64, 42);
            for(int i = 0; i < SeparatorMenu.MENU_SLOTS - 5; i++) pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y + 42, 0, 24, 64, 26);
        }
        else pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y, 64, 0, 23, 26);
        for(Slot slot : menu.upgradeSlots) ((UpgradeSlot) slot).setActive(upgradesGUI);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(isHovering(imageWidth - 1, 5, 16, 16, pMouseX, pMouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 0.6f, 0.3f));
            upgradesGUI = !upgradesGUI;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
