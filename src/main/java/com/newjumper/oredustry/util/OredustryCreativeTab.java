package com.newjumper.oredustry.util;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.item.OredustryItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class OredustryCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Oredustry.MOD_ID);
    public static final RegistryObject<CreativeModeTab> OREDUSTRY = CREATIVE_MODE_TABS.register("oredustry", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + Oredustry.MOD_ID)).icon(() -> new ItemStack(OredustryBlocks.CRUCIBLE.get())).build());

    public static void buildCreativeTab(final BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == OREDUSTRY.get()) {
            OredustryBlocks.BLOCKS.getEntries().forEach(event::accept);
            OredustryItems.ITEMS.getEntries().forEach(event::accept);
        }
    }
}
