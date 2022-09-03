package com.newjumper.oredustry.block;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OredustryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oredustry.MOD_ID);

    public static final RegistryObject<Block> DENSE_COAL_ORE = register("dense_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.COAL_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_COAL_ORE = register("dense_deepslate_coal_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(1, 2)));
    public static final RegistryObject<Block> DENSE_IRON_ORE = register("dense_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_IRON_ORE = register("dense_deepslate_iron_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_IRON_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_COPPER_ORE = register("dense_copper_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_COPPER_ORE = register("dense_deepslate_copper_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COPPER_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_GOLD_ORE = register("dense_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).strength(3.0f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_GOLD_ORE = register("dense_deepslate_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_REDSTONE_ORE = register("dense_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(Blocks.REDSTONE_ORE).strength(3.0f).randomTicks().lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 9 : 0).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_REDSTONE_ORE = register("dense_deepslate_redstone_ore", () -> new RedStoneOreBlock(BlockBehaviour.Properties.copy(DENSE_REDSTONE_ORE.get()).strength(4.5f, 3.0f).randomTicks().lightLevel((blockState) -> blockState.getValue(BlockStateProperties.LIT) ? 9 : 0).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DENSE_EMERALD_ORE = register("dense_emerald_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.EMERALD_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(4, 7)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_EMERALD_ORE = register("dense_deepslate_emerald_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_EMERALD_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(4, 7)));
    public static final RegistryObject<Block> DENSE_LAPIS_ORE = register("dense_lapis_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.LAPIS_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_LAPIS_ORE = register("dense_deepslate_lapis_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_LAPIS_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(2, 6)));
    public static final RegistryObject<Block> DENSE_DIAMOND_ORE = register("dense_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).strength(3.0f).requiresCorrectToolForDrops(), UniformInt.of(4, 8)));
    public static final RegistryObject<Block> DENSE_DEEPSLATE_DIAMOND_ORE = register("dense_deepslate_diamond_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).strength(4.5f, 3.0f).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops(), UniformInt.of(4, 8)));

    public static final RegistryObject<Block> COMPRESSOR = register("compressor", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRUCIBLE = register("crucible", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> PURIFIER = register("purifier", () -> new Block(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SEPARATOR = register("separator", () -> new SeparatorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ENERGY_GENERATOR = register("energy_generator", () -> new EnergyGeneratorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> HEAT_GENERATOR = register("heat_generator", () -> new HeatGeneratorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HEAT_CABLE = register("heat_cable", () -> new HeatCableBlock(BlockBehaviour.Properties.of(Material.WOOL).strength(1f)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> pBlock) {
        RegistryObject<T> block = BLOCKS.register(name, pBlock);
        OredustryItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
        return block;
    }
}
