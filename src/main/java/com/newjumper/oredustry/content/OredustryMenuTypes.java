package com.newjumper.oredustry.content;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.content.gui.menu.CompressorMenu;
import com.newjumper.oredustry.content.gui.menu.CrucibleMenu;
import com.newjumper.oredustry.content.gui.menu.MinerMenu;
import com.newjumper.oredustry.content.gui.menu.SeparatorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Oredustry.MOD_ID);

    public static final RegistryObject<MenuType<CompressorMenu>> COMPRESSOR_MENU = MENUS.register("compressor_menu", () -> IForgeMenuType.create(CompressorMenu::new));
    public static final RegistryObject<MenuType<CrucibleMenu>> CRUCIBLE_MENU = MENUS.register("crucible_menu", () -> IForgeMenuType.create(CrucibleMenu::new));
    public static final RegistryObject<MenuType<SeparatorMenu>> SEPARATOR_MENU = MENUS.register("separator_menu", () -> IForgeMenuType.create(SeparatorMenu::new));
    public static final RegistryObject<MenuType<MinerMenu>> MINER_MENU = MENUS.register("miner_menu", () -> IForgeMenuType.create(MinerMenu::new));
}
