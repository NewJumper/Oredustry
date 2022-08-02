package com.newjumper.oredustry.heat;

import net.minecraftforge.energy.IEnergyStorage;

/**
 * Heat storage is an alternative for {@link IEnergyStorage}, made for Oredustry machines.
 */
public interface IHeatStorage {
    /**
     * Add heat to the system.
     * @param maxInsert Maximum amount of heat to add.
     * @param force If true, and the amount to insert is greater than the maximum storage, the maximum storage updates to the current amount of heat in storage.
     * @return Amount of heat that was successfully added (or forcefully added, if forced) to storage.
     */
    int insertHeat(int maxInsert, boolean force);
    int addHeat(int maxInsert);

    /**
     * Remove heat from the system.
     * @param maxExtract Maximum amount of heat to remove.
     * @return Amount of heat that was removed from storage.
     */
    int extractHeat(int maxExtract);

    /**
     * @param setHeat Amount of heat to set the system to.
     * @return True if heat is within the capacity of system, false if heat is less than 0 or greater than the maximum storage
     */
    boolean setHeat(int setHeat);

    /**
     * @return Current amount of heat in the system.
     */
    int getHeatStored();

    /**
     * @return Maximum amount of heat that can be stored in the system.
     */
    int getMaxHeatStorage();
}
