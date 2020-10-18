package com.unrealdinnerbone.simplytech.machines.minecraft;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;

import java.util.Optional;

public class PoweredFurnaceTileEntity extends BasicRecipeTileEntity<PoweredFurnaceTileEntity, IInventory, FurnaceRecipe> {

    public PoweredFurnaceTileEntity() {
        super(SimplyMachines.POWERED_FURNACE, IRecipeType.SMELTING, 2, SimplyTech.getInstance().getConfig().POWERED_BLAST_FURNACE);
    }

    @Override
    public int getEnergyCost(FurnaceRecipe recipe) {
        return recipe.getCookTime();
    }

    @Override
    public Optional<FurnaceRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, this, world);
    }

}
