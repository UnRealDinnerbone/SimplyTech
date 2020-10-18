package com.unrealdinnerbone.lib.recipe;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class SimplyRecipeSerializer<I extends ISimplyInventory, T extends SimplyRecipe<I>> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T>{
}
