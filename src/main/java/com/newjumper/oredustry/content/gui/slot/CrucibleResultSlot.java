package com.newjumper.oredustry.content.gui.slot;

import com.newjumper.oredustry.content.blocks.entity.CrucibleBlockEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings("NullableProblems")
public class CrucibleResultSlot extends ResultSlot {
    private final CrucibleBlockEntity blockEntity;
    private final Player player;
    private int removeCount;

    public CrucibleResultSlot(CrucibleBlockEntity blockEntity, Player player, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.blockEntity = blockEntity;
        this.player = player;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        this.checkTakeAchievements(pStack);
        super.onTake(pPlayer, pStack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack pStack) {
        pStack.onCraftedBy(this.player.level(), this.player, this.removeCount);
        if(this.player instanceof ServerPlayer) this.blockEntity.awardUsedRecipesAndPopExperience((ServerPlayer) this.player);
        this.removeCount = 0;
    }
}
