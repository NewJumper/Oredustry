package com.newjumper.oredustry.block.entity;

import com.newjumper.oredustry.Oredustry;
import com.newjumper.oredustry.block.MachineBlock;
import com.newjumper.oredustry.block.OredustryBlocks;
import com.newjumper.oredustry.recipe.CompressingRecipe;
import com.newjumper.oredustry.screen.CompressorMenu;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
public class CompressorBlockEntity extends BlockEntity implements MenuProvider {
    protected final ContainerData data = new ContainerData() {
        public int get(int index) {
            return switch (index) {
                case 0 -> CompressorBlockEntity.this.fuel;
                case 1 -> CompressorBlockEntity.this.maxFuel;
                case 2 -> CompressorBlockEntity.this.progress;
                case 3 -> CompressorBlockEntity.this.maxProgress;
                default -> 0;
            };
        }

        public void set(int index, int value) {
            switch (index) {
                case 0 -> CompressorBlockEntity.this.fuel = value;
                case 1 -> CompressorBlockEntity.this.maxFuel = value;
                case 2 -> CompressorBlockEntity.this.progress = value;
                case 3 -> CompressorBlockEntity.this.maxProgress = value;
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

    public CompressorBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(OredustryBlockEntities.COMPRESSOR.get(), pWorldPosition, pBlockState);

        this.itemHandler = new ItemStackHandler(5) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };

        this.lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container." + Oredustry.MOD_ID + "." + OredustryBlocks.COMPRESSOR.getId().getPath());
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CompressorMenu(pContainerId, pPlayerInventory, this, this.data);
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

    public static void tick(Level level, BlockPos pos, BlockState state, CompressorBlockEntity blockEntity) {
        SimpleContainer inventory = new SimpleContainer(blockEntity.itemHandler.getSlots());
        for(int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, blockEntity.itemHandler.getStackInSlot(i));
        }

        Optional<CompressingRecipe> recipe = level.getRecipeManager().getRecipeFor(CompressingRecipe.Type.INSTANCE, inventory, level);
        recipe.ifPresent(compressingRecipe -> blockEntity.maxProgress = (20 - compressingRecipe.getTime()) / 8 * blockEntity.itemHandler.getStackInSlot(4).getCount() + compressingRecipe.getTime());

        boolean isActive = blockEntity.isActive();
        boolean isBlockStateActive = blockEntity.getBlockState().getValue(MachineBlock.ACTIVE);

        if(isActive) {
            blockEntity.fuel--;
            if(!isBlockStateActive) {
                state = state.setValue(MachineBlock.ACTIVE, true);
                level.setBlock(pos, state, 3);
            }
        } else if(isBlockStateActive) {
            state = state.setValue(MachineBlock.ACTIVE, false);
            level.setBlock(pos, state, 3);
        }

        RegistryAccess registry = level.registryAccess();
        if(canCompress(registry, inventory, recipe) && !isActive) {
            double constant = ForgeHooks.getBurnTime(blockEntity.itemHandler.getStackInSlot(0), null) / 200.0;
            constant += blockEntity.itemHandler.getStackInSlot(3).getCount() * constant / 8;
            blockEntity.maxFuel = (int) (blockEntity.maxProgress * constant);
            blockEntity.fuel = blockEntity.maxFuel;
            ItemStack fuelRemainder = blockEntity.itemHandler.extractItem(0, 1, false).getCraftingRemainingItem();
            if(!fuelRemainder.isEmpty()) blockEntity.itemHandler.setStackInSlot(0, fuelRemainder);
        }

        if(canCompress(registry, inventory, recipe) && isActive) {
            blockEntity.progress++;
            if(blockEntity.progress >= blockEntity.maxProgress) {
                blockEntity.itemHandler.extractItem(1, 1, false);
                blockEntity.itemHandler.setStackInSlot(2, new ItemStack(recipe.get().getResultItem(registry).getItem(), blockEntity.itemHandler.getStackInSlot(2).getCount() + recipe.get().getResultItem(registry).getCount()));
                blockEntity.progress = 0;
                blockEntity.recipesUsed.addTo(recipe.get().getId(), 1);
            }
        }

        if(blockEntity.itemHandler.getStackInSlot(1).isEmpty()) blockEntity.progress = 0;
        setChanged(level, pos, state);
    }

    private static boolean canCompress(RegistryAccess registry, SimpleContainer inventory, Optional<CompressingRecipe> recipe) {
        return recipe.isPresent() && (inventory.getItem(2).is(recipe.get().getResultItem(registry).getItem()) || inventory.getItem(2).isEmpty()) && inventory.getItem(2).getCount() < inventory.getItem(2).getMaxStackSize();
    }

    private boolean isActive() {
        return this.fuel > 0;
    }

    public List<Recipe<?>> getRecipesToAwardAndPopExperience(ServerLevel level, Vec3 vec) {
        List<Recipe<?>> list = new ArrayList<>();
        for(Object2IntMap.Entry<ResourceLocation> entry : this.recipesUsed.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                createExperience(level, vec, entry.getIntValue(), ((CompressingRecipe)recipe).getExperience());
            });
        }

        return list;
    }

    public void awardUsedRecipesAndPopExperience(ServerPlayer player) {
        List<Recipe<?>> list = this.getRecipesToAwardAndPopExperience(player.serverLevel(), player.position());
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
