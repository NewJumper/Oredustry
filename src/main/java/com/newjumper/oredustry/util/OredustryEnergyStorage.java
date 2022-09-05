package com.newjumper.oredustry.util;

import net.minecraftforge.energy.EnergyStorage;

public class OredustryEnergyStorage extends EnergyStorage {
    public OredustryEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, 0);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int fe = super.receiveEnergy(maxReceive, simulate);
        if(fe > 0 && !simulate) {
            onEnergyChanged();
        }
        return fe;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int fe = super.extractEnergy(maxExtract, simulate);
        if(fe > 0 && !simulate) {
            onEnergyChanged();
        }
        return fe;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if(this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if(this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }

    protected void onEnergyChanged() { }
}
