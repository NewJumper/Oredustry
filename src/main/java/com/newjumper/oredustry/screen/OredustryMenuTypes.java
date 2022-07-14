package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Oredustry.MOD_ID);

    public static final RegistryObject<MenuType<SeparatorMenu>> SEPARATOR_MENU = MENUS.register("separator_menu", () -> IForgeMenuType.create(SeparatorMenu::new));
    public static final RegistryObject<MenuType<EnergyGeneratorMenu>> ENERGY_GENERATOR_MENU = MENUS.register("energy_generator_menu", () -> IForgeMenuType.create((id, inv, data) -> new EnergyGeneratorMenu(id, data.readBlockPos(), inv, inv.player)));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
