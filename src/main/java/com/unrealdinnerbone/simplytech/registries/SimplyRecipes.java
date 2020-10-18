package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderRecipe;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderTileEntity;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.lib.util.pair.RecipePair;
import com.unrealdinnerbone.lib.util.pair.SimplyRecipeType;
import com.unrealdinnerbone.simplytech.machines.foodprocessor.FoodProcessorRecipe;
import com.unrealdinnerbone.simplytech.machines.foodprocessor.FoodProcessorTileEntity;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SimplyRecipes implements ILoading
{
    private static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.RECIPE_SERIALIZERS);

    public static final RecipePair<ExtruderTileEntity, ExtruderRecipe, ExtruderRecipe.Serializer> EXTRUDER_TYPE = register("extruder", ExtruderRecipe.Serializer::new);
    public static final RecipePair<FoodProcessorTileEntity, FoodProcessorRecipe, FoodProcessorRecipe.Serializer> FOOD_PROCESSOR = register("food_processor", FoodProcessorRecipe.Serializer::new);

    public static <I extends ISimplyInventory, R extends SimplyRecipe<I>, S extends IRecipeSerializer<R>> RecipePair<I, R, S> register(String key, Supplier<S> serializerSupplier) {
        SimplyRecipeType<I, R> sRegistryObject = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(SimplyTech.MOD_ID, key), new SimplyRecipeType<>(key));
        RegistryObject<S> s = RECIPE_SERIALIZER_REGISTER.register(key, serializerSupplier);
        return new RecipePair<>(sRegistryObject, s);
    }


}
