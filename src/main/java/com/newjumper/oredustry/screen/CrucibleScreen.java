package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.screen.slot.UpgradeSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class CrucibleScreen extends AbstractContainerScreen<CrucibleMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/crucible.png");
    public static final ResourceLocation UPGRADES = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/upgrades.png");

    private boolean upgradesGUI;

    public CrucibleScreen(CrucibleMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        super.renderLabels(pPoseStack, pMouseX, pMouseY);

        if(isHovering(66, 18, 12, 52, pMouseX, pMouseY)) {
            List<Component> tooltip = new ArrayList<>();
            if(menu.blockEntity.liquid != null) tooltip.add(Component.translatable("liquid." + Oredustry.MOD_ID + "." + menu.blockEntity.liquid.getName()));
            tooltip.add(Component.literal(menu.getLiquid() + " mB"));

            renderTooltip(pPoseStack, tooltip, Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        }

        if(isHovering(111, 54, 8, 16, pMouseX, pMouseY)) renderTooltip(pPoseStack, List.of(Component.literal("Water"), Component.literal(menu.getWater() + " mB")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.drawFuel() > -1) this.blit(pPoseStack, x + 22, y + 32 + menu.drawFuel(), 176, menu.drawFuel(), 14, 14 - menu.drawFuel());
        if(menu.drawProgress() > 0) this.blit(pPoseStack, x + 44, y + 47 - menu.drawProgress(), 176, 27 - menu.drawProgress(), 14, menu.drawProgress() + 1);
        if(menu.drawCoolingProgress() > 0) this.blit(pPoseStack, x + 85, y + 33, 176, 28, menu.drawCoolingProgress(), 13);
        if(menu.drawLiquid() > 0) this.blit(pPoseStack, x + 66, y + 65 - menu.drawLiquid(), 176, 85 - menu.drawLiquid(), 12, menu.drawLiquid());
        if(menu.drawWater() > 0) this.blit(pPoseStack, x + 111, y + 66 - menu.drawWater(), 188, 57 - menu.drawWater(), 8, menu.drawWater());

        RenderSystem.setShaderTexture(0, UPGRADES);
        if(upgradesGUI) {
            this.blit(pPoseStack, x + imageWidth - 3, y, 0, 0, 64, 42);
            for(int i = 0; i < CrucibleMenu.MENU_SLOTS - 5; i++) this.blit(pPoseStack, x + imageWidth - 3, y + 42, 0, 24, 64, 26);
        }
        else this.blit(pPoseStack, x + imageWidth - 3, y, 64, 0, 23, 26);
        for(Slot slot : menu.upgradeSlots) ((UpgradeSlot) slot).setActive(upgradesGUI);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if(isHovering(imageWidth - 1, 5, 16, 16, pMouseX, pMouseY)) {
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6f, 0.3f));
            upgradesGUI = !upgradesGUI;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
