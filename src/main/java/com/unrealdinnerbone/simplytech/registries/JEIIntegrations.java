package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import com.unrealdinnerbone.lib.recipe.base.BasicMachineRecipe;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderRecipe;
import com.unrealdinnerbone.simplytech.machines.foodprocessor.FoodProcessorRecipe;
import com.unrealdinnerbone.simplytech.machines.foodprocessor.FoodProcessorTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JEIIntegrations
{
    private static List<GenericJEIIntegration<?, ?>> integrations = null;

    public static List<GenericJEIIntegration<?, ?>> getIntegrations() {
        if(integrations == null) {
            integrations = new ArrayList<>();
            register();
        }
        return integrations;
    }

    private static void register() {
        integrations.add(new GenericJEIIntegration<>(SimplyRecipes.FOOD_PROCESSOR.getRecipeType(), FoodProcessorRecipe.class, () -> new ItemStack(SimplyMachines.FOOD_PROCESSOR)));
        integrations.add(new GenericJEIIntegration<>(SimplyRecipes.EXTRUDER_TYPE.getRecipeType(), ExtruderRecipe.class, () -> new ItemStack(SimplyMachines.EXTRUDER)));
    }

    public static class GenericJEIIntegration<C extends ISimplyInventory, R extends BasicMachineRecipe<C>>
    {
        private final IRecipeType<R> recipeType;
        private final Class<R> rClass;
        private final List<Supplier<ItemStack>> icons;

        public GenericJEIIntegration(IRecipeType<R> recipeType, Class<R> rClass, Supplier<ItemStack> icons) {
            this.recipeType = recipeType;
            this.rClass = rClass;
            this.icons = Collections.singletonList(icons);
        }


        public IRecipeType<R> getType() {
            return recipeType;
        }

        public List<Supplier<ItemStack>> getIcons() {
            return icons;
        }

        public Class<R> getRClass() {
            return rClass;
        }
    }

}
