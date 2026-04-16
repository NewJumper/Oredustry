package com.newjumper.oredustry.content.blocks.entity;

import com.newjumper.oredustry.content.OredustryBlocks;
import com.newjumper.oredustry.content.blocks.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("NullableProblems")
public class ForgeControllerBlockEntity extends BlockEntity implements MenuProvider {
    public ForgeControllerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(OredustryBlockEntities.FORGE_CONTROLLER.get(), pPos, pBlockState);
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ForgeControllerBlockEntity blockEntity) {

    }

    public void search(Player player) {
        Direction facing = this.getBlockState().getValue(MachineBlock.FACING);
        Direction direction = Direction.from2DDataValue((facing.get2DDataValue() + 3) % 4);
        BlockPos startPos = this.getBlockPos();
        BlockPos.MutableBlockPos checkPos = startPos.mutable();

        int length = 0;
        int width = 0;
        boolean complete = false;
        assert level != null;
        String message = "";

        while(true) {
            if(direction == Direction.WEST) {
                if(level.getBlockState(checkPos.south()).is(OredustryBlocks.FORGE_BASE.get()) || level.getBlockState(checkPos.south()).is(OredustryBlocks.FORGE_CONTROLLER.get())) {
                    direction = Direction.SOUTH;
                    width++;
                    if(direction == facing && checkPos.getX() >= startPos.getX()) {
                        message = "s: invalid block at " + checkPos.toShortString();
                        break;
                    }
                }
            } else if(direction == Direction.SOUTH) {
                if(level.getBlockState(checkPos.east()).is(OredustryBlocks.FORGE_BASE.get()) || level.getBlockState(checkPos.east()).is(OredustryBlocks.FORGE_CONTROLLER.get())) {
                    direction = Direction.EAST;
                    length++;
                    if(direction == facing && checkPos.getZ() <= startPos.getZ()) {
                        message = "e: invalid block at " + checkPos.toShortString();
                        break;
                    }
                } else {
                    width++;
                }
            } else if(direction == Direction.EAST) {
                if(level.getBlockState(checkPos.north()).is(OredustryBlocks.FORGE_BASE.get()) || level.getBlockState(checkPos.north()).is(OredustryBlocks.FORGE_CONTROLLER.get())) {
                    direction = Direction.NORTH;
                    if(direction == facing && checkPos.getX() <= startPos.getX()) {
                        message = "n: invalid block at " + checkPos.toShortString();
                        break;
                    }
                } else {
                    length++;
                }
            } else {
                if(level.getBlockState(checkPos.west()).is(OredustryBlocks.FORGE_BASE.get()) || level.getBlockState(checkPos.west()).is(OredustryBlocks.FORGE_CONTROLLER.get())) {
                    direction = Direction.WEST;
                    if(direction == facing && checkPos.getZ() >= startPos.getZ()) {
                        message = "w: invalid block at " + checkPos.toShortString();
                        break;
                    }
                }
            }
            checkPos.move(direction);

            if(!level.getBlockState(checkPos).is(OredustryBlocks.FORGE_BASE.get()) && !level.getBlockState(checkPos).is(OredustryBlocks.FORGE_CONTROLLER.get())) {
                message = "block at " + checkPos.toShortString() + " is not forge block";
                break;
            }
            if(level.getBlockState(checkPos).is(OredustryBlocks.FORGE_CONTROLLER.get()) && !checkPos.equals(startPos)) {
                message = "more than 1 controller is not allowed";
                break;
            }
            if(checkPos.equals(startPos)) {
                complete = true;
                break;
            }
        }

        length++;
        width++;
        int area = (length - 2) * (width - 2);
        if(length < 3 || width < 3) {
            message = "forge is too small";
            complete = false;
        }

        if(!level.isClientSide()) {
            System.out.println("length: " + length);
            System.out.println("width: " + width);
            System.out.println("area: " + area);
            System.out.println("perimeter: " + (2 * (length + width) - 4));
        }
        if(complete) message = "complete";
        player.displayClientMessage(Component.literal(message), true);
    }
}
