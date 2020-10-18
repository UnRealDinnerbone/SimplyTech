package com.unrealdinnerbone.lib.util.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Optional;
import java.util.function.Consumer;

public class SimplyItemStackHandler extends ItemStackHandler {

    private final Optional<Consumer<Integer>> integerConsumer;

    public SimplyItemStackHandler() {
        super();
        integerConsumer = Optional.empty();
    }

    public SimplyItemStackHandler(int size, Consumer<Integer> onSlotUpdated) {
        super(size);
        integerConsumer = Optional.of(onSlotUpdated);
    }

    public SimplyItemStackHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
        integerConsumer = Optional.empty();
    }

    public boolean isStackEqual(ItemStack itemStackOne, ItemStack itemStackTwo) {
        return itemStackOne.equals(itemStackTwo) || ItemStack.areItemsEqual(itemStackOne, itemStackTwo);
    }

    public boolean isEmpty(ItemStack itemStackOne) {
        return itemStackOne.isEmpty();
    }

    public boolean isEmpty(int slot) {
        return isEmpty(getStackInSlot(slot));
    }

    public boolean moveAndChangeItemStack(int from, int to, Item typeTo, int amount) {
        if(isEmpty(from)) {
            return false;
        }else {
            if(getStackInSlot(to).getCount() + amount <= 64) {
                extractItem(from, 1, false);
                increaseStackSize(to, amount, typeTo);
                return true;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        ItemStack itemStack = getStackInSlot(slot);
        if(itemStack.isEmpty()) {
            return true;
        }else {
            return stack.getCount() < itemStack.getMaxStackSize();
        }
    }

    public boolean canSlotStore(int slot, ItemStack stack) {
        ItemStack itemStack = getStackInSlot(slot);
        return (itemStack.getItem() == stack.getItem() || itemStack.isEmpty()) && itemStack.getCount() + stack.getCount() <= stack.getMaxStackSize();
    }


    /**
     * @param index the slot
     * @param count the amount
     * @return the items not inserted
     */
    public ItemStack increaseStackSize(int index, int count) {
        ItemStack stackToIncrease = getStackInSlot(index);
        if(stackToIncrease.getCount() + count < 64) {
            stackToIncrease.grow(count);
            return ItemStack.EMPTY;
        } else {
            int theCount = (stackToIncrease.getCount() + count) - 64;
            stackToIncrease.setCount(64);
            return new ItemStack(getStackInSlot(index).getItem(), theCount);
        }
    }

    public ItemStack increaseStackSize(int index, int count, Item defaultItem) {
        if(isEmpty(index)) {
            ItemStack itemStack = new ItemStack(defaultItem, count);
            setStackInSlot(index, itemStack);
            return itemStack;
        }else {
            return increaseStackSize(index, count);
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        integerConsumer.ifPresent(integerConsumer1 -> integerConsumer1.accept(slot));
    }
}
