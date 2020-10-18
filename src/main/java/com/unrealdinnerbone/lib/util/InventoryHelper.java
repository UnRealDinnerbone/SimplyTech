package com.unrealdinnerbone.lib.util;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class InventoryHelper extends net.minecraft.inventory.InventoryHelper
{
    public static ItemStack addItemToInventory(IItemHandler outputInventory, ItemStack itemStack) {
        ItemStack stack = itemStack;
        for (int i = 0; i < outputInventory.getSlots(); i++) {
            if(stack.isEmpty()) {
                break;
            }else {
                stack = outputInventory.insertItem(i, stack, false);
            }
        }
        return stack;
    }

    public static ItemStack addItemToInventory(IItemHandler outputInventory, ItemStack itemStack, int limit) {
        ItemStack stack = itemStack.copy();
        stack.setCount(Math.min(stack.getCount(), limit));
        for (int i = 0; i < outputInventory.getSlots(); i++) {
            if(stack.isEmpty()) {
                break;
            }else {
                stack = outputInventory.insertItem(i, stack, false);
            }
        }
        return   itemStack.copy();
//        returnStack.setCount(itemStack.getCount() - (limit + stack.getCount()));
//        return returnStack;
    }

    public static boolean dropInventoryContents(World world, BlockPos blockPos) {
        TileEntity tileentity = world.getTileEntity(blockPos);
        if (tileentity instanceof IInventory) {
            dropInventoryItems(world, blockPos, (IInventory) tileentity);
            return true;
        }else {
            return false;
        }
    }
}
