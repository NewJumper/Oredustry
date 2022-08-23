package com.newjumper.oredustry.world;

import com.google.common.base.Suppliers;
import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class OreConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Oredustry.MOD_ID);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_COAL_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_COAL_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_IRON_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_IRON_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_COPPER_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_COPPER_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_GOLD_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_GOLD_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_REDSTONE_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_REDSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_EMERALD_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_EMERALD_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_LAPIS_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_LAPIS_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get().defaultBlockState())));
    public static final Supplier<List<OreConfiguration.TargetBlockState>> DENSE_DIAMOND_TARGET_LIST = Suppliers.memoize(() -> List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DIAMOND_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get().defaultBlockState())));

    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_COAL = CONFIGURED_FEATURES.register("ore_dense_coal", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_COAL_TARGET_LIST.get(), 8, 0.2f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_COAL_BURIED = CONFIGURED_FEATURES.register("ore_dense_coal_buried", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_COAL_TARGET_LIST.get(), 10, 0.9f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_IRON_SMALL = CONFIGURED_FEATURES.register("ore_dense_iron_small", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_IRON_TARGET_LIST.get(), 7)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_IRON_LARGE = CONFIGURED_FEATURES.register("ore_dense_iron_large", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_IRON_TARGET_LIST.get(), 9, 0.5f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_COPPER = CONFIGURED_FEATURES.register("ore_dense_copper", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_COPPER_TARGET_LIST.get(), 8, 0.7f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_GOLD = CONFIGURED_FEATURES.register("ore_dense_gold", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_GOLD_TARGET_LIST.get(), 7, 0.4f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_GOLD_BURIED = CONFIGURED_FEATURES.register("ore_dense_gold_buried", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_GOLD_TARGET_LIST.get(), 8, 0.9f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_REDSTONE_UPPER = CONFIGURED_FEATURES.register("ore_dense_redstone_upper", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_REDSTONE_TARGET_LIST.get(), 7, 0.8f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_REDSTONE_LOWER = CONFIGURED_FEATURES.register("ore_dense_redstone_lower", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_REDSTONE_TARGET_LIST.get(), 9, 0.7f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_EMERALD = CONFIGURED_FEATURES.register("ore_dense_emerald", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_EMERALD_TARGET_LIST.get(), 6, 0.3f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_LAPIS_UPPER = CONFIGURED_FEATURES.register("ore_dense_lapis_upper", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_LAPIS_TARGET_LIST.get(), 8)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_LAPIS_LOWER = CONFIGURED_FEATURES.register("ore_dense_lapis_lower", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_LAPIS_TARGET_LIST.get(), 9, 0.2f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_DIAMOND_SMALL = CONFIGURED_FEATURES.register("ore_dense_diamond_small", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_DIAMOND_TARGET_LIST.get(), 6, 0.6f)));
    public static final RegistryObject<ConfiguredFeature<?, ?>> ORE_DENSE_DIAMOND_LARGE = CONFIGURED_FEATURES.register("ore_dense_diamond_large", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(DENSE_DIAMOND_TARGET_LIST.get(), 9, 0.9f)));
}
