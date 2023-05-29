package com.newjumper.oredustry.network;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    public static SimpleChannel INSTANCE;

    private static int ID;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channel) {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Oredustry.MOD_ID, channel), () -> "1.0", s -> true, s -> true);
        INSTANCE.registerMessage(nextID(), PacketMachineButton.class, PacketMachineButton::toBytes, PacketMachineButton::new, PacketMachineButton::handle);
    }
}
