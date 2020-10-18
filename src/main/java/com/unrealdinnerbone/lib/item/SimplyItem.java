package com.unrealdinnerbone.lib.item;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import com.unrealdinnerbone.lib.util.SimplyItemGroup;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class SimplyItem extends Item implements IItemModelProvider, IRecipeProvider {

    public SimplyItem() {
        this(new Properties());
    }

    public SimplyItem(Item.Properties properties) {
        super(properties.group(SimplyItemGroup.ITEM_GROUP));
    }

    @Override
    public void registerItemModel(Item item, ItemModelProvider provider) {
        provider.itemGenerated(item, new ResourceLocation(SimplyReference.MOD_ID, "item/" + this.getRegistryName().getPath().toLowerCase()));
    }

    @Override
    public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {

    }
}
