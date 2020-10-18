package com.unrealdinnerbone.lib.screen.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LockableSlot extends Slot {

    private Item lockedItem;

    public LockableSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(lockedItem != null) {
            return stack.getItem() == lockedItem;
        }else {
            return super.isItemValid(stack);
        }
    }

    public void setLockedItem(Item lockedItem) {
        this.lockedItem = lockedItem;
    }
}
