package com.newjumper.oredustry;

import com.mojang.logging.LogUtils;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.screen.OredustryMenuTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod("oredustry")
public class Oredustry {
    public static final String MOD_ID = "oredustry";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Oredustry() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        OredustryBlocks.register(eventBus);
        OredustryItems.register(eventBus);
        OredustryBlockEntities.register(eventBus);
        OredustryMenuTypes.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
