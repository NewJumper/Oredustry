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
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE = BLOCK_ENTITIES.register("crucible", () -> BlockEntityType.Builder.of(CrucibleBlockEntity::new, OredustryBlocks.CRUCIBLE.get()).build(null));
}
