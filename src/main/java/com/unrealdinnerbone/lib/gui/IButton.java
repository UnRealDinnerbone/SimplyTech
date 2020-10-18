package com.unrealdinnerbone.lib.gui;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;

public interface IButton<T extends BaseTileEntity & ISimplyInventory> {
    void onPressed(T t, double xPos, double yPos);

    boolean isInRange(double xPos, double yPos, double mouseX, double mouseY);
}
