package com.unrealdinnerbone.lib.data.api;

import com.unrealdinnerbone.lib.data.BlockModelProvider;
import net.minecraft.block.Block;

public interface IBlockModelProvider extends IItemModelProvider {
    void registerBlockModel(Block block, BlockModelProvider provider);
}
