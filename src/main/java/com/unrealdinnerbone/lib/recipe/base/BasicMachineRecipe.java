package com.unrealdinnerbone.lib.recipe.base;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import com.unrealdinnerbone.lib.recipe.SimplyRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.Optional;


public abstract class BasicMachineRecipe<I extends ISimplyInventory> extends SimplyRecipe<I> {

    private final Ingredient inputIngredient;
    private final int energy;

    public BasicMachineRecipe(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy) {
        super(resourceLocation, output);
        this.inputIngredient = inputIngredient;
        this.energy = energy;
    }

    public Ingredient getInputIngredient() {
        return inputIngredient;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public boolean matches(I inv, World worldIn) {
        return inv.matches(worldIn, inputIngredient);
    }

    public static abstract class Serializer<I extends ISimplyInventory, R extends BasicMachineRecipe<I>> extends SimplyRecipeSerializer<I, R> {

        @Override
        public R read(ResourceLocation recipeId, JsonObject json) {
            JsonElement inputElement = (JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input"));
            ResourceLocation resultLocation = new ResourceLocation(JSONUtils.getString(json, "output"));
            int count = JSONUtils.getInt(json, "count");
            ItemStack resultStack = new ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resultLocation))
                    .orElseThrow(() -> new IllegalArgumentException("Item " + resultLocation + " does not exist")), count);
            int energy = JSONUtils.getInt(json, "energy");
            return create(recipeId, Ingredient.deserialize(inputElement), resultStack, energy);
        }

        @Nullable
        @Override
        public R read(ResourceLocation recipeId, PacketBuffer buffer) {
            return create(recipeId, Ingredient.read(buffer), buffer.readItemStack(), buffer.readInt());
        }

        @Override
        public void write(PacketBuffer buffer, BasicMachineRecipe recipe) {
            recipe.inputIngredient.write(buffer);
            buffer.writeItemStack(recipe.getRecipeOutput());
            buffer.writeInt(recipe.getEnergy());
        }

        public abstract R create(ResourceLocation resourceLocation, Ingredient inputIngredient, ItemStack output, int energy);

    }
}
