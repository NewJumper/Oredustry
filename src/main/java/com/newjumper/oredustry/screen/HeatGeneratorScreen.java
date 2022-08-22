package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class HeatGeneratorScreen extends AbstractContainerScreen<HeatGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/heat_generator.png");

    public HeatGeneratorScreen(HeatGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 192;
        this.imageHeight = 180;
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, (this.imageWidth - this.font.width(this.title)) / 2f, 10, 0x404040);
        this.font.draw(pPoseStack, this.playerInventoryTitle, this.inventoryLabelX + 8, this.inventoryLabelY + 14, 0x404040);

        TextComponent heat = new TextComponent(this.menu.getHeat() + "°");
        this.font.draw(pPoseStack, heat, (this.imageWidth - this.font.width(heat)) / 2f + 1, 66, 0xffffff);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(this.menu.getHeat() > 0) {
            int frame = (int) (this.menu.blockEntity.getLevel().getGameTime() / 5) % 4;
            this.blit(pPoseStack, x + 86, y + 30, 192, frame * 28, 20, 28);
        }
    }
}
