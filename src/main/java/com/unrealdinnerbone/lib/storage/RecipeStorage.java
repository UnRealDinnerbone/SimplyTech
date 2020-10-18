package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.api.IRenderType;
import com.unrealdinnerbone.lib.api.SimplyReference;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;
import java.util.function.Supplier;

public class RecipeStorage<C extends IInventory, R extends IRecipe<C>> implements INBTStorage {

    private static final ResourceLocation EMPTY = new ResourceLocation(SimplyReference.MOD_ID, "empty");
    private final ResourceLocation id;
    private final Supplier<World> worldSupplier;
    private Optional<R> recipeOptional;

    public RecipeStorage(IRecipeType<R> recipeType, Supplier<World> worldSupplier) {
        this.id = Registry.RECIPE_TYPE.getKey(recipeType);
        this.worldSupplier = worldSupplier;
        this.recipeOptional = Optional.empty();
    }

    @Override
    public String getName() {
        return id.toString();
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("id", recipeOptional.map(IRecipe::getId).orElse(EMPTY).toString());
        return compoundNBT;
    }

    public void setRecipeOptional(Optional<R> recipeOptional) {
        this.recipeOptional = recipeOptional;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        World world = worldSupplier.get();
        if(world != null) {
            recipeOptional = (Optional<R>) worldSupplier.get().getRecipeManager().getRecipe(new ResourceLocation(nbt.getString("id")));
        }else {
            recipeOptional = Optional.empty();
        }
    }

    public Optional<R> getRecipe() {
        return recipeOptional;
    }
}
