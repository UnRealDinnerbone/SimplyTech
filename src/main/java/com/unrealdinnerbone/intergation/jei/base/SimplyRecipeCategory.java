package com.unrealdinnerbone.intergation.jei.base;

import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.recipe.SimplyRecipe;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public abstract class SimplyRecipeCategory<I extends ISimplyInventory, R extends SimplyRecipe<I>> implements IRecipeCategory<R> {

    private final ResourceLocation id;
    private final LazyValue<IDrawable> background;
    private final LazyValue<IDrawable> icon;
    protected static final ResourceLocation PATTERN = new ResourceLocation(SimplyTech.MOD_ID, "textures/gui/jei/pattern.png");
    private final String name;
    protected final int x;
    protected final int y;

    public SimplyRecipeCategory(ResourceLocation id, Supplier<IDrawable> background, Supplier<IDrawable> icon) {
        this.name = I18n.format(id.toString().replace(":", ".jei."));
        this.id = id;
        this.background = new LazyValue<>(background);
        this.icon = new LazyValue<>(icon);
        x = background.get().getWidth() / 2;
        y = background.get().getHeight() / 2;
    }

    @Override
    public final ResourceLocation getUid() {
        return id;
    }

    @Override
    public final String getTitle() {
        return name;
    }

    @Override
    public abstract Class<? extends R> getRecipeClass();

    @Override
    public IDrawable getBackground() {
        return background.getValue();
    }

    @Override
    public IDrawable getIcon() {
        return icon.getValue();
    }

    @Override
    public abstract void setIngredients(R r, IIngredients iIngredients);

    @Override
    public abstract void setRecipe(IRecipeLayout iRecipeLayout, R r, IIngredients iIngredients);


    public static ResourceLocation createID(String name) {
        return new ResourceLocation(SimplyTech.MOD_ID, name.toLowerCase());
    }

}
