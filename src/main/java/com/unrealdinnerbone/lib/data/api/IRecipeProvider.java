package com.unrealdinnerbone.lib.data.api;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;

import java.util.function.Consumer;

public interface IRecipeProvider {
    void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer);
}
