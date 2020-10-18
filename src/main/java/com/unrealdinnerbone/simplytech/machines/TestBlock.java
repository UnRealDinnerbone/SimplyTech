package com.unrealdinnerbone.simplytech.machines;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.data.BlockModelProvider;
import com.unrealdinnerbone.lib.data.api.IBlockModelProvider;
import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class TestBlock extends Block implements IBlockModelProvider, IItemModelProvider {

    public TestBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK));
    }

    @Override
    public void registerBlockModel(Block block, BlockModelProvider provider) {
        provider.simpleBlock(block);

    }

    @Override
    public void registerItemModel(Item item, ItemModelProvider provider) {
        provider.itemGenerated(item, new ResourceLocation(SimplyReference.MOD_ID, "block/" + this.getRegistryName().getPath().toLowerCase()));

    }
}
