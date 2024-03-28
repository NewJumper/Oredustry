package com.newjumper.oredustry.network;

import com.newjumper.oredustry.block.entity.MinerBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketMachineButton {
    private final int x;
    private final int y;
    private final int z;
    private final int index;
    private final int value;

    public PacketMachineButton(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.index = buf.readInt();
        this.value = buf.readInt();
    }

    public PacketMachineButton(BlockPos pos, int index, int value) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.index = index;
        this.value = value;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(index);
        buf.writeInt(value);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Level level = context.get().getSender().level();
            BlockPos pos = new BlockPos(x, y, z);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(level.isLoaded(pos) && blockEntity instanceof MinerBlockEntity miner) {
                miner.data.set(index, value);
                miner.getLevel().markAndNotifyBlock(pos, level.getChunkAt(pos), blockEntity.getLevel().getBlockState(pos).getBlock().defaultBlockState(), blockEntity.getLevel().getBlockState(pos), 2, 0);
                miner.setChanged();
            }
        });
        context.get().setPacketHandled(true);
    }
}
