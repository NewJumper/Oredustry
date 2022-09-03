package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.capabilities.HeatStorage;
import com.newjumper.oredustry.capabilities.IHeatStorage;
import com.newjumper.oredustry.capabilities.OredustryCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HeatCableBlockEntity extends BlockEntity {
    public static final int HEAT_CAPACITY = 100;

    private final LazyOptional<IHeatStorage> lazyHeatStorage;
    public final HeatStorage heatStorage;

    public HeatCableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.HEAT_CABLE.get(), pPos, pBlockState);

        this.heatStorage = new HeatStorage(HEAT_CAPACITY, 0) {
            @Override
            protected void onHeatChanged() {
                setChanged();
            }
        };

        this.lazyHeatStorage = LazyOptional.of(() -> heatStorage);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("heat", heatStorage.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        heatStorage.deserializeNBT(pTag.get("heat"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == OredustryCapabilities.HEAT) return lazyHeatStorage.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyHeatStorage.invalidate();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, HeatCableBlockEntity pBlockEntity) {

    }

    public int search(ArrayList<BlockPos> cables, Level level, BlockPos pos) {
        int count = 0;

        if(cables.contains(pos)) return count;
        else cables.add(pos);

        if(level.getBlockState(pos.above()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.above());
        if(level.getBlockState(pos.below()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.below());
        if(level.getBlockState(pos.north()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.north());
        if(level.getBlockState(pos.east()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.east());
        if(level.getBlockState(pos.south()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.south());
        if(level.getBlockState(pos.west()).is(OredustryBlocks.HEAT_CABLE.get())) count += search(cables, level, pos.west());

        return count + 1;
    }
}
