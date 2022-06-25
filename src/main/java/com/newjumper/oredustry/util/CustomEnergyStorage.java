package com.newjumper.oredustry.util;

import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer, 0);
    }

    protected void onEnergyChanged() { }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int fe = super.receiveEnergy(maxReceive, simulate);
        if (fe > 0 && !simulate) {
            onEnergyChanged();
        }
        return fe;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int reduce = super.extractEnergy(maxExtract, simulate);
        if (reduce > 0 && !simulate) {
            onEnergyChanged();
        }
        return reduce;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        onEnergyChanged();
    }

    public void addEnergy(int energy) {
        this.energy += energy;
        if (this.energy > getMaxEnergyStored()) {
            this.energy = getEnergyStored();
        }
        onEnergyChanged();
    }

    public void consumeEnergy(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
        onEnergyChanged();
    }
}
