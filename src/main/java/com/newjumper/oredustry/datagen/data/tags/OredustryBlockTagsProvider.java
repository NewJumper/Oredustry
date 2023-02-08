package com.newjumper.oredustry.datagen.data.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class OredustryBlockTagsProvider extends BlockTagsProvider {
    public OredustryBlockTagsProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(OredustryBlocks.MACHINE_FRAME.get()).addTag(OredustryTags.Blocks.MACHINES);

        tag(OredustryTags.Blocks.MACHINES).add(OredustryBlocks.COMPRESSOR.get(), OredustryBlocks.CRUCIBLE.get(), OredustryBlocks.PURIFIER.get(), OredustryBlocks.SEPARATOR.get());
    }

    @NotNull
    @Override
    public String getName() {
        return "Block Tags";
    }
}
