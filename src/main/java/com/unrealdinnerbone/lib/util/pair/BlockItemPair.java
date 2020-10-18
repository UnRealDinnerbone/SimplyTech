package com.unrealdinnerbone.lib.util.pair;

import com.mojang.datafixers.util.Pair;
import com.unrealdinnerbone.lib.item.SimplyBlockItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fml.RegistryObject;

public class BlockItemPair<B extends Block> extends Pair<RegistryObject<B>, RegistryObject<SimplyBlockItem>> implements IItemProvider {

    public BlockItemPair(RegistryObject<B> b, RegistryObject<SimplyBlockItem> i) {
        super(b, i);
    }

    public static <B extends Block> BlockItemPair<B> of(RegistryObject<B> b, RegistryObject<SimplyBlockItem> i) {
        return new BlockItemPair<>(b, i);
    }

    public Block getBlock() {
        return super.getFirst().get();
    }

    public Item getItem() {
        return super.getSecond().get();
    }


    @Override
    public Item asItem() {
        return getItem();
    }
}
