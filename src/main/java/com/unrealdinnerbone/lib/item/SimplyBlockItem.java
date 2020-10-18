package com.unrealdinnerbone.lib.item;

import com.unrealdinnerbone.lib.util.SimplyItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class SimplyBlockItem extends BlockItem {

    public SimplyBlockItem(RegistryObject<? extends Block> blockIn) {
        super(blockIn.get(), new Item.Properties().group(SimplyItemGroup.ITEM_GROUP));
    }
}
