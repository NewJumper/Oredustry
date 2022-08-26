package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.screen.renderer.ToolTipRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SeparatorScreen extends AbstractContainerScreen<SeparatorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/separator.png");
    private final ToolTipRenderer<IEnergyStorage> energyToolTip = new ToolTipRenderer<>(this.menu.blockEntity.energyStorage);

    public SeparatorScreen(SeparatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        this.font.draw(pPoseStack, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 14, 0x404040);

        if(isHovering(164, 21, 12, 58, pMouseX, pMouseY)) {
            renderTooltip(pPoseStack, energyToolTip.getToolTip(), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        }
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.drawProgress() > 0) blit(pPoseStack, x + 80, y + 39, 232, 0, menu.drawProgress(), 22);
        if(menu.drawFuel() > -1) {
            this.blit(pPoseStack, x + 16, y + 21, 192, 0, 12, 58);
            this.blit(pPoseStack, x + 18, y + 23 + menu.drawFuel(), 216, menu.drawFuel(), 8, 54 - menu.drawFuel());
        }
        if(this.menu.drawEnergy() > 0) {
            this.blit(pPoseStack, x + 164, y + 21, 204, 0, 12, 58);
            this.blit(pPoseStack, x + 166, y + 77 - this.menu.drawEnergy(), 224, 54 - this.menu.drawEnergy(), 8, this.menu.drawEnergy());
        }
    }
}
