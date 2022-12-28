package com.newjumper.oredustry.block;

import com.newjumper.oredustry.block.entity.CrucibleBlockEntity;
import com.newjumper.oredustry.block.entity.OredustryBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
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

@SuppressWarnings({"deprecation", "NullableProblems"})
public class CrucibleBlock extends BaseEntityBlock {
    public CrucibleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.join(Block.box(0, 0, 0, 16, 12, 16), Block.box(3, 13, 3, 13, 14, 13), BooleanOp.OR);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof CrucibleBlockEntity) {
            if(pPlayer.getMainHandItem().is(Items.WATER_BUCKET) && ((CrucibleBlockEntity) blockEntity).getWater() <= 3000) {
                if(!pPlayer.isCreative()) pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                if(!pLevel.isClientSide()) pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1, 1);
                ((CrucibleBlockEntity) blockEntity).addWater(1000);
            }

            if(!pLevel.isClientSide() && !pPlayer.getMainHandItem().is(Items.WATER_BUCKET)) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (CrucibleBlockEntity) blockEntity, pPos);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof CrucibleBlockEntity) {
                ((CrucibleBlockEntity) blockEntity).dropContents();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrucibleBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, OredustryBlockEntities.CRUCIBLE.get(), CrucibleBlockEntity::tick);
    }
}
