package com.unrealdinnerbone.lib.inventory;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.util.inventory.SimplyItemStackHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class UpgradeInventory extends SimplyItemStackHandler implements INBTStorage {

    private final WrappedItemHandlerInventory itemHandlerInventory;

    public UpgradeInventory(Runnable markedDirty) {
        super(2, integer -> {});
        this.itemHandlerInventory = new WrappedItemHandlerInventory(this, markedDirty);
    }

    @Override
    public String getName() {
        return "upgrade";
    }

    @Override
    public CompoundNBT toNBT() {
        return serializeNBT();
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        deserializeNBT(nbt);
    }

    public WrappedItemHandlerInventory getItemHandlerInventory() {
        return itemHandlerInventory;
    }
}
