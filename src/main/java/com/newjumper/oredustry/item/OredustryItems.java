package com.newjumper.oredustry.item;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oredustry.MOD_ID);

    public static final RegistryObject<Item> PURE_IRON = ITEMS.register("pure_iron", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> PURE_COPPER = ITEMS.register("pure_copper", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> PURE_GOLD = ITEMS.register("pure_gold", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> PURE_NETHERITE = ITEMS.register("pure_netherite", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));

    public static final RegistryObject<Item> ENERGY_CELL = ITEMS.register("energy_cell", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> FUEL_CELL = ITEMS.register("fuel_cell", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> HEAT_CELL = ITEMS.register("heat_cell", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    public static final RegistryObject<Item> RADIATOR = ITEMS.register("radiator", () -> new Item(new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
}
