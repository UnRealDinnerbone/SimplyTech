package com.unrealdinnerbone.lib.recipe;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

public abstract class SimplyRecipe<I extends ISimplyInventory> implements IRecipe<I> {

    private final ResourceLocation id;
    private final ItemStack output;

    public SimplyRecipe(ResourceLocation id, ItemStack output) {
        this.id = id;
        this.output = output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return output;
    }

    @Override
    public ItemStack getCraftingResult(I inv) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return getPair().getSerializer();
    }

    @Override
    public IRecipeType<?> getType() {
        return getPair().getRecipeType();
    }

    public abstract RecipePair<I, ?, ?> getPair();

}
