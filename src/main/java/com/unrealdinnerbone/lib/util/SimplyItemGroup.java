package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.simplytech.registries.SimplyItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.LazyValue;

public class SimplyItemGroup extends ItemGroup {

    public static final SimplyItemGroup ITEM_GROUP = new SimplyItemGroup();
    private static final LazyValue<ItemStack> itemStackLazyValue = new LazyValue<>(() -> new ItemStack(SimplyItems.WRENCH.get()));

    public SimplyItemGroup() {
        super(SimplyReference.MOD_ID);
    }

    @Override
    public ItemStack createIcon() {
        return itemStackLazyValue.getValue();
    }
}
