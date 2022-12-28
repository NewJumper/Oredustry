package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.MeltingRecipe;
import com.newjumper.oredustry.screen.CrucibleMenu;
import com.newjumper.oredustry.util.MoltenLiquids;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class CrucibleBlockEntity extends BlockEntity implements MenuProvider {
    public static final int LIQUID_CAPACITY = 10400;
    public static final int WATER_CAPACITY = 4000;

    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> CrucibleBlockEntity.this.fuel;
                case 1 -> CrucibleBlockEntity.this.maxFuel;
                case 2 -> CrucibleBlockEntity.this.progress;
                case 3 -> CrucibleBlockEntity.this.maxProgress;
                case 4 -> CrucibleBlockEntity.this.coolingProgress;
                case 5 -> CrucibleBlockEntity.this.liquidAmount;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> CrucibleBlockEntity.this.fuel = value;
                case 1 -> CrucibleBlockEntity.this.maxFuel = value;
                case 2 -> CrucibleBlockEntity.this.progress = value;
                case 3 -> CrucibleBlockEntity.this.maxProgress = value;
                case 4 -> CrucibleBlockEntity.this.coolingProgress = value;
                case 5 -> CrucibleBlockEntity.this.liquidAmount = value;
            }
        }

        public int getCount() {
            return 6;
        }
    };
    private final LazyOptional<IItemHandler> lazyItemHandler;
    private final LazyOptional<IFluidHandler> lazyFluidHandler;
    public final ItemStackHandler itemHandler;
    public final FluidTank fluidTank;
    private int fuel;
    private int maxFuel;
    private int progress;
    private int maxProgress;
    private int coolingProgress;
    private int liquidAmount;
    private MoltenLiquids liquid;

    public CrucibleBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.CRUCIBLE.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(4) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.fluidTank = new FluidTank(WATER_CAPACITY) {
            @Override
            protected void onContentsChanged() {
                setChanged();
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return stack.getFluid() == Fluids.WATER;
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
        this.lazyFluidHandler = LazyOptional.of(() -> fluidTank);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.CRUCIBLE.getId().getPath());
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CrucibleMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.put("inventory", itemHandler.serializeNBT());
        fluidTank.writeToNBT(pTag);
        pTag.putInt("fuel", this.fuel);
        pTag.putInt("maxFuel", this.maxFuel);
        pTag.putInt("progress", this.progress);
        pTag.putInt("maxProgress", this.maxProgress);
        pTag.putInt("cooling", this.coolingProgress);
        pTag.putInt("liquid", this.liquidAmount);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        fluidTank.readFromNBT(pTag);
        this.fuel = pTag.getInt("fuel");
        this.maxFuel = pTag.getInt("maxFuel");
        this.progress = pTag.getInt("progress");
        this.maxProgress = pTag.getInt("maxProgress");
        this.coolingProgress = pTag.getInt("cooling");
        this.liquidAmount = pTag.getInt("liquid");
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) return lazyItemHandler.cast();
        if(cap == ForgeCapabilities.FLUID_HANDLER) return lazyFluidHandler.cast();

        return super.getCapability(cap);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    public void dropContents() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    /** TODO: FUTURE FUNCTIONALITY OF THE CRUCIBLE
     * <p>
     * When given raw materials (iron, copper, or gold) or an ore block (all the other types),
     * turn that into a molten version of itself (not actually obtainable, but for visual purposed).
     * Raw materials result in more molten liquid vs ore blocks (ratio of 4:3), ie. raw materials give 400 while ores give 300; exception for lapis and redstone.
     * The molten metals slowly cool (maybe use a formula or something) and turn into ingots or the actual material.
     * The molten metals can cool quicker if supplied with water.
     */
    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<MeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(MeltingRecipe.Type.INSTANCE, inventory, level);
        recipe.ifPresent(meltingRecipe -> blockEntity.maxProgress = meltingRecipe.getTime());

        if(blockEntity.isActive()) blockEntity.fuel--;

        if(canMelt(inventory, recipe) && blockEntity.liquidAmount + recipe.get().getResultItem().getCount() * 100 <= LIQUID_CAPACITY) {
            if(!blockEntity.isActive()) {
                double constant = blockEntity.getFuelTime(blockEntity.itemHandler.getStackInSlot(0)) / 200.0;
                blockEntity.maxFuel = (int) (blockEntity.maxProgress * constant);
                blockEntity.fuel = blockEntity.maxFuel;
                blockEntity.itemHandler.extractItem(0, 1, false);
            }

            if(blockEntity.isActive()) {
                blockEntity.progress++;
                if (blockEntity.progress == blockEntity.maxProgress) {
                    blockEntity.itemHandler.extractItem(1, 1, false);
                    blockEntity.liquid = MoltenLiquids.getLiquid(recipe.get().getResultItem());
                    blockEntity.liquidAmount += recipe.get().getResultItem().getCount() * 100;

                    blockEntity.progress = 0;
                }
            }
        }

        boolean bucket = blockEntity.itemHandler.getStackInSlot(2).is(Items.WATER_BUCKET);
        if(bucket && blockEntity.fluidTank.getFluidAmount() + 1000 < WATER_CAPACITY) {
            blockEntity.itemHandler.setStackInSlot(2, new ItemStack(Items.BUCKET));
            blockEntity.fluidTank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
        }

        if(blockEntity.liquidAmount > 0 && !bucket) {
            blockEntity.liquidAmount--;
            blockEntity.coolingProgress++;
        } else if(blockEntity.liquidAmount > 1) {
            blockEntity.liquidAmount -= 2;
            blockEntity.fluidTank.drain(2, IFluidHandler.FluidAction.EXECUTE);
            blockEntity.coolingProgress += 2;
        }

        if(blockEntity.coolingProgress == 100) {
            blockEntity.itemHandler.setStackInSlot(3, blockEntity.liquid.getSolidItem(blockEntity.itemHandler.getStackInSlot(3).getCount() + 1));
            blockEntity.coolingProgress = 0;
        }

        if(blockEntity.itemHandler.getStackInSlot(1).isEmpty()) blockEntity.progress = 0;
        setChanged(level, pos, state);
    }

    private static boolean canMelt(SimpleContainer inventory, Optional<MeltingRecipe> recipe) {
        int output = inventory.getContainerSize() - 1;
        return recipe.isPresent() && validOutput(inventory, recipe.get().getResultItem(), output);
    }

    private static boolean validOutput(SimpleContainer container, ItemStack stack, int slot) {
        return (container.getItem(slot).sameItem(stack) || container.getItem(slot).isEmpty()) && container.getItem(slot).getCount() < container.getItem(slot).getMaxStackSize();
    }

    private boolean isActive() {
        return this.fuel > 0;
    }

    private int getFuelTime(ItemStack stack) {
        return stack.isEmpty() ? 0 : ForgeHooks.getBurnTime(stack, null);
    }

    public void addWater(int amount) {
        fluidTank.fill(new FluidStack(Fluids.WATER, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public int getWater() {
        return fluidTank.getFluidAmount();
    }
}
