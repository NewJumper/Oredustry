package com.newjumper.oredustry.content.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.gui.MachineButton;
import com.newjumper.oredustry.content.gui.menu.MinerMenu;
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

import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class MinerScreen extends AbstractContainerScreen<MinerMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/miner.png");
    public static final ResourceLocation UPGRADES = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/upgrades.png");

    private boolean upgradesGUI;
    private MachineButton powerButton;

    public MinerScreen(MinerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 204;
        this.imageHeight = 246;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelX = 22;
        this.inventoryLabelY = 152;
        this.powerButton = new MachineButton(leftPos + 183, topPos + 24, 8, 10);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        super.renderLabels(pGuiGraphics, pMouseX, pMouseY);

        if(isHovering(14, 79, 91, 8, pMouseX, pMouseY)) pGuiGraphics.renderTooltip(this.font, List.of(Component.literal((int) (menu.drawProgress() / 0.93) + "%")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        if(isHovering(111, 79, 79, 8, pMouseX, pMouseY)) {
            int time = menu.data.get(3) / 20;
            pGuiGraphics.renderTooltip(this.font, List.of(Component.literal(time + (time != 1 ? " seconds" : " second"))), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        pGuiGraphics.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);
        if(isHovering(183, 24, 8, 10, pMouseX, pMouseY)) pGuiGraphics.blit(GUI, x + 183, y + 24, 204 + 8 * menu.data.get(0), 10, 8, 10);
        else pGuiGraphics.blit(GUI, x + 183, y + 24, 204 + 8 * menu.data.get(0), 0, 8, 10);
        if(menu.drawProgress() > 0) pGuiGraphics.blit(GUI, x + 15, y + 80, 0, 246, menu.drawProgress(), 6);
        pGuiGraphics.blit(GUI, x + 112, y + 80, 204, 20 + menu.data.get(2) * 12, 52, 6);
        pGuiGraphics.blit(GUI, x + 164, y + 80, 204, 26 + menu.data.get(2) * 12, 25, 6);

        RenderSystem.setShaderTexture(0, UPGRADES);
        if(upgradesGUI) {
            pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y, 0, 0, 64, 42);
            for(int i = 0; i < 2; i++) pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y + 42 + i * 18, 0, 24, 64, 26);
        }
        else pGuiGraphics.blit(UPGRADES, x + imageWidth - 3, y, 64, 0, 23, 26);
        for(Slot slot : menu.upgradeSlots) ((UpgradeSlot) slot).setActive(upgradesGUI);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int state = menu.data.get(0);
        if(state == 1 || state == 3) state = 2;
        else if(state == 2) state = 3;
        powerButton.onClick(pMouseX, pMouseY, menu.blockEntity.getBlockPos(), 0, state);

        if(isHovering(imageWidth - 1, 5, 16, 16, pMouseX, pMouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.get(), 0.6f, 0.3f));
            upgradesGUI = !upgradesGUI;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
