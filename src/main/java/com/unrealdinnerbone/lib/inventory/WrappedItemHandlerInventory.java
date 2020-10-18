package com.unrealdinnerbone.lib.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.stream.IntStream;

public class WrappedItemHandlerInventory implements IInventory {

    private final ItemStackHandler handler;
    private final Runnable markedDirty;

    public WrappedItemHandlerInventory(ItemStackHandler handler, Runnable markedDirty) {
        this.handler = handler;
        this.markedDirty = markedDirty;
    }

    @Override
    public int getSizeInventory() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return IntStream.range(0, getSizeInventory()).allMatch(i -> handler.getStackInSlot(i).isEmpty());
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return handler.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return handler.extractItem(index, count, false);
    }


    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        handler.setStackInSlot(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        handler.setStackInSlot(index, stack);
    }

    @Override
    public void markDirty() {
        markedDirty.run();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }


    @Override
    public void clear() {
        int bound = handler.getSlots();
        for (int i = 0; i < bound; i++) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
