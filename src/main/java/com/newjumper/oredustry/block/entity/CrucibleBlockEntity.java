package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.CrucibleBlock;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.MeltingRecipe;
import com.newjumper.oredustry.screen.CrucibleMenu;
import com.newjumper.oredustry.util.MoltenLiquids;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
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
                case 5 -> CrucibleBlockEntity.this.maxCooling;
                case 6 -> CrucibleBlockEntity.this.liquidAmount;
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
                case 5 -> CrucibleBlockEntity.this.maxCooling = value;
                case 6 -> CrucibleBlockEntity.this.liquidAmount = value;
            }
        }

        public int getCount() {
            return 7;
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
    private int maxCooling;
    private int liquidAmount;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    public MoltenLiquids liquid;

    public CrucibleBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.CRUCIBLE.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(6) {
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

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<MeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(MeltingRecipe.Type.INSTANCE, inventory, level);
        recipe.ifPresent(meltingRecipe -> blockEntity.maxProgress = meltingRecipe.getTime());

        if(blockEntity.isActive()) blockEntity.fuel--;
        if(!blockEntity.getBlockState().getValue(CrucibleBlock.WARM) == blockEntity.liquidAmount > 0) {
            state = state.setValue(CrucibleBlock.WARM, blockEntity.liquidAmount > 0);
            level.setBlock(pos, state, 3);
        }

        if(canMelt(inventory, recipe) && blockEntity.liquidAmount + recipe.get().getResultItem().getCount() * 150 <= LIQUID_CAPACITY) {
            if(!blockEntity.isActive()) {
                double constant = ForgeHooks.getBurnTime(blockEntity.itemHandler.getStackInSlot(0), null) / 200.0;
                blockEntity.maxFuel = (int) (blockEntity.maxProgress * constant);
                blockEntity.fuel = blockEntity.maxFuel;
                ItemStack fuelRemainder = blockEntity.itemHandler.extractItem(0, 1, false).getCraftingRemainingItem();
                if(!fuelRemainder.isEmpty()) blockEntity.itemHandler.setStackInSlot(0, fuelRemainder);
            }

            if(blockEntity.isActive()) {
                blockEntity.progress++;
                if(blockEntity.progress == blockEntity.maxProgress) {
                    blockEntity.itemHandler.extractItem(1, 1, false);
                    blockEntity.liquid = MoltenLiquids.getLiquid(recipe.get().getResultItem());
                    blockEntity.maxCooling = blockEntity.liquid.getCapacity();
                    blockEntity.liquidAmount += recipe.get().getResultItem().getCount() * blockEntity.maxCooling;

                    blockEntity.progress = 0;
                    blockEntity.recipesUsed.addTo(recipe.get().getId(), 1);
                }
            }
        }

        if(blockEntity.itemHandler.getStackInSlot(2).is(Items.WATER_BUCKET) && blockEntity.fluidTank.getFluidAmount() + 1000 < WATER_CAPACITY) {
            blockEntity.itemHandler.setStackInSlot(2, new ItemStack(Items.BUCKET));
            blockEntity.fluidTank.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
        }

        if(blockEntity.liquidAmount > 0 && blockEntity.fluidTank.getFluidAmount() < 2) {
            blockEntity.liquidAmount--;
            blockEntity.coolingProgress++;
        } else if(blockEntity.liquidAmount > 1) {
            blockEntity.liquidAmount -= 2;
            blockEntity.fluidTank.drain(2, IFluidHandler.FluidAction.EXECUTE);
            blockEntity.coolingProgress += 2;
        } else if(blockEntity.liquidAmount == 1 && blockEntity.fluidTank.getFluidAmount() >= 2) {
            blockEntity.liquidAmount--;
            blockEntity.fluidTank.drain(1, IFluidHandler.FluidAction.EXECUTE);
            blockEntity.coolingProgress++;
        }

        if(blockEntity.liquid != null && blockEntity.coolingProgress >= blockEntity.liquid.getCapacity()) {
            blockEntity.itemHandler.setStackInSlot(3, blockEntity.liquid.getSolidItem(blockEntity.itemHandler.getStackInSlot(3).getCount() + 1));
            blockEntity.coolingProgress = 0;
        }

        if(blockEntity.itemHandler.getStackInSlot(1).isEmpty()) blockEntity.progress = 0;
        setChanged(level, pos, state);
    }

    private static boolean canMelt(SimpleContainer inventory, Optional<MeltingRecipe> recipe) {
        return recipe.isPresent() && validOutput(inventory, recipe.get().getResultItem());
    }

    private static boolean validOutput(SimpleContainer container, ItemStack stack) {
        return (container.getItem(3).sameItem(stack) || container.getItem(3).isEmpty()) && container.getItem(3).getCount() < container.getItem(3).getMaxStackSize();
    }

    private boolean isActive() {
        return this.fuel > 0;
    }

    public void addWater(int amount) {
        fluidTank.fill(new FluidStack(Fluids.WATER, amount), IFluidHandler.FluidAction.EXECUTE);
    }

    public int getWater() {
        return fluidTank.getFluidAmount();
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 vec) {
        List<Recipe<?>> list = new ArrayList<>();
        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(level, vec, entry.getIntValue(), ((MeltingRecipe)recipe).getExperience());
            });
        }

        return list;
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.getLevel(), player.position());
        player.awardRecipes(list);
        this.recipesUsed.clear();
    }

    private static void createExperience(ServerLevel level, Vec3 vec, int index, float experience) {
        int i = Mth.floor(index * experience);
        float f = Mth.frac(index * experience);
        if(f != 0 && Math.random() < f) i++;

        ExperienceOrb.award(level, vec, i);
    }
}
