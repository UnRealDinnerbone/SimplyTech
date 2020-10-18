package com.unrealdinnerbone.lib.api.quarry;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockSuppler extends IUpgrade {

    boolean supplyStone(World world, Direction direction, BlockPos blockPos);

    boolean hasStone(World world, Direction direction, BlockPos blockPos);
}
