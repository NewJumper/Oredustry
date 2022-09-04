package com.newjumper.oredustry.block;

import com.newjumper.oredustry.block.entity.HeatCableBlockEntity;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;

public class HeatCableBlock extends CableBlock {
    public HeatCableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    boolean canConnectCable(BlockState pState) {
        return pState.is(OredustryTags.Blocks.HEAT_CONTAINER);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof HeatCableBlockEntity) {
            ArrayList<BlockPos> positions = new ArrayList<>();
            int cables = ((HeatCableBlockEntity) blockEntity).search(positions, pLevel, pPos);

            if(pLevel.isClientSide) System.out.println(cables);
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, OredustryBlockEntities.HEAT_CABLE.get(), HeatCableBlockEntity::tick);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HeatCableBlockEntity(pPos, pState);
    }
}
