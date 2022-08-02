package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.heat.CapabilityHeat;
import com.newjumper.oredustry.heat.HeatStorage;
import com.newjumper.oredustry.heat.IHeatStorage;
import com.newjumper.oredustry.screen.HeatGeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    public static final int HEAT_CAPACITY = 8000;

    private final LazyOptional<IItemHandler> lazyItemHandler;
    private final LazyOptional<IHeatStorage> lazyHeatStorage;
    public final ItemStackHandler itemHandler;
    public final HeatStorage heatStorage;

    public HeatGeneratorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.HEAT_GENERATOR.get(), pPos, pBlockState);

        this.itemHandler = new ItemStackHandler(8) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
        this.heatStorage = new HeatStorage(HEAT_CAPACITY, 0) {
            @Override
            protected void onHeatChanged() {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyHeatStorage = LazyOptional.of(() -> heatStorage);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.oredustry.heat_generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new HeatGeneratorMenu(pContainerId, this.getBlockPos(), pPlayerInventory, pPlayer);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.put("heat", heatStorage.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        heatStorage.deserializeNBT(pTag.get("heat"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return lazyItemHandler.cast();
        if(cap == CapabilityHeat.HEAT) return lazyHeatStorage.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyHeatStorage.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, HeatGeneratorBlockEntity pBlockEntity) {
        int burnTime = ForgeHooks.getBurnTime(pBlockEntity.itemHandler.getStackInSlot(0), RecipeType.SMELTING) / 20;

        if(burnTime > 0 && pBlockEntity.heatStorage.getHeatStored() < HEAT_CAPACITY / 2) {
            if(pBlockEntity.itemHandler.getStackInSlot(0).is(Items.LAVA_BUCKET)) pBlockEntity.itemHandler.setStackInSlot(0, new ItemStack(Items.BUCKET));
            else pBlockEntity.itemHandler.extractItem(0, 1, false);
            pBlockEntity.heatStorage.addHeat(toHeat(burnTime));
        }

        if(pBlockEntity.heatStorage.getHeatStored() > 0 && pLevel.getGameTime() % 20 == 0) {
            pBlockEntity.heatStorage.extractHeat(1);
        }

        setChanged(pLevel, pPos, pState);
    }

    public static int toHeat(int x) {
        return (int) (0.00154 * (x * x) + 0.568 * x + 32);
    }
}
