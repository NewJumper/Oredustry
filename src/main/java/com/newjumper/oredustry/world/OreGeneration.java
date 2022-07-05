package com.newjumper.oredustry.world;

import com.newjumper.oredustry.Oredustry;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Oredustry.MOD_ID)
public class OreGeneration {
    @SubscribeEvent
    public static void generateOres(final BiomeLoadingEvent event) {
        List<Holder<PlacedFeature>> base = event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);

        base.add(OrePlacedFeatures.DENSE_COAL);
        base.add(OrePlacedFeatures.DENSE_COAL_BURIED);
        base.add(OrePlacedFeatures.DENSE_IRON_SMALL);
        base.add(OrePlacedFeatures.DENSE_IRON_LARGE);
        base.add(OrePlacedFeatures.DENSE_COPPER);
        base.add(OrePlacedFeatures.DENSE_GOLD);
        base.add(OrePlacedFeatures.DENSE_GOLD_BURIED);
        base.add(OrePlacedFeatures.DENSE_REDSTONE_UPPER);
        base.add(OrePlacedFeatures.DENSE_REDSTONE_LOWER);
        base.add(OrePlacedFeatures.DENSE_EMERALD);
        base.add(OrePlacedFeatures.DENSE_LAPIS_UPPER);
        base.add(OrePlacedFeatures.DENSE_LAPIS_LOWER);
        base.add(OrePlacedFeatures.DENSE_DIAMOND_SMALL);
        base.add(OrePlacedFeatures.DENSE_DIAMOND_LARGE);
    }
}
