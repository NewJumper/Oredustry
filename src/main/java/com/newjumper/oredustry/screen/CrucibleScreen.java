package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class CrucibleScreen extends AbstractContainerScreen<CrucibleMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/crucible.png");

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

        if(isHovering(66, 18, 12, 52, pMouseX, pMouseY)) renderTooltip(pPoseStack, List.of(Component.literal(menu.getLiquid() + " mB")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        if(isHovering(111, 54, 8, 16, pMouseX, pMouseY)) renderTooltip(pPoseStack, List.of(Component.literal(menu.getWater() + " mB")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.drawFuel() > -1) this.blit(pPoseStack, x + 22, y + 36 + menu.drawFuel(), 176, menu.drawFuel(), 14, 14 - menu.drawFuel());
        if(menu.drawProgress() > 0) this.blit(pPoseStack, x + 44, y + 51 - menu.drawProgress(), 176, 27 - menu.drawProgress(), 14, menu.drawProgress() + 1);
        if(menu.drawCoolingProgress() > 0) this.blit(pPoseStack, x + 85, y + 23, 176, 28, menu.drawCoolingProgress(), 13);
        if(menu.drawLiquid() > 0) this.blit(pPoseStack, x + 66, y + 70 - menu.drawLiquid(), 176, 93 - menu.drawLiquid(), 12, menu.drawLiquid());
        if(menu.drawWater() > 0) this.blit(pPoseStack, x + 111, y + 70 - menu.drawWater(), 188, 57 - menu.drawWater(), 8, menu.drawWater());
    }
}
