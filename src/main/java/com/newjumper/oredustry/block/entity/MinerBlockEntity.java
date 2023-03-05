package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.screen.MinerMenu;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("NullableProblems")
public class MinerBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> MinerBlockEntity.this.fuel;
                case 1 -> MinerBlockEntity.this.maxFuel;
                case 2 -> MinerBlockEntity.this.progress;
                case 3 -> MinerBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> MinerBlockEntity.this.fuel = value;
                case 1 -> MinerBlockEntity.this.maxFuel = value;
                case 2 -> MinerBlockEntity.this.progress = value;
                case 3 -> MinerBlockEntity.this.maxProgress = value;
            }
        }

        public int getCount() {
            return 4;
        }
    };
    private final LazyOptional<IItemHandler> lazyItemHandler;
    public final ItemStackHandler itemHandler;
    private int fuel;
    private int maxFuel;
    private int progress;
    private int maxProgress;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();

    public MinerBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.MINER.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(27) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.MINER.getId().getPath());
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new MinerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("fuel", this.fuel);
        pTag.putInt("maxFuel", this.maxFuel);
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.fuel = pTag.getInt("fuel");
        this.maxFuel = pTag.getInt("maxFuel");
        this.progress = pTag.getInt("progress");
        this.maxProgress = pTag.getInt("maxProgress");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();

        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, MinerBlockEntity blockEntity) {

    }
}
