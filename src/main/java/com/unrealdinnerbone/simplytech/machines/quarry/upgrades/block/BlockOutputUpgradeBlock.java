//package com.unrealdinnerbone.simplytech.machines.quarry.upgrades.block;
//
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.BaseFacingUpgradeBlock;
//import com.unrealdinnerbone.simplytech.registries.SimplyBlocks;
//import com.unrealdinnerbone.simplytech.config.ServerConfig;
//import com.unrealdinnerbone.simplytech.registries.SimplyItems;
//import com.unrealdinnerbone.simplytech.SimplyTech;
//import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
//import com.unrealdinnerbone.lib.api.quarry.IOreGiver;
//import net.minecraft.block.*;
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
//public class BlockOutputUpgradeBlock extends BaseFacingUpgradeBlock implements IOreGiver, IRecipeProvider {
//
//    public BlockOutputUpgradeBlock() {
//        super(Properties.create(Material.ROCK));
//    }
//
//    @Override
//    public boolean giveOre(BlockState oreBlockState, World world, Direction direction, BlockPos blockPos) {
//        BlockPos facingBlock = getFacingBlockPos(blockPos, world.getBlockState(blockPos));
//        if(world.getBlockState(facingBlock).isAir(world, facingBlock)) {
//            world.setBlockState(facingBlock, oreBlockState);
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    @Override
//    public boolean hasAir(BlockState oreBlockState, World world, Direction direction, BlockPos blockPos) {
//        BlockPos facingBlock = getFacingBlockPos(blockPos, world.getBlockState(blockPos));
//        return world.getBlockState(facingBlock).isAir(world, facingBlock);
//    }
//    @Override
//    public TransferType getType() {
//        return TransferType.OUT;
//    }
//
//    @Override
//    public ServerConfig.UpgradeConfig getConfig() {
//        return SimplyTech.getInstance().getConfig().BLOCK_OUTPUT_UPGRADE;
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
//                .patternLine("WHW")
//                .patternLine("WCW")
//                .patternLine("WTW")
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
//                .addIngredient(SimplyBlocks.BLOCK_INPUT_UPGRADE)
//                .addCriterion("has_input", RecipeProvider.hasItem(SimplyBlocks.BLOCK_INPUT_UPGRADE))
//                .build(iFinishedRecipeConsumer, item.getRegistryName().toString() + "_convert");
//    }
//
//}
