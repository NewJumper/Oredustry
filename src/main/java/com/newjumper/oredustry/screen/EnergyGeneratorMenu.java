package com.newjumper.oredustry.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;

public class EnergyGeneratorMenu extends AbstractContainerMenu {
    private final BlockEntity blockEntity;

    public EnergyGeneratorMenu(int pContainerId, BlockPos pPos, Inventory pInventory, Player pPlayer) {
        super(OredustryMenuTypes.ENERGY_GENERATOR_MENU.get(), pContainerId);

        this.blockEntity = pPlayer.level.getBlockEntity(pPos);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
