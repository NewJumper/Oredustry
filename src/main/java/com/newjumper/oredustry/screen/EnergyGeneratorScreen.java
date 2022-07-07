package com.newjumper.oredustry.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.screen.renderer.EnergyToolTip;
import com.newjumper.oredustry.screen.renderer.FluidToolTip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class EnergyGeneratorScreen extends AbstractContainerScreen<EnergyGeneratorMenu> {
    public static final ResourceLocation GUI = new ResourceLocation(Oredustry.MOD_ID, "textures/gui/container/energy_generator.png");
    private final EnergyToolTip energyToolTip = new EnergyToolTip(this.menu.blockEntity.energyStorage);
    private final FluidToolTip waterToolTip = new FluidToolTip(this.menu.blockEntity.fluidTank);

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
        this.font.draw(pPoseStack, this.title, (this.imageWidth - this.font.width(this.title)) / 2f, 13, 0x41C479);
        this.font.draw(pPoseStack, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 14, 0x404040);

        if(isHovering(114, 28, 26, 46, pMouseX, pMouseY)) {
            renderTooltip(pPoseStack, waterToolTip.getToolTip(), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        }
        if(isHovering(164, 16, 12, 58, pMouseX, pMouseY)) {
            renderTooltip(pPoseStack, energyToolTip.getToolTip(), Optional.empty(), pMouseX - this.leftPos, pMouseY - this.topPos);
        }
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
        int x = this.leftPos;
        int y = this.topPos;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(this.menu.isActive()) {
            this.blit(pPoseStack, x + 87, y + 27, 192, 0, 18, 18);
            if(this.menu.drawEnergy() < 54 && this.menu.drawWater() > 0) this.blit(pPoseStack, x + 87, y + 47, 192, 18, 18, 26);
        }

        if(this.menu.drawWater() > 0) {
            this.blit(pPoseStack, x + 114, y + 28, 192, 76, 26, 46);
            this.blit(pPoseStack, x + 116, y + 72 - this.menu.drawWater(), 210, 42 - this.menu.drawWater(), 22, this.menu.drawWater());
        }
        if(this.menu.drawEnergy() > 0) {
            this.blit(pPoseStack, x + 164, y + 16, 232, 76, 12, 58);
            this.blit(pPoseStack, x + 166, y + 72 - this.menu.drawEnergy(), 232, 54 - this.menu.drawEnergy(), 8, this.menu.drawEnergy());
        }

        if(this.menu.getSlotAt(0).hasItem()) this.blit(pPoseStack, x + 23, y + 73, 192, 76, 12 ,2);
        if(this.menu.getSlotAt(1).hasItem()) this.blit(pPoseStack, x + 41, y + 73, 218, 76, 12 ,2);
        if(this.menu.getSlotAt(2).hasItem()) this.blit(pPoseStack, x + 59, y + 73, 232, 76, 12 ,2);
    }
}
