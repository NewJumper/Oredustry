package com.newjumper.oredustry.content.blocks;

import com.newjumper.oredustry.content.blocks.entity.CompressorBlockEntity;
import com.newjumper.oredustry.content.blocks.entity.OredustryBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class CompressorBlock extends MachineBlock {
    public CompressorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pState.getValue(ACTIVE)) {
            double d0 = pPos.getX() + 0.5;
            double d1 = pPos.getY();
            double d2 = pPos.getZ() + 0.5;
            if(pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(d0, d1, d2, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1, 1, false);
            }

            Direction direction = pState.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double d3 = pRandom.nextDouble() * 0.4 - 0.2;
            double d4 = axis == Direction.Axis.X ? direction.getStepX() * 0.52 : d3;
            double d5 = pRandom.nextDouble() * 6 / 16.0;
            double d6 = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52 : d3;
            pLevel.addParticle(ParticleTypes.SMOKE, d0 + d4, d1 + d5, d2 + d6, 0, 0, 0);
            pLevel.addParticle(ParticleTypes.FLAME, d0 + d4, d1 + d5, d2 + d6, 0, 0, 0);
        }
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof CompressorBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) pPlayer), (CompressorBlockEntity) blockEntity, pPos);
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof CompressorBlockEntity) {
                ((CompressorBlockEntity) blockEntity).dropContents();
                ((CompressorBlockEntity) blockEntity).getRecipesToAwardAndPopExperience((ServerLevel) pLevel, Vec3.atCenterOf(pPos));
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CompressorBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, OredustryBlockEntities.COMPRESSOR.get(), CompressorBlockEntity::tick);
    }
}
