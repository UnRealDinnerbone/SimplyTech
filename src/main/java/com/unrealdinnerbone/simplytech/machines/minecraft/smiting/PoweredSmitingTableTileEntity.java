package com.unrealdinnerbone.simplytech.machines.minecraft.smiting;

import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class PoweredSmitingTableTileEntity extends BasicRecipeTileEntity<PoweredSmitingTableTileEntity, IInventory, SmithingRecipe> {

    public PoweredSmitingTableTileEntity() {
        super(SimplyMachines.POWERED_SMITING_TABLE, IRecipeType.SMITHING, 3, SimplyTech.getInstance().getConfig().FOOD_PROCESSOR);
    }

    @Override
    public int getEnergyCost(SmithingRecipe smithingRecipe) {
        return 10000;
    }

    @Override
    public Optional<SmithingRecipe> getActiveRecipe() {
        return world.getRecipeManager().getRecipe(IRecipeType.SMITHING, this, world);
    }

    @Override
    public boolean craft(SmithingRecipe recipe) {
        ItemStack outputStack = recipe.getCraftingResult(this);
        getStackHandler().getStackInSlot(0).shrink(1);
        getStackHandler().getStackInSlot(1).shrink(1);
        getStackHandler().setStackInSlot(2, outputStack);
        return true;
    }

    @Override
    public boolean matches(World worldIn, Ingredient input) {
        throw new RuntimeException("HOW DID YOU GET HERE?");
    }

    @Override
    public Optional<Integer> getSlotForType(TransferType transferType) {
        switch (transferType) {
            case INPUT:
                return Optional.of(0);
            case INPUT_2:
                return Optional.of(1);
            case OUTPUT:
                return Optional.of(2);
            default:
                return Optional.empty();
        }
    }

    @Override
    public List<TransferType> getAllowedTransferTypes() {
        return TransferType.ALL;
    }
}
