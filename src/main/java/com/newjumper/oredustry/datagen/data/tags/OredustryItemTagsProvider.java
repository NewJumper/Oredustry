package com.newjumper.oredustry.datagen.data.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class OredustryItemTagsProvider extends ItemTagsProvider {
    public OredustryItemTagsProvider(DataGenerator gen, BlockTagsProvider blockTags, ExistingFileHelper exFileHelper) {
        super(gen, blockTags, Oredustry.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        tag(OredustryTags.Items.DENSE_MATERIALS).add(OredustryItems.DENSE_COAL.get(), OredustryItems.DENSE_RAW_COPPER.get(), OredustryItems.DENSE_RAW_GOLD.get(), OredustryItems.DENSE_RAW_IRON.get(), OredustryItems.DENSE_REDSTONE.get(), OredustryItems.DENSE_EMERALD.get(), OredustryItems.DENSE_LAPIS.get(), OredustryItems.DENSE_DIAMOND.get());
        tag(OredustryTags.Items.CELLS).add(OredustryItems.CONDUCTION_CELL.get(), OredustryItems.INDUCTION_CELL.get());
        tag(OredustryTags.Items.FRAME_UPGRADES).add(OredustryItems.COMPRESSOR_UPGRADE.get(), OredustryItems.CRUCIBLE_UPGRADE.get(), OredustryItems.SEPARATOR_UPGRADE.get());
    }

    @NotNull
    @Override
    public String getName() {
        return "Item Tags";
    }
}
