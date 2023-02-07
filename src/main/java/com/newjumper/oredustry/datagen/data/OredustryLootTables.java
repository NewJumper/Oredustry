package com.newjumper.oredustry.datagen.data;

import com.newjumper.oredustry.block.OredustryBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class OredustryLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        this.add(OredustryBlocks.DENSE_COAL_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_COAL_ORE.get(), Items.COAL, 2, 4));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_COAL_ORE.get(), Items.COAL, 2, 4));
        this.add(OredustryBlocks.DENSE_IRON_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_IRON_ORE.get(), Items.RAW_IRON, 2, 4));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_IRON_ORE.get(), Items.RAW_IRON, 2, 4));
        this.add(OredustryBlocks.DENSE_COPPER_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_COPPER_ORE.get(), Items.RAW_COPPER, 3, 8));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_COPPER_ORE.get(), Items.RAW_COPPER, 3, 7));
        this.add(OredustryBlocks.DENSE_GOLD_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_GOLD_ORE.get(), Items.RAW_GOLD, 2, 4));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_GOLD_ORE.get(), Items.RAW_GOLD, 2, 4));
        this.add(OredustryBlocks.DENSE_REDSTONE_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_REDSTONE_ORE.get(), Items.REDSTONE, 5, 8));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_REDSTONE_ORE.get(), Items.REDSTONE, 5, 8));
        this.add(OredustryBlocks.DENSE_EMERALD_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_EMERALD_ORE.get(), Items.EMERALD, 2, 3));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_EMERALD_ORE.get(), Items.EMERALD, 2, 4));
        this.add(OredustryBlocks.DENSE_LAPIS_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_LAPIS_ORE.get(), Items.LAPIS_LAZULI, 6, 11));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_LAPIS_ORE.get(), Items.LAPIS_LAZULI, 7, 11));
        this.add(OredustryBlocks.DENSE_DIAMOND_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DIAMOND_ORE.get(), Items.DIAMOND, 2, 3));
        this.add(OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get(), createDenseOreDrop(OredustryBlocks.DENSE_DEEPSLATE_DIAMOND_ORE.get(), Items.DIAMOND, 2, 3));

        this.dropSelf(OredustryBlocks.MACHINE_FRAME.get());
        this.dropSelf(OredustryBlocks.COMPRESSOR.get());
        this.dropSelf(OredustryBlocks.CRUCIBLE.get());
        this.dropSelf(OredustryBlocks.PURIFIER.get());
        this.dropSelf(OredustryBlocks.SEPARATOR.get());
    }

    private LootTable.Builder createDenseOreDrop(Block pBlock, Item pItem, int pMin, int pMax) {
        return createSilkTouchDispatchTable(pBlock, applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(pMin, pMax))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @NotNull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return OredustryBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
