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
        distribute(pLevel, pPos, pBlockEntity);
    }

    public static void distribute(Level level, BlockPos pos, EnergyCableBlockEntity blockEntity) {
        BlockPos[] allPos = { pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west() };

        for(BlockPos testPos : allPos) {
            if (level.getBlockState(testPos).is(OredustryBlocks.ENERGY_CABLE.get())) {
                EnergyCableBlockEntity test = (EnergyCableBlockEntity) level.getBlockEntity(testPos);
                if (test.energyStorage.getEnergyStored() < blockEntity.energyStorage.getEnergyStored()) {
                    blockEntity.energyStorage.consumeEnergy(1);
                    test.energyStorage.addEnergy(1);
                }
            }
        }
    }

    public int search(ArrayList<BlockPos> cables, Level level, BlockPos pos) {
        BlockPos[] allPos = { pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west() };
        int count = 0;

        if(cables.contains(pos)) return count;
        else cables.add(pos);

        for(BlockPos testPos : allPos) {
            if(level.getBlockState(testPos).is(OredustryBlocks.ENERGY_CABLE.get())) count += search(cables, level, testPos);
        }

        return count + 1;
    }
}
