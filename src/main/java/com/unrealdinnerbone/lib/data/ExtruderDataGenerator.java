package com.unrealdinnerbone.lib.data;

import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ExtruderDataGenerator extends BasicMachineDataGenerator {

    public ExtruderDataGenerator(DataGenerator generatorIn) {
        super("extruder", generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        new Builder(Ingredient.fromItems(Items.IRON_ORE), Items.IRON_INGOT, 2, 1000).build(consumer, SimplyTech.MOD_ID, "iron_ore_to_iron_ingot");
        new Builder(Ingredient.fromItems(Items.GOLD_ORE), Items.GOLD_INGOT, 2, 1000).build(consumer, SimplyTech.MOD_ID, "gold_ore_to_gold_ingot");
        new Builder(Ingredient.fromItems(Items.COAL_ORE), Items.COAL, 2, 1000).build(consumer, SimplyTech.MOD_ID, "coal_ore_to_coal");
        new Builder(Ingredient.fromItems(Items.NETHER_GOLD_ORE), Items.GOLD_INGOT, 1, 1000).build(consumer, SimplyTech.MOD_ID, "nether_gold_ore_to_gold_ingot");
        new Builder(Ingredient.fromItems(Items.LAPIS_ORE), Items.LAPIS_LAZULI, 13, 1000).build(consumer, SimplyTech.MOD_ID, "lapis_ore_to_lapis");
        new Builder(Ingredient.fromItems(Items.REDSTONE_ORE), Items.REDSTONE, 6, 1000).build(consumer, SimplyTech.MOD_ID, "redstone_ore_to_redstone_dust");
        new Builder(Ingredient.fromItems(Items.EMERALD_ORE), Items.EMERALD, 2, 1000).build(consumer, SimplyTech.MOD_ID, "emerald_ore_to_emerald");
        new Builder(Ingredient.fromItems(Items.DIAMOND_ORE), Items.DIAMOND, 2, 1000).build(consumer, SimplyTech.MOD_ID, "diamond_ore_to_diamond");
        new Builder(Ingredient.fromItems(Items.NETHER_QUARTZ_ORE), Items.QUARTZ, 2, 1000).build(consumer, SimplyTech.MOD_ID, "nether_quarts_ore_to_quarts");
        new Builder(Ingredient.fromItems(Items.BONE), Items.BONE_MEAL, 6, 1000).build(consumer, SimplyTech.MOD_ID, "bone_to_bonemeal");
    }

    public static class Builder extends BasicMachineDataGenerator.Builder {

        public Builder(Ingredient input, IItemProvider output, int outputCount, int energyUsage) {
            super(input, output, outputCount, energyUsage);
        }

        @Override
        public String getName() {
            return "extruder";
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
            return SimplyRecipes.EXTRUDER_TYPE.getSerializer();
        }

    }
}
