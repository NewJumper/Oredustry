package com.newjumper.oredustry.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.PipeBlock.PROPERTY_BY_DIRECTION;

public abstract class CableBlock extends BaseEntityBlock {
    public static final BooleanProperty UP = PipeBlock.UP;
    public static final BooleanProperty DOWN = PipeBlock.DOWN;
    public static final BooleanProperty NORTH = PipeBlock.NORTH;
    public static final BooleanProperty EAST = PipeBlock.EAST;
    public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
    public static final BooleanProperty WEST = PipeBlock.WEST;

    protected static final VoxelShape[] shapes = {
            Block.box(5, 5, 5, 11, 11, 11),  // CUBE
            Block.box(6, 11, 6, 10, 16, 10), // UP
            Block.box(6, 0, 6, 10, 5, 10),   // DOWN
            Block.box(6, 6, 0, 10, 10, 5),   // NORTH
            Block.box(11, 6, 6, 16, 10, 10), // EAST
            Block.box(6, 6, 11, 10, 10, 16), // SOUTH
            Block.box(0, 6, 6, 5, 10, 10)    // WEST
    };

    public CableBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, Boolean.FALSE).setValue(DOWN, Boolean.FALSE).setValue(NORTH, Boolean.FALSE).setValue(EAST, Boolean.FALSE).setValue(SOUTH, Boolean.FALSE).setValue(WEST, Boolean.FALSE));
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        if(pState.hasBlockEntity()) return PushReaction.BLOCK;

        return super.getPistonPushReaction(pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        BlockGetter level = pContext.getLevel();

        BlockState upState = level.getBlockState(pos.above());
        BlockState downState = level.getBlockState(pos.below());
        BlockState northState = level.getBlockState(pos.north());
        BlockState eastState = level.getBlockState(pos.east());
        BlockState southState = level.getBlockState(pos.south());
        BlockState westState = level.getBlockState(pos.west());

        return super.getStateForPlacement(pContext).setValue(UP, canConnectCable(upState)).setValue(DOWN, canConnectCable(downState)).setValue(NORTH, canConnectCable(northState)).setValue(EAST, canConnectCable(eastState)).setValue(SOUTH, canConnectCable(southState)).setValue(WEST, canConnectCable(westState));
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return pState.setValue(PROPERTY_BY_DIRECTION.get(pDirection), canConnectCable(pNeighborState));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = shapes[0];

        if(pState.getValue(UP)) shape = Shapes.join(shape, shapes[1], BooleanOp.OR);
        if(pState.getValue(DOWN)) shape = Shapes.join(shape, shapes[2], BooleanOp.OR);
        if(pState.getValue(NORTH)) shape = Shapes.join(shape, shapes[3], BooleanOp.OR);
        if(pState.getValue(EAST)) shape = Shapes.join(shape, shapes[4], BooleanOp.OR);
        if(pState.getValue(SOUTH)) shape = Shapes.join(shape, shapes[5], BooleanOp.OR);
        if(pState.getValue(WEST)) shape = Shapes.join(shape, shapes[6], BooleanOp.OR);

        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    abstract boolean canConnectCable(BlockState pState);
}
