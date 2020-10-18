package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Optional;

public class RecipeUtils {

    public static <C extends ISimplyInventory, R extends IRecipe<C>> List<R> getRecipes(IRecipeType<R> riRecipeType) {
        return getRecipeManger().getRecipesForType(riRecipeType);
    }

    public static RecipeManager getRecipeManger() {
        return DistExecutor.runForDist(() -> () -> Minecraft.getInstance().world.getRecipeManager(), () -> () -> ServerLifecycleHooks.getCurrentServer().getRecipeManager());
    }
}
