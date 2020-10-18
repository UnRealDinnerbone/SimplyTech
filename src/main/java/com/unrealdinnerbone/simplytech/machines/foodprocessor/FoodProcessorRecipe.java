package com.unrealdinnerbone.simplytech.machines.foodprocessor;

import com.unrealdinnerbone.lib.recipe.base.BasicMachineRecipe;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class FoodProcessorRecipe extends BasicMachineRecipe<FoodProcessorTileEntity> {

    public FoodProcessorRecipe(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy) {
        super(resourceLocation, inputIngredient, output, energy);
    }

    @Override
    public RecipePair<FoodProcessorTileEntity, ?, ?> getPair() {
        return SimplyRecipes.FOOD_PROCESSOR;
    }

    public static class Serializer extends BasicMachineRecipe.Serializer<FoodProcessorTileEntity, FoodProcessorRecipe> {

        @Override
        public FoodProcessorRecipe create(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy) {
            return new FoodProcessorRecipe(resourceLocation, inputIngredient, output, energy);
        }
    }
}
