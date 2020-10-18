package com.unrealdinnerbone.simplytech.machines.extruder;

import com.unrealdinnerbone.lib.recipe.base.BasicMachineRecipe;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class ExtruderRecipe extends BasicMachineRecipe<ExtruderTileEntity> {

    public ExtruderRecipe(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy) {
        super(resourceLocation, inputIngredient, output, energy);
    }

    @Override
    public RecipePair<ExtruderTileEntity, ?, ?> getPair() {
        return SimplyRecipes.EXTRUDER_TYPE;
    }

    public static class Serializer extends BasicMachineRecipe.Serializer<ExtruderTileEntity, ExtruderRecipe> {

        @Override
        public ExtruderRecipe create(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy) {
            return new ExtruderRecipe(resourceLocation, inputIngredient, output, energy);
        }
    }
}
