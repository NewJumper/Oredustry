package com.newjumper.oredustry.block;

import com.newjumper.oredustry.block.entity.MinerBlockEntity;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class MinerBlock extends MachineBlock {
    private final VoxelShape CUTOUT_1 = Block.box(4, 0, 0, 12, 2, 2);
    private final VoxelShape CUTOUT_2 = Block.box(14, 0, 4, 16, 2, 12);
    private final VoxelShape CUTOUT_3 = Block.box(4, 0, 14, 12, 2, 16);
    private final VoxelShape CUTOUT_4 = Block.box(0, 0, 4, 2, 2, 12);

    protected MinerBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Stream.of(Shapes.block(), CUTOUT_1, CUTOUT_2, CUTOUT_3, CUTOUT_4).reduce((s1, s2) -> Shapes.join(s1, s2, BooleanOp.ONLY_FIRST)).get();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof MinerBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (MinerBlockEntity) blockEntity, pPos);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof MinerBlockEntity) {
                ((MinerBlockEntity) blockEntity).dropContents();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MinerBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, OredustryBlockEntities.MINER.get(), MinerBlockEntity::tick);
    }
}
