package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oredustry.MOD_ID);

    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> COMPRESSOR = BLOCK_ENTITIES.register("compressor", () -> BlockEntityType.Builder.of(CompressorBlockEntity::new, OredustryBlocks.COMPRESSOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITIES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, OredustryBlocks.CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SeparatorBlockEntity>> SEPARATOR = BLOCK_ENTITIES.register("separator", () -> BlockEntityType.Builder.of(SeparatorBlockEntity::new, OredustryBlocks.SEPARATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MinerBlockEntity>> MINER = BLOCK_ENTITIES.register("miner", () -> BlockEntityType.Builder.of(MinerBlockEntity::new, OredustryBlocks.MINER.get()).build(null));
}
