package com.newjumper.oredustry.content.blocks;

import com.newjumper.oredustry.content.blocks.entity.CrucibleBlockEntity;
import com.newjumper.oredustry.content.blocks.entity.OredustryBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import java.util.stream.Stream;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class CrucibleBlock extends BaseEntityBlock {
    public static final BooleanProperty WARM = BooleanProperty.create("warm");
    private final VoxelShape CUTOUT = box(4, 7, 4, 12, 14, 12);
    private final VoxelShape MAIN = Shapes.join(box(0, 0, 0, 16, 13, 16), CUTOUT, BooleanOp.ONLY_FIRST);
    private final VoxelShape CORNER_1 = box(0, 12, 0, 3, 13, 3);
    private final VoxelShape CORNER_2 = box(0, 12, 13, 3, 13, 16);
    private final VoxelShape CORNER_3 = box(13, 12, 13, 16, 13, 16);
    private final VoxelShape CORNER_4 = box(13, 12, 0, 16, 13, 3);
    private final VoxelShape CENTER = Shapes.join(box(3, 0, 3, 13, 14, 13), CUTOUT, BooleanOp.ONLY_FIRST);

    public CrucibleBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WARM, Boolean.FALSE));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(WARM, Boolean.FALSE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WARM);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pState.getValue(WARM)) {
            if(pRandom.nextDouble() < 0.1) {
                pLevel.playLocalSound(pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1, 1, false);
            }

            double d = pRandom.nextDouble() * 6 / 16.0 + 0.4;
            pLevel.addParticle(ParticleTypes.LARGE_SMOKE, pPos.getX() + 0.52, pPos.getY() + d, pPos.getZ() + 0.52, 0, 0, 0);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(Stream.of(MAIN, CORNER_1, CORNER_2, CORNER_3, CORNER_4).reduce((s1, s2) -> Shapes.join(s1, s2, BooleanOp.ONLY_FIRST)).get(), CENTER);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof CrucibleBlockEntity) {
            if(pPlayer.getMainHandItem().is(Items.WATER_BUCKET) && ((CrucibleBlockEntity) blockEntity).getWater() <= 3000) {
                if(!pPlayer.isCreative()) pPlayer.setItemInHand(pHand, new ItemStack(Items.BUCKET));
                if(!pLevel.isClientSide()) pLevel.playSound(null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1, 1);
                ((CrucibleBlockEntity) blockEntity).addWater(1000);
            } else if(!pLevel.isClientSide() && !pPlayer.getMainHandItem().is(Items.WATER_BUCKET)) {
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
                ((CrucibleBlockEntity) blockEntity).getRecipesToAwardAndPopExperience((ServerLevel) pLevel, Vec3.atCenterOf(pPos));
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
