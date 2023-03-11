package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.MachineBlock;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.screen.MinerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("NullableProblems")
public class MinerBlockEntity extends BlockEntity implements MenuProvider {
    public static final int RANGE = 5;
    public static final int LIMIT = 40;

    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> MinerBlockEntity.this.state;
                case 1 -> MinerBlockEntity.this.progress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> MinerBlockEntity.this.state = Math.max(0, Math.min(value, 3));
                case 1 -> MinerBlockEntity.this.progress = value;
            }
        }

        public int getCount() {
            return 2;
        }
    };
    private final LazyOptional<IItemHandler> lazyItemHandler;
    public final ItemStackHandler itemHandler;
    private int state;
    private int progress;

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
        pTag.putInt("state", this.state);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.state = pTag.getInt("state");
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
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        if((blockEntity.state == 0 || blockEntity.state == 2) && !state.getValue(MachineBlock.ACTIVE)) {
            state = state.setValue(MachineBlock.ACTIVE, true);
            level.setBlock(pos, state, 3);
        } else if((blockEntity.state == 1 || blockEntity.state == 3) && state.getValue(MachineBlock.ACTIVE)) {
            state = state.setValue(MachineBlock.ACTIVE, false);
            level.setBlock(pos, state, 3);
        }

        Block block = level.getBlockState(pos.north(RANGE).west(RANGE).below()).getBlock();
        if(blockEntity.progress < LIMIT && block != Blocks.AIR) blockEntity.progress++;

        if(!level.isClientSide() && blockEntity.getBlockState().getValue(MachineBlock.ACTIVE) && blockEntity.progress == LIMIT) {
            if (block != Blocks.AIR && blockEntity.fillIfEmpty(inventory, new ItemStack(block))) {
                level.setBlock(pos.north(RANGE).west(RANGE).below(), Blocks.AIR.defaultBlockState(), 3);
            }

            blockEntity.progress = 0;
        }
    }

    private boolean fillIfEmpty(SimpleContainer container, ItemStack stack) {
        for(int i = 0; i < container.getContainerSize(); i++) {
            if(container.getItem(i).isEmpty()) {
                this.itemHandler.setStackInSlot(i, stack);
            } else if(container.getItem(i).sameItem(stack) && container.getItem(i).getCount() < container.getItem(i).getMaxStackSize()) {
                stack.setCount(this.itemHandler.getStackInSlot(i).getCount() + 1);
                this.itemHandler.setStackInSlot(i, stack);
            } else continue;

            return true;
        }

        return false;
    }
}
