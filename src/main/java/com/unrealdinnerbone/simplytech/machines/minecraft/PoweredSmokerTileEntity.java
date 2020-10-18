package com.unrealdinnerbone.simplytech.machines.minecraft;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SmokingRecipe;

import java.util.Optional;

public class PoweredSmokerTileEntity extends BasicRecipeTileEntity<PoweredSmokerTileEntity, IInventory, SmokingRecipe> {

    public PoweredSmokerTileEntity() {
        super(SimplyMachines.POWERED_SMOKER, IRecipeType.SMOKING, 2, SimplyTech.getInstance().getConfig().POWERED_BLAST_FURNACE);
    }

    @Override
    public int getEnergyCost(SmokingRecipe recipe) {
        return recipe.getCookTime();
    }

    @Override
    public Optional<SmokingRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(IRecipeType.SMOKING, this, world);
    }

}
