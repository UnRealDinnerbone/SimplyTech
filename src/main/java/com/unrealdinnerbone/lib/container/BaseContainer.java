package com.unrealdinnerbone.lib.container;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BaseContainer<T extends BaseTileEntity & ISimplyInventory> extends Container {

    private final T tileEntity;
    private final int slots;
    private final List<Slot> customSlots = new ArrayList<>();

    public BaseContainer(ContainerType<?> type, List<Function<T, Slot>> slots, int id, T tileEntity, PlayerInventory playerInventory) {
        super(type, id);
        this.tileEntity = tileEntity;
        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        final int slotSizePlus2 = 18;
        slots.forEach(tSlotFunction -> customSlots.add(this.addSlot(tSlotFunction.apply(tileEntity))));
        this.slots = this.inventorySlots.size();
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }
        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }
    }


    public List<Slot> getCustomSlots() {
        return customSlots;
    }

    public T getTileEntity() {
        return tileEntity;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return tileEntity.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack = slot.getStack();
            stack = itemStack.copy();
            if (index < slots) {
                if (!mergeItemStack(itemStack, slots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(itemStack, 0, slots, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

}
