package com.newjumper.oredustry.datagen.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class OredustryBlockTagsProvider extends BlockTagsProvider {
    public OredustryBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(OredustryTags.Blocks.MACHINES).add(OredustryBlocks.ENERGY_GENERATOR.get(), OredustryBlocks.HEAT_GENERATOR.get());
    }
}
