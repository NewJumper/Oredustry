package com.newjumper.oredustry.screen.renderer;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.List;

public class ToolTipRenderer<T> extends GuiComponent {
    private final T material;

    public ToolTipRenderer(T material)  {
        this.material = material;
    }

    public List<Component> getToolTip() {
        if(material instanceof FluidTank) return List.of(Component.literal(((FluidTank) material).getFluidAmount() + " mb"));
        if(material instanceof IEnergyStorage) return List.of(Component.literal(((IEnergyStorage) material).getEnergyStored() + " FE"));
        return null;
    }
}
