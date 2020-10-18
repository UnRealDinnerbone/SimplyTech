package com.unrealdinnerbone.lib.data;

import com.google.gson.JsonObject;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyItems;
import com.unrealdinnerbone.simplytech.registries.SimplyRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class BasicMachineDataGenerator extends RecipeProvider {

    private final String name;

    public BasicMachineDataGenerator(String name, DataGenerator generatorIn) {
        super(generatorIn);
        this.name = name;
    }

    @Override
    protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

    @Override
    public String getName() {
        return name;
    }

    public abstract static class Builder {

        private final Ingredient input;
        private final IItemProvider output;
        private final int outputCount;
        private final int energyUsage;
        private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();


        public Builder(Ingredient input, IItemProvider output, int outputCount, int energyUsage) {
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
            this.energyUsage = energyUsage;
            for (ItemStack matchingStack : input.getMatchingStacks()) {
                addCriterion("has_input", hasItem(matchingStack.getItem()));
            }
        }

        public Builder addCriterion(String name, ICriterionInstance criterionIn) {
            this.advancementBuilder.withCriterion(name, criterionIn);
            return this;
        }

        public void build(Consumer<IFinishedRecipe> consumerIn, String modID, String name) {
            ResourceLocation id = new ResourceLocation(modID, getName() + "/" + name);
            this.validate(id);
            this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
            consumerIn.accept(createResult(id, this.input, this.output.asItem(), this.outputCount, this.energyUsage, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + getName() + "/" + id.getPath())));
        }

        public abstract String getName();

        public abstract Result createResult(ResourceLocation name, Ingredient input, Item output, int outputCount, int energyUsage, Advancement.Builder builder, ResourceLocation advancementName);

        private void validate(ResourceLocation id) {
            if (this.advancementBuilder.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + id);
            }
        }
    }

    public abstract static class Result implements IFinishedRecipe {

        private final Ingredient input;
        private final Item output;
        private final int outputCount;
        private final int energyUsage;
        private final Advancement.Builder builder;
        private final ResourceLocation advancementId;
        private final ResourceLocation name;

        public Result(ResourceLocation name, Ingredient input, Item output, int outputCount, int energyUsage, Advancement.Builder builder, ResourceLocation advancementName) {
            this.name = name;
            this.input = input;
            this.output = output;
            this.outputCount = outputCount;
            this.energyUsage = energyUsage;
            this.builder = builder;
            this.advancementId = advancementName;
        }


        @Override
        public void serialize(JsonObject json) {
            json.add("input", input.serialize());
            json.addProperty("output", output.getRegistryName().toString());
            json.addProperty("count", outputCount);
            json.addProperty("energy", energyUsage);
        }

        @Override
        public ResourceLocation getID() {
            return name;
        }

        @Override
        public abstract IRecipeSerializer<?> getSerializer();

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return builder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return advancementId;
        }
    }
}
