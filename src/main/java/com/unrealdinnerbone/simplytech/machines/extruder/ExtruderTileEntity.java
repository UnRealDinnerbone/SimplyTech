package com.unrealdinnerbone.simplytech.machines.extruder;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;

import java.util.Optional;

public class ExtruderTileEntity extends BasicRecipeTileEntity<ExtruderTileEntity, ExtruderTileEntity, ExtruderRecipe> {

    public ExtruderTileEntity() {
        super(SimplyMachines.EXTRUDER, SimplyRecipes.EXTRUDER_TYPE.getRecipeType(), 2, SimplyTech.getInstance().getConfig().EXTRUDER);
    }

    @Override
    public int getEnergyCost(ExtruderRecipe extruderRecipe) {
        return extruderRecipe.getEnergy();
    }

    @Override
    public Optional<ExtruderRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(SimplyRecipes.EXTRUDER_TYPE.getRecipeType(), this, world);
    }
}
