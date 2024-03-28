package com.newjumper.oredustry.datagen.data.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class OredustryBlockTagsProvider extends BlockTagsProvider {
    public OredustryBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(OredustryBlocks.MACHINE_FRAME.get()).addTag(OredustryTags.Blocks.MACHINES);
        tag(OredustryTags.Blocks.MACHINES).add(OredustryBlocks.COMPRESSOR.get(), OredustryBlocks.CRUCIBLE.get(), OredustryBlocks.SEPARATOR.get());
    }
}
