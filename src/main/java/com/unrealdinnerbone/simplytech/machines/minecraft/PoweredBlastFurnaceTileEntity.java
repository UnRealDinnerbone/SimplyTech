package com.unrealdinnerbone.simplytech.machines.minecraft;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.IRecipeType;

import java.util.Optional;

public class PoweredBlastFurnaceTileEntity extends BasicRecipeTileEntity<PoweredBlastFurnaceTileEntity, IInventory, BlastingRecipe> {

    public PoweredBlastFurnaceTileEntity() {
        super(SimplyMachines.POWERED_BLAST_FURNACE, IRecipeType.BLASTING, 2, SimplyTech.getInstance().getConfig().POWERED_BLAST_FURNACE);
    }

    @Override
    public int getEnergyCost(BlastingRecipe recipe) {
        return recipe.getCookTime();
    }

    @Override
    public Optional<BlastingRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(IRecipeType.BLASTING, this, world);
    }

}
