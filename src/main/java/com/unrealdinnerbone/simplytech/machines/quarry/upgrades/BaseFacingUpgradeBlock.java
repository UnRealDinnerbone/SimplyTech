//package com.unrealdinnerbone.simplytech.machines.quarry.upgrades;
//
//import com.unrealdinnerbone.simplytech.SimplyTech;
//import com.unrealdinnerbone.lib.data.api.IBlockModelProvider;
//import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
//import com.unrealdinnerbone.lib.api.quarry.IUpgrade;
//import com.unrealdinnerbone.lib.data.BlockModelProvider;
//import com.unrealdinnerbone.lib.data.ItemModelProvider;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.item.BlockItemUseContext;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.state.DirectionProperty;
//import net.minecraft.state.StateContainer;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.util.Direction;
//import net.minecraft.util.Mirror;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.Rotation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraft.world.IBlockReader;
//
//import javax.annotation.Nullable;
//import java.util.List;
//
//public abstract class BaseFacingUpgradeBlock extends Block implements IBlockModelProvider, IItemModelProvider, IUpgrade {
//
//    public static final DirectionProperty PROPERTY_FACING = BlockStateProperties.FACING;
//
//    public BaseFacingUpgradeBlock(Properties properties) {
//        super(properties);
//        this.setDefaultState(this.stateContainer.getBaseState().with(PROPERTY_FACING, Direction.NORTH));
//
//    }
//
//    public BlockState rotate(BlockState state, Rotation rot) {
//        return state.with(PROPERTY_FACING, rot.rotate(state.get(PROPERTY_FACING)));
//    }
//
//    public BlockState mirror(BlockState state, Mirror mirrorIn) {
//        return state.rotate(mirrorIn.toRotation(state.get(PROPERTY_FACING)));
//    }
//
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(PROPERTY_FACING);
//    }
//
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return this.getDefaultState().with(PROPERTY_FACING, context.getNearestLookingDirection().getOpposite());
//    }
//
//
//    protected BlockPos getFacingBlockPos(BlockPos blockPos, BlockState blockState) {
//        return blockPos.offset(blockState.get(PROPERTY_FACING));
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, @Nullable IBlockReader blockReader, List<ITextComponent> textComponents, ITooltipFlag flag) {
//        textComponents.add(new TranslationTextComponent("tooltip.upgrade.cost", getConfig().getCostPerOperation()));
//        super.addInformation(stack, blockReader, textComponents, flag);
//    }
//
//    @Override
//    public void registerItemModel(Item item, ItemModelProvider provider) {
//        provider.orientableVertical(item.getRegistryName().toString(), new ResourceLocation(SimplyTech.MOD_ID, "block/upgrades/" + getUpgradeName() + "/side"), new ResourceLocation(SimplyTech.MOD_ID, "block/upgrades/" + getUpgradeName() + "/" + getType().name().toLowerCase()));
//
//    }
//
//    @Override
//    public void registerBlockModel(Block block, BlockModelProvider provider) {
//        provider.directionalBlock(block, provider.models().orientableVertical(block.getRegistryName().toString(), new ResourceLocation(SimplyTech.MOD_ID, "block/upgrades/" + getUpgradeName() + "/side"), new ResourceLocation(SimplyTech.MOD_ID, "block/upgrades/" + getUpgradeName() + "/" + getType().name().toLowerCase())));
//
//    }
//
//    public abstract String getUpgradeName();
//    public abstract TransferType getType();
//
//    public enum TransferType {
//        IN,
//        OUT
//    }
//}
