package com.newjumper.oredustry.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class OredustryCapabilities {
    public static final Capability<IHeatStorage> HEAT = CapabilityManager.get(new CapabilityToken<>() { });
}
