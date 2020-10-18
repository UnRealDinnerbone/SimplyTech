//package com.unrealdinnerbone.simplytech.machines.quarry.upgrades.block;
//
//import com.unrealdinnerbone.simplytech.*;
//import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
//import com.unrealdinnerbone.lib.api.quarry.IBlockSuppler;
//import com.unrealdinnerbone.simplytech.config.ServerConfig;
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.BaseFacingUpgradeBlock;
//import com.unrealdinnerbone.simplytech.registries.SimplyBlocks;
//import com.unrealdinnerbone.simplytech.registries.SimplyItems;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.block.material.Material;
//import net.minecraft.data.*;
//import net.minecraft.item.Item;
//import net.minecraft.item.Items;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.common.Tags;
//
//import java.util.function.Consumer;
//
//public class BlockInputUpgradeBlock extends BaseFacingUpgradeBlock implements IBlockSuppler, IRecipeProvider {
//
//    public BlockInputUpgradeBlock() {
//        super(Properties.create(Material.ROCK));
//    }
//
//    @Override
//    public boolean supplyStone(World world, Direction direction, BlockPos blockPos) {
//        BlockPos blockPos1 = getFacingBlockPos(blockPos, world.getBlockState(blockPos));
//        BlockState blockState = world.getBlockState(getFacingBlockPos(blockPos, world.getBlockState(blockPos)));
//        if(blockState.getBlock().isIn(Tags.Blocks.STONE)) {
//            world.setBlockState(blockPos1, Blocks.AIR.getDefaultState());
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean hasStone(World world, Direction direction, BlockPos blockPos) {
//        return world.getBlockState(getFacingBlockPos(blockPos, world.getBlockState(blockPos))).getBlock().isIn(Tags.Blocks.STONE);
//    }
//
//    @Override
//    public TransferType getType() {
//        return TransferType.IN;
//    }
//
//    @Override
//    public String getUpgradeName() {
//        return "block";
//    }
//
//    @Override
//    public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {
//        ShapedRecipeBuilder.shapedRecipe(item)
//                .patternLine("WTW")
//                .patternLine("WCW")
//                .patternLine("WHW")
//                .key('W', Tags.Items.STONE)
//                .key('H', Items.HOPPER)
//                .key('C', SimplyItems.QUARRY_CORE.get())
//                .key('T', Tags.Items.CHESTS)
//                .addCriterion("has_stone", RecipeProvider.hasItem(Tags.Items.STONE))
//                .addCriterion("has_hopper", RecipeProvider.hasItem(Items.HOPPER))
//                .addCriterion("has_quarry_core", RecipeProvider.hasItem(SimplyItems.QUARRY_CORE.get()))
//                .addCriterion("has_chest", RecipeProvider.hasItem(Tags.Items.CHESTS))
//                .build(iFinishedRecipeConsumer);
//        ShapelessRecipeBuilder.shapelessRecipe(item)
//                .addIngredient(SimplyBlocks.BLOCK_OUTPUT_UPGRADE)
//                .addCriterion("has_input", RecipeProvider.hasItem(SimplyBlocks.BLOCK_OUTPUT_UPGRADE))
//                .build(iFinishedRecipeConsumer, item.getRegistryName().toString() + "_convert");
//    }
//
//    @Override
//    public ServerConfig.UpgradeConfig getConfig() {
//        return SimplyTech.getInstance().getConfig().BLOCK_INPUT_UPGRADE;
//    }
//}
