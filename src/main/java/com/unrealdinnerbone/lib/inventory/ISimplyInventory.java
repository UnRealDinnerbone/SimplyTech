package com.unrealdinnerbone.lib.inventory;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.api.TransferType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.IntStream;

public interface ISimplyInventory extends ISidedInventory, INBTStorage, INamedContainerProvider {

    ItemStackHandler getStackHandler();

    @Override
    default int getSizeInventory() {
        return getStackHandler().getSlots();
    }

    @Override
    default boolean isEmpty() {
        return IntStream.range(0, getSizeInventory()).allMatch(i -> getStackHandler().getStackInSlot(i).isEmpty());
    }

    @Override
    default ItemStack getStackInSlot(int index) {
        return getStackHandler().getStackInSlot(index);
    }

    @Override
    default ItemStack decrStackSize(int index, int count) {
        return getStackHandler().extractItem(index, count, false);
    }


    @Override
    default ItemStack removeStackFromSlot(int index) {
        ItemStack stack = getStackInSlot(index);
        getStackHandler().setStackInSlot(index, ItemStack.EMPTY);
        return stack;
    }

    @Override
    default void setInventorySlotContents(int index, ItemStack stack) {
        getStackHandler().setStackInSlot(index, stack);
    }

    @Override
    default boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    default void clear() {
        int bound = getStackHandler().getSlots();
        for (int i = 0; i < bound; i++) {
            getStackHandler().setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    default CompoundNBT toNBT() {
        return getStackHandler().serializeNBT();
    }

    @Override
    default void fromNBT(CompoundNBT nbt) {
        getStackHandler().deserializeNBT(nbt);
    }

    @Override
    default String getName() {
        return "inventory";
    }

    @Override
    default int[] getSlotsForFace(Direction side) {
        int[] ints;
        ints = getSlotForType(getTransferTypeForDirection(side)).map(integer -> new int[]{integer}).orElseGet(() -> new int[0]);
        return ints;
    }

    @Override
    default boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        if(direction != null) {
            TransferType transferType = getTransferTypeForDirection(direction);
            return transferType.isInput() && getSlotForType(transferType).filter(integer -> getStackHandler().isItemValid(integer, itemStackIn)).isPresent();
        }else {
            return false;
        }
    }

    @Override
    default boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        TransferType transferType = getTransferTypeForDirection(direction);
        return transferType.isOutput() && getSlotForType(transferType).filter(integer -> integer.equals(index)).isPresent();
    }

    Optional<Integer> getSlotForType(TransferType transferType);

    void onSlotUpdated(int i);

    boolean matches(World worldIn, Ingredient inputIngredient);

    void setTransferTypeForDirection(Direction direction, TransferType transferType);

    TransferType getTransferTypeForDirection(Direction direction);

}
