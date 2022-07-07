package com.newjumper.oredustry.screen.renderer;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.List;

public class FluidToolTip extends GuiComponent {
    private final IFluidHandler fluid;

    public FluidToolTip(IFluidHandler fluid)  {
        this.fluid = fluid;
    }

    public List<Component> getToolTip() {
        return List.of(new TextComponent(((FluidTank)fluid).getFluidAmount() + " mb"));
    }
}
