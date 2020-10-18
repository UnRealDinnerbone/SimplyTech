package com.unrealdinnerbone.lib.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class ItemStackUtil
{
    public static void shrink(ItemStack itemStack, PlayerEntity playerEntity) {
        if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
            itemStack.shrink(1);
        }
    }
}
