package com.newjumper.oredustry.block;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OredustryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oredustry.MOD_ID);

    public static final RegistryObject<Block> DENSE_COAL_ORE = registerBlock("dense_coal_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.COAL_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_COAL_ORE = registerBlock("dense_deepslate_coal_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> DENSE_IRON_ORE = registerBlock("dense_iron_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_IRON_ORE = registerBlock("dense_deepslate_iron_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_COPPER_ORE = registerBlock("dense_copper_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_COPPER_ORE = registerBlock("dense_deepslate_copper_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_GOLD_ORE = registerBlock("dense_gold_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_GOLD_ORE = registerBlock("dense_deepslate_gold_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_REDSTONE_ORE = registerBlock("dense_redstone_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(3, 6))); // TODO: dense redstone and deepslate redstone ores should have a LIT blockstate and work similar to normal redstone ores
    public static final RegistryObject<Block> DENSE_DEEPSLATE_REDSTONE_ORE = registerBlock("dense_deepslate_redstone_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(DENSE_REDSTONE_ORE.get()).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(3, 6)));
    public static final RegistryObject<Block> DENSE_EMERALD_ORE = registerBlock("dense_emerald_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(4, 7)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_EMERALD_ORE = registerBlock("dense_deepslate_emerald_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_EMERALD_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(4, 7)));
    public static final RegistryObject<Block> DENSE_LAPIS_ORE = registerBlock("dense_lapis_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_LAPIS_ORE = registerBlock("dense_deepslate_lapis_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_LAPIS_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DENSE_DIAMOND_ORE = registerBlock("dense_diamond_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(4, 8)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_DIAMOND_ORE = registerBlock("dense_deepslate_diamond_ore", () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(4, 8)));

    public static final RegistryObject<Block> COMPRESSOR = registerBlock("compressor", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRUCIBLE = registerBlock("crucible", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PURIFIER = registerBlock("purifier", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SEPARATOR = registerBlock("separator", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator", () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HEAT_GENERATOR = registerBlock("heat_generator", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> pBlock) {
        RegistryObject<T> block = BLOCKS.register(name, pBlock);
        registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> pBlock) {
        OredustryItems.ITEMS.register(name, () -> new BlockItem(pBlock.get(), new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
