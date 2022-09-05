package com.newjumper.oredustry.block.entity;

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
}
