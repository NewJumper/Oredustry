package com.newjumper.oredustry.heat;

import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatStorage implements IHeatStorage, INBTSerializable<Tag> {
    protected int heat;
    protected int system;

    public HeatStorage(int systemCapacity, int storedHeat) {
        this.system = systemCapacity;
        this.heat = storedHeat;
    }

    @Override
    public int insertHeat(int maxInsert, boolean force) {
        if(!force) {
            int heatInsert = Math.min(maxInsert, system - heat);
            heat += heatInsert;
            return heatInsert;
        }

        int heatInsert = Math.max(maxInsert, system - heat);
        if(heatInsert > system - heat) system = heat + heatInsert;
        heat += heatInsert;
        return heatInsert;
    }

    @Override
    public int addHeat(int maxInsert) {
        return insertHeat(maxInsert, false);
    }

    @Override
    public int extractHeat(int maxExtract) {
        heat -= maxExtract;
        if(heat < 0) {
            heat = 0;
            return 0;
        }
        return maxExtract;
    }

    @Override
    public boolean setHeat(int setHeat) {
        if(setHeat > system) return false;
        heat = setHeat;
        return true;
    }

    @Override
    public int getHeatStored() {
        return heat;
    }

    @Override
    public int getMaxHeatStorage() {
        return system;
    }

    @Override
    public Tag serializeNBT() {
        return IntTag.valueOf(this.getHeatStored());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof IntTag intNbt))
            throw new IllegalArgumentException("Cannot deserialize to an instance that isn't the default implementation");
        this.heat = intNbt.getAsInt();
    }

    protected void onHeatChanged() { }
}
