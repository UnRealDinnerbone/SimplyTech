//package com.unrealdinnerbone.simplytech.machines.quarry;
//
//import com.unrealdinnerbone.lib.api.UpgradeType;
//import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
//import com.unrealdinnerbone.lib.util.WorldUtil;
//import com.unrealdinnerbone.simplytech.SimplyTech;
//import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
//import com.unrealdinnerbone.lib.data.api.IBlockModelProvider;
//import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
//import com.unrealdinnerbone.lib.data.BlockModelProvider;
//import com.unrealdinnerbone.lib.data.ItemModelProvider;
//import net.minecraft.block.AbstractBlock;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.material.Material;
//import net.minecraft.data.IFinishedRecipe;
//import net.minecraft.data.RecipeProvider;
//import net.minecraft.data.ShapedRecipeBuilder;
//import net.minecraft.item.Item;
//import net.minecraft.item.Items;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.IBlockReader;
//import net.minecraft.world.IWorldReader;
//import net.minecraft.world.World;
//import net.minecraftforge.common.Tags;
//
//import javax.annotation.Nullable;
//import java.util.function.Consumer;
//
//@SuppressWarnings("deprecation")
//public class QuarryBlock extends FacingTileDirectionBlock implements IRecipeProvider, IBlockModelProvider, IItemModelProvider {
//
//    public QuarryBlock() {
//        super(AbstractBlock.Properties.create(Material.ROCK));
//    }
//
//    @Override
//    public void neighborChanged(BlockState blockState, World world, BlockPos pos, Block block, BlockPos blockPos2, boolean isMoving) {
//        WorldUtil.getTileEntity(QuarryTileEntity.class, world, pos).ifPresent(QuarryTileEntity::updateUpgradesCache);
//        super.neighborChanged(blockState, world, pos, block, blockPos2, isMoving);
//    }
//
//    @Override
//    public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
//        WorldUtil.getTileEntity(QuarryTileEntity.class, world, pos).ifPresent(QuarryTileEntity::updateUpgradesCache);
//        super.onNeighborChange(state, world, pos, neighbor);
//    }
//
//    @Override
//    public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {
//        ShapedRecipeBuilder.shapedRecipe(item)
//                .patternLine("EPE")
//                .patternLine("ESE")
//                .patternLine("OTO")
//                .key('E', Tags.Items.END_STONES)
//                .key('P', Items.DIAMOND_PICKAXE)
//                .key('S', Tags.Items.NETHER_STARS)
//                .key('O', Tags.Items.OBSIDIAN)
//                .key('T', Items.ENDER_EYE)
//                .addCriterion("has_endstone", RecipeProvider.hasItem(Tags.Items.END_STONES))
//                .addCriterion("has_diamond_pickaxe", RecipeProvider.hasItem(Items.DIAMOND_PICKAXE))
//                .addCriterion("has_nether_star", RecipeProvider.hasItem(Tags.Items.NETHER_STARS))
//                .addCriterion("has_obsidain", RecipeProvider.hasItem(Tags.Items.OBSIDIAN))
//                .addCriterion("has_eye", RecipeProvider.hasItem(Items.ENDER_EYE))
//                .setGroup(SimplyTech.MOD_ID)
//                .build(iFinishedRecipeConsumer);
//    }
//
//    @Override
//    public void registerBlockModel(Block block, BlockModelProvider provider) {
//        provider.horizontalBlock(block, new ResourceLocation(SimplyTech.MOD_ID, "block/quarry/side"), new ResourceLocation(SimplyTech.MOD_ID, "block/quarry/front"), new ResourceLocation("minecraft", "block/obsidian"));
//
//    }
//
//    @Override
//    public UpgradeType getUpdateType() {
//        return UpgradeType.NETHERITE;
//    }
//
//    @Override
//    public void registerItemModel(Item item, ItemModelProvider provider) {
//        provider.orientable(item.getRegistryName().toString(), new ResourceLocation(SimplyTech.MOD_ID, "block/quarry/side"), new ResourceLocation(SimplyTech.MOD_ID, "block/quarry/front"), new ResourceLocation("minecraft", "block/obsidian"));
//
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return new QuarryTileEntity();
//    }
//
//    @Override
//    public String getTextureName() {
//        return "quarry";
//    }
//}
