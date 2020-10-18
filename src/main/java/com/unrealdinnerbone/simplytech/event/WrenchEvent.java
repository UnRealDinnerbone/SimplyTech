package com.unrealdinnerbone.simplytech.event;

import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.registries.SimplyTags;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class WrenchEvent {
    public static void onWrenched(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getWorld().isRemote()) {
            if(!event.getItemStack().isEmpty()) {
                if(event.getItemStack().getItem().isIn(SimplyTags.WRENCH)) {
                    BlockState blockState = event.getWorld().getBlockState(event.getPos());
                    if (blockState.getBlock() instanceof FacingTileDirectionBlock) {
                        Direction facing = event.getFace();
                        if (facing != blockState.get(FacingTileDirectionBlock.FACING)) {
                            TileEntity tileEntity = event.getWorld().getTileEntity(event.getPos());
                            if (tileEntity instanceof BasicRecipeTileEntity) {
                                BasicRecipeTileEntity<?, ?, ?> basicRecipeTileEntity = (BasicRecipeTileEntity<?, ?, ?>) tileEntity;
                                TransferType transferType = basicRecipeTileEntity.getTransferTypeForDirection(facing);
                                basicRecipeTileEntity.setTransferTypeForDirection(facing, transferType.next(basicRecipeTileEntity.getAllowedTransferTypes()));
                            }
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
