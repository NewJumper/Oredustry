package com.newjumper.oredustry.item;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oredustry.MOD_ID);

    public static final RegistryObject<Item> RANDOM_ITEM = ITEMS.register("random_item", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
