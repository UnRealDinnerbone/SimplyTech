package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.api.TransferType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimplyStackHandler implements IItemHandlerModifiable {

    private final boolean isInput;
    private final ItemStackHandler handler;
    private final Map<Integer, Integer> slotMap = new HashMap<>();
    private final Supplier<ItemStackHandler> handlerSupplier;

    public SimplyStackHandler(Supplier<ItemStackHandler> itemStackHandler, boolean isInput, int... slots) {
        this.handlerSupplier = itemStackHandler;
        this.isInput = isInput;
        this.handler = new ItemStackHandler(slots.length);

        for (int i = 0; i < slots.length; i++) {
            int value = slots[i];
            slotMap.put(i, value);
            handler.setStackInSlot(i, itemStackHandler.get().getStackInSlot(value));
        }
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        handlerSupplier.get().setStackInSlot(slotMap.get(slot), stack);
    }

    @Override
    public int getSlots() {
        return handler.getSlots();
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return handlerSupplier.get().getStackInSlot(slot);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(isInput) {
            return handlerSupplier.get().insertItem(slotMap.get(slot), stack, simulate);
        }else {
            return ItemStack.EMPTY;
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if(!isInput) {
            return handlerSupplier.get().extractItem(slotMap.get(slot), amount, simulate);
        }else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return handlerSupplier.get().getSlotLimit(slotMap.get(slot));
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return  handlerSupplier.get().isItemValid(slotMap.get(slot), stack);
    }
}
