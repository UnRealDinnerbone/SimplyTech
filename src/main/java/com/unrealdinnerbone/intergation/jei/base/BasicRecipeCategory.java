package com.unrealdinnerbone.intergation.jei.base;

import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import com.unrealdinnerbone.lib.recipe.base.BasicMachineRecipe;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderRecipe;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderTileEntity;
import com.unrealdinnerbone.simplytech.registries.SimplyBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BasicRecipeCategory<I extends ISimplyInventory, R extends BasicMachineRecipe<I>> extends SimplyRecipeCategory<I, R> {

    private final Class<? extends R> aClass;

    public BasicRecipeCategory(String name, Supplier<ItemStack> itemStackSupplier, Class<? extends R> clazz, IGuiHelper guiHelper) {
        super(createID(name), () -> guiHelper.createDrawable(PATTERN, 0, 0, 92, 26),
                () -> guiHelper.createDrawableIngredient(itemStackSupplier.get()));
        this.aClass = clazz;
    }

    @Override
    public void setIngredients(R extruderRecipe, IIngredients ingredients) {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(extruderRecipe.getInputIngredient());
        ingredients.setInputIngredients(ingredientList);
        ingredients.setOutput(VanillaTypes.ITEM, extruderRecipe.getRecipeOutput());
    }

    @Override
    public Class<? extends R> getRecipeClass() {
        return aClass;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, R extruderRecipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(1, true, x - 42, 4);
        guiItemStacks.init(0, false, x + 24, 4);
        guiItemStacks.set(ingredients);
    }

    @Override
    public List<ITextComponent> getTooltipStrings(R recipe, double mouseX, double mouseY) {
        List<ITextComponent> list = new ArrayList<>();
        if (mouseY >= 4 && mouseY <= 18 && mouseX >= 35 && mouseX <= 56) {
            list.add(new TranslationTextComponent("simplytech.energy.cost", recipe.getEnergy()));
        }
        return list;
    }
}
