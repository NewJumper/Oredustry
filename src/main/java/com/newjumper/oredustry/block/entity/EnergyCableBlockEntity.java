package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.util.OredustryEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class EnergyCableBlockEntity extends BlockEntity {
    public static final int ENERGY_CAPACITY = 100;

    private final LazyOptional<IEnergyStorage> lazyEnergyStorage;
    public final OredustryEnergyStorage energyStorage;

    public EnergyCableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.ENERGY_CABLE.get(), pPos, pBlockState);

        this.energyStorage = new OredustryEnergyStorage(ENERGY_CAPACITY, 0) {
            @Override
            protected void onEnergyChanged() {
                setChanged();
            }
        };

        this.lazyEnergyStorage = LazyOptional.of(() -> energyStorage);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("energy", energyStorage.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        energyStorage.deserializeNBT(pTag.get("energy"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) return lazyEnergyStorage.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyStorage.invalidate();
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, EnergyCableBlockEntity pBlockEntity) {

    }

    public int search(ArrayList<BlockPos> cables, Level level, BlockPos pos) {
        int count = 0;

        if(cables.contains(pos)) return count;
        else cables.add(pos);

        if(level.getBlockState(pos.above()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.above());
        if(level.getBlockState(pos.below()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.below());
        if(level.getBlockState(pos.north()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.north());
        if(level.getBlockState(pos.east()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.east());
        if(level.getBlockState(pos.south()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.south());
        if(level.getBlockState(pos.west()).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, pos.west());

        return count + 1;
    }
}
