package com.newjumper.oredustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class MachineFrameBlock extends Block {
    private final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private final VoxelShape CUTOUT_1 = Block.box(0, 2, 2, 16, 14, 14);
    private final VoxelShape CUTOUT_2 = Block.box(2, 0, 2, 14, 16, 14);
    private final VoxelShape CUTOUT_3 = Block.box(2, 2, 0, 14, 14, 16);

    public MachineFrameBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;

        if(pPlayer.getMainHandItem().is(Items.DIAMOND)) {
            System.out.println("DIAMOND");
            return InteractionResult.CONSUME;
        }
        if(pPlayer.getMainHandItem().is(Items.EMERALD)) {
            System.out.println("EMERALD");
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Stream.of(SHAPE, CUTOUT_1, CUTOUT_2, CUTOUT_3).reduce((s1, s2) -> Shapes.join(s1, s2, BooleanOp.ONLY_FIRST)).get();
    }
}
