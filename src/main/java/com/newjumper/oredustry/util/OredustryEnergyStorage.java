package com.newjumper.oredustry.util;

import net.minecraftforge.energy.EnergyStorage;

public class OredustryEnergyStorage extends EnergyStorage {
    public OredustryEnergyStorage(int capacity) {
        super(capacity);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receiveEnergy = super.receiveEnergy(maxReceive, simulate);
        if(receiveEnergy > 0) onEnergyChanged();
        return receiveEnergy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractEnergy = super.extractEnergy(maxExtract, simulate);
        if(extractEnergy > 0) onEnergyChanged();
        return extractEnergy;
    }

    public int setEnergy(int energy) {
        this.energy = Math.min(energy, this.capacity);
        onEnergyChanged();
        return this.energy;
    }

    public void addEnergy(int energy) {
        this.receiveEnergy(energy, false);
    }

    public void consumeEnergy(int energy) {
        this.extractEnergy(energy, false);
    }


    protected void onEnergyChanged() { }
}
