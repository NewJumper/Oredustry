package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/energy_generator.png");
    private int save = 0;

    public EnergyGeneratorScreen(EnergyGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
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
        this.font.draw(pPoseStack, this.title, (this.imageWidth - this.font.width(this.title)) / 2f, 14, 0x41C479);
        this.font.draw(pPoseStack, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 14, 0x404040);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        // TODO: only draw electricity symbol when the generator is actually producing FE
        // this IF statement does not work btw, but its a good start.. i think
        if(this.menu.drawEnergy() > save) {
            save = this.menu.drawEnergy();
            this.blit(pPoseStack, x + 79, y + 47, 192, 17, 18, 26);
        }

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(this.menu.isLit()) this.blit(pPoseStack, x + 79, y + 28, 192, 0, 18, 17);
        if(this.menu.drawEnergy() > 0) this.blit(pPoseStack, x + 113, y + 64, 192, 91, this.menu.drawEnergy(), 8);
    }
}
