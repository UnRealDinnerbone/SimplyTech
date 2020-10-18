package com.unrealdinnerbone.lib.builders;

import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.block.BaseTileEntityBlock;
import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
import com.unrealdinnerbone.lib.data.BlockModelProvider;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.client.model.generators.*;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class BlockBuilder {

    public static Supplier<BaseTileEntityBlock> createTileEntityBlock(Supplier<TileEntity> tileEntitySupplier) {
        return () -> new BaseTileEntityBlock(AbstractBlock.Properties.create(Material.ROCK)) {
            @Nullable
            @Override
            public TileEntity createTileEntity(BlockState state, IBlockReader world) {
                return tileEntitySupplier.get();
            }
        };
    }

    public static <T extends TileEntity> FacingTileBlockBuilder<T> createFacingTileBlock(String name, Supplier<T> tileEntitySupplier) {
        return new FacingTileBlockBuilder<T>(name, tileEntitySupplier);
    }

    public static class FacingTileBlockBuilder<T extends TileEntity> {
        private final Supplier<T> tileEntitySupplier;
        private final String textureName;

        public FacingTileBlockBuilder(String textureName, Supplier<T> tileEntitySupplier) {
            this.textureName = textureName;
            this.tileEntitySupplier = tileEntitySupplier;
        }

        public BaseTileEntityBlock build() {
            return new FacingTileDirectionBlock(AbstractBlock.Properties.create(Material.ROCK)) {
                @Override
                public String getTextureName() {
                    return textureName;
                }

                @Nullable
                @Override
                public TileEntity createTileEntity(BlockState state, IBlockReader world) {
                    return tileEntitySupplier.get();
                }
            };
        }
    }
}
