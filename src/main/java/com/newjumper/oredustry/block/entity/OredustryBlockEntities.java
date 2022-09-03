package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OredustryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Oredustry.MOD_ID);

    public static final RegistryObject<BlockEntityType<SeparatorBlockEntity>> SEPARATOR = BLOCK_ENTITIES.register("separator", () -> BlockEntityType.Builder.of(SeparatorBlockEntity::new, OredustryBlocks.SEPARATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<EnergyGeneratorBlockEntity>> ENERGY_GENERATOR = BLOCK_ENTITIES.register("energy_generator", () -> BlockEntityType.Builder.of(EnergyGeneratorBlockEntity::new, OredustryBlocks.ENERGY_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<HeatGeneratorBlockEntity>> HEAT_GENERATOR = BLOCK_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorBlockEntity::new, OredustryBlocks.HEAT_GENERATOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<HeatCableBlockEntity>> HEAT_CABLE = BLOCK_ENTITIES.register("heat_cable", () -> BlockEntityType.Builder.of(HeatCableBlockEntity::new, OredustryBlocks.HEAT_CABLE.get()).build(null));
}
