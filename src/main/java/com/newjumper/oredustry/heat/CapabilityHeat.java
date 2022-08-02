package com.newjumper.oredustry.heat;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;

public class CapabilityHeat {
    public static final Capability<IHeatStorage> HEAT = CapabilityManager.get(new CapabilityToken<>(){});

    public static void register(RegisterCapabilitiesEvent event)
    {
        event.register(IHeatStorage.class);
    }
}
