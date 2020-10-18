package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Optional;

public class WorldUtil {

    public static <T extends BaseTileEntity, I extends ISimplyInventory> T getTileEntity(Class<T> tClass, World world, PacketBuffer data) {
        TileEntity tileEntity = world.getTileEntity(data.readBlockPos());
        if (tileEntity != null && tClass.isAssignableFrom(tileEntity.getClass())) {
            return tClass.cast(tileEntity);
        } else {
            throw new IllegalStateException("FIX ME");
        }
    }

    public static <T extends BaseTileEntity, I extends ISimplyInventory> T getTileEntity(Class<T> tClass, PlayerInventory playerInventory, PacketBuffer data) {
        return getTileEntity(tClass, playerInventory.player.world, data);
    }

    public static <T extends TileEntity> Optional<T> getTileEntity(Class<T> tClass, IWorldReader world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.getClass().isInstance(tClass) ? Optional.of(tClass.cast(tileEntity)) : Optional.empty();
    }
}
