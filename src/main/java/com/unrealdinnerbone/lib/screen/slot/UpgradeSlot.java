package com.unrealdinnerbone.lib.screen.slot;

import com.unrealdinnerbone.simplytech.items.UpgradeItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class UpgradeSlot extends Slot {

    private final Class<? extends UpgradeItem> clazz;

    public UpgradeSlot(Class<? extends UpgradeItem> clazz, IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.clazz = clazz;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return clazz.equals(stack.getItem().getClass());
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
