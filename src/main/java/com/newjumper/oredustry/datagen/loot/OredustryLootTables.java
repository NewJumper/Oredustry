package com.newjumper.oredustry.datagen.loot;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class OredustryLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        this.dropSelf(OredustryBlocks.COMPRESSOR.get());
        this.dropSelf(OredustryBlocks.CRUCIBLE.get());
        this.dropSelf(OredustryBlocks.PURIFIER.get());
        this.dropSelf(OredustryBlocks.SEPARATOR.get());
        this.dropSelf(OredustryBlocks.ENERGY_GENERATOR.get());
        this.dropSelf(OredustryBlocks.HEAT_GENERATOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return OredustryBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
