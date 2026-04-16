package com.newjumper.oredustry.content.blocks.multiblock;

import net.minecraft.core.BlockPos;

import java.util.ArrayList;

public class MultiBlockData {
    private BlockPos parent;
    private BlockPos corner;
    private int xSize;
    private int ySize;
    private int zSize;

    public BlockPos getParent() {
        return parent;
    }

    public BlockPos getCorner() {
        return corner;
    }

    public int getVolume() {
        return xSize * ySize * zSize;
    }

    public void getList() {
        ArrayList<BlockPos> list = new ArrayList<>();
        BlockPos.MutableBlockPos pos = corner.mutable();
        for(int i = 0; i < )
    }
}
