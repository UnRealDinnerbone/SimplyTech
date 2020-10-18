package com.unrealdinnerbone.lib.block;

import com.unrealdinnerbone.lib.api.IRenderType;
import com.unrealdinnerbone.lib.api.ITextureName;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.data.BlockModelProvider;
import com.unrealdinnerbone.lib.data.api.IBlockModelProvider;
import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import com.unrealdinnerbone.lib.util.InventoryHelper;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

import java.util.Arrays;

@SuppressWarnings("deprecation")
public abstract class FacingTileDirectionBlock extends BaseTileEntityBlock implements IBlockModelProvider, IItemModelProvider, IRenderType, ITextureName {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public FacingTileDirectionBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!isMoving) {
            if (!state.isIn(newState.getBlock())) {
                InventoryHelper.dropInventoryContents(worldIn, pos);
                super.onReplaced(state, worldIn, pos, newState, isMoving);
            }
        }
    }

    @Override
    public void registerBlockModel(Block block, BlockModelProvider provider) {
        String textureName = block instanceof ITextureName ? ((ITextureName) block).getTextureName() : block.getRegistryName().getPath();
        MultiPartBlockStateBuilder builderBuilder = provider.getMultipartBuilder(block)
                .part()
                .modelFile(provider.models().cubeAll("block/machine/base", provider.modLoc("block/machine/base/base")))
                .addModel()
                .end();
        ModelFile file = provider.getDirectionOnlyTextureTwo(provider.mcLoc("block/orientable"), Direction.NORTH, "block/machine/" + textureName);
        builderBuilder.part().modelFile(file).addModel().condition(FacingTileDirectionBlock.FACING, Direction.NORTH);
        builderBuilder.part().modelFile(file).rotationY(180).addModel().condition(FacingTileDirectionBlock.FACING, Direction.SOUTH);
        builderBuilder.part().modelFile(file).rotationY(90).addModel().condition(FacingTileDirectionBlock.FACING, Direction.EAST);
        builderBuilder.part().modelFile(file).rotationY(270).addModel().condition(FacingTileDirectionBlock.FACING, Direction.WEST);
    }

    @Override
    public void registerItemModel(Item item, ItemModelProvider provider) {
        provider.getBuilder("item/" + item.getRegistryName().getPath())
                .parent(provider.getExistingFile(provider.modLoc("block/machine/" + getTextureName())))
                .texture("base", provider.modLoc("block/machine/base/base"))
                .texture("machine", provider.modLoc("block/machine/" + getTextureName()))
                .element()
                .textureAll("#base")
                .end()
                .element()
                .face(Direction.NORTH)
                .texture("#machine")
                .end()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .end();
    }

}
