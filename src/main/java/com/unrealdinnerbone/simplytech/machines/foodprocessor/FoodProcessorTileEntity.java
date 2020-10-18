package com.unrealdinnerbone.simplytech.machines.foodprocessor;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;

import java.util.Optional;

public class FoodProcessorTileEntity extends BasicRecipeTileEntity<FoodProcessorTileEntity, FoodProcessorTileEntity, FoodProcessorRecipe> {

    public FoodProcessorTileEntity() {
        super(SimplyMachines.FOOD_PROCESSOR, SimplyRecipes.FOOD_PROCESSOR.getRecipeType(), 2, SimplyTech.getInstance().getConfig().FOOD_PROCESSOR);
    }

    @Override
    public int getEnergyCost(FoodProcessorRecipe foodProcessorRecipe) {
        return foodProcessorRecipe.getEnergy();
    }

    @Override
    public Optional<FoodProcessorRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(SimplyRecipes.FOOD_PROCESSOR.getRecipeType(), this, world);
    }

}
