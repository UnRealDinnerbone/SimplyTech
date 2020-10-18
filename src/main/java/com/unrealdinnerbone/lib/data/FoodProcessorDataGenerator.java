package com.unrealdinnerbone.lib.data;

import com.unrealdinnerbone.simplytech.registries.SimplyItems;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class FoodProcessorDataGenerator extends BasicMachineDataGenerator {

    public FoodProcessorDataGenerator(DataGenerator generatorIn) {
        super("food_processor", generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ForgeRegistries.ITEMS.getValues().stream().filter(Item::isFood).forEach(item -> new Builder(Ingredient.fromItems(item), SimplyItems.COMPOST.get(), item.getFood().getHealing(), Math.max((int) (item.getFood().getSaturation() * 10) * 500, 500)).build(consumer, item.getRegistryName().getNamespace(), item.getRegistryName().getPath()));
    }

    public static class Builder extends BasicMachineDataGenerator.Builder {

        public Builder(Ingredient input, IItemProvider output, int outputCount, int energyUsage) {
            super(input, output, outputCount, energyUsage);
        }

        @Override
        public String getName() {
            return "food_processor";
        }

        @Override
        public BasicMachineDataGenerator.Result createResult(ResourceLocation name, Ingredient input, Item output, int outputCount, int energyUsage, Advancement.Builder builder, ResourceLocation advancementName) {
            return new Result(name, input, output, outputCount, energyUsage, builder, advancementName);
        }

    }

    public static class Result extends BasicMachineDataGenerator.Result {

        public Result(ResourceLocation name, Ingredient input, Item output, int outputCount, int energyUsage, Advancement.Builder builder, ResourceLocation advancementName) {
            super(name, input, output, outputCount, energyUsage, builder, advancementName);
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return SimplyRecipes.FOOD_PROCESSOR.getSerializer();
        }

    }
}
