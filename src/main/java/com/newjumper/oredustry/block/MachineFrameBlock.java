package com.newjumper.oredustry.block;

import com.newjumper.oredustry.item.OredustryItems;
import com.newjumper.oredustry.util.OredustryTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class MachineFrameBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);
    private final VoxelShape CUTOUT_1 = Block.box(0, 2, 2, 16, 14, 14);
    private final VoxelShape CUTOUT_2 = Block.box(2, 0, 2, 14, 16, 14);
    private final VoxelShape CUTOUT_3 = Block.box(2, 2, 0, 14, 14, 16);

    public MachineFrameBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return InteractionResult.SUCCESS;

        if(pPlayer.getMainHandItem().is(OredustryItems.COMPRESSOR_UPGRADE.get())) {
            pLevel.setBlock(pPos, OredustryBlocks.COMPRESSOR.get().withPropertiesOf(pState), 3);
        } else if(pPlayer.getMainHandItem().is(OredustryItems.CRUCIBLE_UPGRADE.get())) {
            pLevel.setBlock(pPos, OredustryBlocks.CRUCIBLE.get().withPropertiesOf(pState), 3);
        } else if(pPlayer.getMainHandItem().is(OredustryItems.SEPARATOR_UPGRADE.get())) {
            pLevel.setBlock(pPos, OredustryBlocks.SEPARATOR.get().withPropertiesOf(pState), 3);
        }

        if(pPlayer.getMainHandItem().is(OredustryTags.Items.FRAME_UPGRADES)) {
            pPlayer.getMainHandItem().shrink(1);
            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Stream.of(SHAPE, CUTOUT_1, CUTOUT_2, CUTOUT_3).reduce((s1, s2) -> Shapes.join(s1, s2, BooleanOp.ONLY_FIRST)).get();
    }
}
