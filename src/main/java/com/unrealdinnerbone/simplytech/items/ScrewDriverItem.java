package com.unrealdinnerbone.simplytech.items;

import com.unrealdinnerbone.lib.item.SimplyItem;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Rotation;
import net.minecraftforge.common.util.Constants;

public class ScrewDriverItem extends SimplyItem {

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        if(!context.getWorld().isRemote()) {
            BlockState blockState = context.getWorld().getBlockState(context.getPos());
            context.getWorld().setBlockState(context.getPos(), blockState.rotate(context.getWorld(), context.getPos(), context.getPlayer().isSneaking() ? Rotation.CLOCKWISE_90 : Rotation.COUNTERCLOCKWISE_90), Constants.BlockFlags.BLOCK_UPDATE);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }
}
