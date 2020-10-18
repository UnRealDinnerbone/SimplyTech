package com.unrealdinnerbone.lib.screen.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;

public class InputSlot extends Slot {

    public InputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
}
