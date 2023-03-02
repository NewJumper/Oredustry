package com.newjumper.oredustry.screen.slot;

import com.newjumper.oredustry.block.entity.SeparatorBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@SuppressWarnings("NullableProblems")
public class SeparatorResultSlot extends SlotItemHandler {
    private final SeparatorBlockEntity blockEntity;
    private final Player player;
    private int removeCount;

    public SeparatorResultSlot(SeparatorBlockEntity blockEntity, Player player, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.blockEntity = blockEntity;
        this.player = player;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        super.onTake(pPlayer, pStack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack pStack) {
        pStack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if(this.player instanceof ServerPlayer) this.blockEntity.awardUsedRecipesAndPopExperience((ServerPlayer) this.player);
        this.removeCount = 0;
    }
}
