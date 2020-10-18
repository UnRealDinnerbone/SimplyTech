package com.unrealdinnerbone.lib.data.api;

import com.unrealdinnerbone.lib.data.ItemModelProvider;
import net.minecraft.item.Item;

public interface IItemModelProvider {
    void registerItemModel(Item item, ItemModelProvider provider);
}
