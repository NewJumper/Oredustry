package com.newjumper.oredustry.screen.renderer;

import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class EnergyToolTip extends GuiComponent {
    private final IEnergyStorage energy;

    public EnergyToolTip(IEnergyStorage energy)  {
        this.energy = energy;
    }

    public List<Component> getToolTip() {
        return List.of(new TextComponent(energy.getEnergyStored() + " FE"));
    }
}
