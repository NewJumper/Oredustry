package com.newjumper.oredustry.content;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Oredustry.MOD_ID);

    public static final RegistryObject<Item> DENSE_COAL = ITEMS.register("dense_coal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_RAW_IRON = ITEMS.register("dense_raw_iron", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_RAW_COPPER = ITEMS.register("dense_raw_copper", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_RAW_GOLD = ITEMS.register("dense_raw_gold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_REDSTONE = ITEMS.register("dense_redstone", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_EMERALD = ITEMS.register("dense_emerald", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_LAPIS = ITEMS.register("dense_lapis", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_DIAMOND = ITEMS.register("dense_diamond", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CONDUCTION_CELL = ITEMS.register("conduction_cell", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INDUCTION_CELL = ITEMS.register("induction_cell", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> COMPRESSOR_UPGRADE = ITEMS.register("compressor_upgrade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRUCIBLE_UPGRADE = ITEMS.register("crucible_upgrade", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SEPARATOR_UPGRADE = ITEMS.register("separator_upgrade", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FUEL_UPGRADE = ITEMS.register("fuel_upgrade", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> RANGE_UPGRADE = ITEMS.register("range_upgrade", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STORAGE_UPGRADE = ITEMS.register("storage_upgrade", () -> new Item(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> UPGRADE_BASE = ITEMS.register("upgrade_base", () -> new Item(new Item.Properties().stacksTo(64)));
}
