package com.newjumper.oredustry.content.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeTankBlockEntity extends BlockEntity {
    public ForgeTankBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.FORGE_TANK.get(), pPos, pBlockState);
    }
}
