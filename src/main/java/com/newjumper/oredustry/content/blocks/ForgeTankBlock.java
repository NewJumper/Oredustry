package com.newjumper.oredustry.content.blocks;

import com.newjumper.oredustry.content.blocks.entity.ForgeTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation, NullableProblems")
public class ForgeTankBlock extends BaseEntityBlock {
    public ForgeTankBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ForgeTankBlockEntity(pPos, pState);
    }
}
