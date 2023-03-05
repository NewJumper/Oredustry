package com.newjumper.oredustry.block;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class OredustryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Oredustry.MOD_ID);

    public static final RegistryObject<Block> MACHINE_FRAME = register("machine_frame", () -> new MachineFrameBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(4.5f, 6f).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COMPRESSOR = register("compressor", () -> new CompressorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CRUCIBLE = register("crucible", () -> new CrucibleBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SEPARATOR = register("separator", () -> new SeparatorBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(3.5f, 4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> MINER = register("miner", () -> new MinerBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).strength(5f, 12f).requiresCorrectToolForDrops()));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> pBlock) {
        RegistryObject<T> block = BLOCKS.register(name, pBlock);
        OredustryItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(OredustryCreativeTab.OREDUSTRY)));
        return block;
    }
}
