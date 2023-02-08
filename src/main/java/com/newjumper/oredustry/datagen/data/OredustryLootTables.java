package com.newjumper.oredustry.datagen.data;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class OredustryLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        this.dropSelf(OredustryBlocks.MACHINE_FRAME.get());
        this.dropSelf(OredustryBlocks.COMPRESSOR.get());
        this.dropSelf(OredustryBlocks.CRUCIBLE.get());
        this.dropSelf(OredustryBlocks.PURIFIER.get());
        this.dropSelf(OredustryBlocks.SEPARATOR.get());
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return OredustryBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
