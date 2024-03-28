package com.newjumper.oredustry.datagen.data.tags;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class OredustryItemTagsProvider extends ItemTagsProvider {
    public OredustryItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, TagsProvider<Block> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookup, blockTags.contentsGetter(), Oredustry.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        tag(OredustryTags.Items.CELLS).add(OredustryItems.CONDUCTION_CELL.get(), OredustryItems.INDUCTION_CELL.get());
        tag(OredustryTags.Items.DENSE_MATERIALS).add(OredustryItems.DENSE_COAL.get(), OredustryItems.DENSE_RAW_COPPER.get(), OredustryItems.DENSE_RAW_GOLD.get(), OredustryItems.DENSE_RAW_IRON.get(), OredustryItems.DENSE_REDSTONE.get(), OredustryItems.DENSE_EMERALD.get(), OredustryItems.DENSE_LAPIS.get(), OredustryItems.DENSE_DIAMOND.get());
        tag(OredustryTags.Items.FRAME_UPGRADES).add(OredustryItems.COMPRESSOR_UPGRADE.get(), OredustryItems.CRUCIBLE_UPGRADE.get(), OredustryItems.SEPARATOR_UPGRADE.get());
        tag(OredustryTags.Items.UPGRADES).add(OredustryItems.FUEL_UPGRADE.get(), OredustryItems.RANGE_UPGRADE.get(), OredustryItems.SPEED_UPGRADE.get(), OredustryItems.STORAGE_UPGRADE.get());
    }
}
