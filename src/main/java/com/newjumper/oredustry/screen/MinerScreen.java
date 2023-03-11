package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.entity.MinerBlockEntity;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class MinerScreen extends AbstractContainerScreen<MinerMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/miner.png");

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

        if(isHovering(14, 79, 91, 8, pMouseX, pMouseY)) renderTooltip(pPoseStack, List.of(Component.literal((int) (menu.drawProgress() / 0.93) + "%")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        if(isHovering(111, 79, 79, 8, pMouseX, pMouseY)) renderTooltip(pPoseStack, List.of(Component.literal(20.0 / MinerBlockEntity.LIMIT + " blocks/s")), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.drawProgress() > 0) this.blit(pPoseStack, x + 15, y + 80, 0, 246, menu.drawProgress(), 6);
        if(isHovering(183, 24, 8, 10, pMouseX, pMouseY)) {
            this.blit(pPoseStack, x + 183, y + 24, 204 + 8 * menu.getState(), 10, 8, 10);
        } else {
            this.blit(pPoseStack, x + 183, y + 24, 204 + 8 * menu.getState(), 0, 8, 10);
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int state = menu.getState();
        if(isHovering(183, 24, 8, 10, pMouseX, pMouseY)) {
            if(state == 0 || state == 3) menu.setState(2);
            else if(state == 2) menu.setState(3);
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
