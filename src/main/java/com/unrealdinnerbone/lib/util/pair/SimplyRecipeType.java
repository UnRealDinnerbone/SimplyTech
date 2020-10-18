package com.unrealdinnerbone.lib.util.pair;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.crafting.IRecipeType;

public class SimplyRecipeType<I extends ISimplyInventory, T extends SimplyRecipe<I>> implements IRecipeType<T> {

    private final String key;

    public SimplyRecipeType(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
