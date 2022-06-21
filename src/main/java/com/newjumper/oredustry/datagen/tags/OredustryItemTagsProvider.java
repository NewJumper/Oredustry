package com.newjumper.oredustry.datagen.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class OredustryItemTagsProvider extends ItemTagsProvider {
    public OredustryItemTagsProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(OredustryTags.Items.PURE_MATERIALS).add(OredustryItems.PURE_COPPER.get(), OredustryItems.PURE_GOLD.get(), OredustryItems.PURE_IRON.get(), OredustryItems.PURE_NETHERITE.get());
    }
}
