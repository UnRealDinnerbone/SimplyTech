package com.unrealdinnerbone.lib.util.pair;

import com.mojang.datafixers.util.Pair;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;

public class RecipePair<I extends ISimplyInventory, R extends SimplyRecipe<I>, S extends IRecipeSerializer<R>> extends Pair<SimplyRecipeType<I, R>, RegistryObject<S>> {

    public RecipePair(SimplyRecipeType<I, R> recipe, RegistryObject<S> serializer) {
        super(recipe, serializer);
    }

    public SimplyRecipeType<I, R> getRecipeType() {
        return super.getFirst();
    }

    public S getSerializer() {
        return super.getSecond().get();
    }

}
