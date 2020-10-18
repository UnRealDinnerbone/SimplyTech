package com.unrealdinnerbone.lib.tile;

import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDirectionFacing {

    default Direction getFacing(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).get(FacingTileDirectionBlock.FACING);
    }
}
