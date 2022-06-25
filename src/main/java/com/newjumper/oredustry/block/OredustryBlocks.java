package com.newjumper.oredustry.block;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OredustryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oredustry.MOD_ID);

    public static final RegistryObject<Block> COMPRESSOR = registerBlock("compressor", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);
    public static final RegistryObject<Block> CRUCIBLE = registerBlock("crucible", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);
    public static final RegistryObject<Block> PURIFIER = registerBlock("purifier", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);
    public static final RegistryObject<Block> SEPARATOR = registerBlock("separator", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);

    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator", () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);
    public static final RegistryObject<Block> HEAT_GENERATOR = registerBlock("heat_generator", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL)), OredustryCreativeTab.OREDUSTRY);

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> pBlock, CreativeModeTab pTab) {
        RegistryObject<T> block = BLOCKS.register(name, pBlock);
        registerBlockItem(name, block, pTab);
        return block;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> pBlock, CreativeModeTab pTab) {
        OredustryItems.ITEMS.register(name, () -> new BlockItem(pBlock.get(), new Item.Properties().tab(pTab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
