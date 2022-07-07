package com.newjumper.oredustry.world;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.List;

public class OreConfiguredFeatures {
    public static final List<OreConfiguration.TargetBlockState> DENSE_COAL_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_COAL_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_IRON_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_IRON_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_COPPER_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_COPPER_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_GOLD_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_GOLD_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_REDSTONE_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_REDSTONE_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_EMERALD_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_EMERALD_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_LAPIS_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_LAPIS_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get().defaultBlockState()));
    public static final List<OreConfiguration.TargetBlockState> DENSE_DIAMOND_TARGET_LIST = List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DIAMOND_ORE.get().defaultBlockState()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get().defaultBlockState()));

    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_COAL = FeatureUtils.register("ore_dense_coal", Feature.ORE, new OreConfiguration(DENSE_COAL_TARGET_LIST, 4, 0.2f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_COAL_BURIED = FeatureUtils.register("ore_dense_coal_buried", Feature.ORE, new OreConfiguration(DENSE_COAL_TARGET_LIST, 6, 0.9f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_IRON_SMALL = FeatureUtils.register("ore_dense_iron_small", Feature.ORE, new OreConfiguration(DENSE_IRON_TARGET_LIST, 3));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_IRON_LARGE = FeatureUtils.register("ore_dense_iron_large", Feature.ORE, new OreConfiguration(DENSE_IRON_TARGET_LIST, 5, 0.5f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_COPPER = FeatureUtils.register("ore_dense_copper", Feature.ORE, new OreConfiguration(DENSE_COPPER_TARGET_LIST, 4, 0.7f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_GOLD = FeatureUtils.register("ore_dense_gold", Feature.ORE, new OreConfiguration(DENSE_GOLD_TARGET_LIST, 3, 0.4f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_GOLD_BURIED = FeatureUtils.register("ore_dense_gold_buried", Feature.ORE, new OreConfiguration(DENSE_GOLD_TARGET_LIST, 4, 0.9f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_REDSTONE_UPPER = FeatureUtils.register("ore_dense_redstone_upper", Feature.ORE, new OreConfiguration(DENSE_REDSTONE_TARGET_LIST, 3, 0.8f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_REDSTONE_LOWER = FeatureUtils.register("ore_dense_redstone_lower", Feature.ORE, new OreConfiguration(DENSE_REDSTONE_TARGET_LIST, 5, 0.7f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_EMERALD = FeatureUtils.register("ore_dense_emerald", Feature.ORE, new OreConfiguration(DENSE_EMERALD_TARGET_LIST, 2, 0.3f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_LAPIS_UPPER = FeatureUtils.register("ore_dense_lapis_upper", Feature.ORE, new OreConfiguration(DENSE_LAPIS_TARGET_LIST, 4));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_LAPIS_LOWER = FeatureUtils.register("ore_dense_lapis_lower", Feature.ORE, new OreConfiguration(DENSE_LAPIS_TARGET_LIST, 5, 0.2f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_DIAMOND_SMALL = FeatureUtils.register("ore_dense_diamond_small", Feature.ORE, new OreConfiguration(DENSE_DIAMOND_TARGET_LIST, 2, 0.6f));
    public static final Holder<ConfiguredFeature<OreConfiguration, ?>> ORE_DENSE_DIAMOND_LARGE = FeatureUtils.register("ore_dense_diamond_large", Feature.ORE, new OreConfiguration(DENSE_DIAMOND_TARGET_LIST, 5, 0.9f));
}
