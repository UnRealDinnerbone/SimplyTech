//package com.unrealdinnerbone.simplytech.machines.quarry.upgrades.chest;
//
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.BaseFacingUpgradeBlock;
//import com.unrealdinnerbone.simplytech.registries.SimplyBlocks;
//import com.unrealdinnerbone.simplytech.config.ServerConfig;
//import com.unrealdinnerbone.simplytech.registries.SimplyItems;
//import com.unrealdinnerbone.simplytech.SimplyTech;
//import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
//import com.unrealdinnerbone.lib.api.quarry.IOreGiver;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.material.Material;
//import net.minecraft.data.IFinishedRecipe;
//import net.minecraft.data.RecipeProvider;
//import net.minecraft.data.ShapedRecipeBuilder;
//import net.minecraft.data.ShapelessRecipeBuilder;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.tags.ItemTags;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//import net.minecraftforge.common.Tags;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.ItemHandlerHelper;
//
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.function.Consumer;
//
//public class ChestOutputUpgradeBlock extends BaseFacingUpgradeBlock implements IOreGiver, IRecipeProvider {
//
//    public ChestOutputUpgradeBlock() {
//        super(Properties.create(Material.ROCK));
//    }
//
//    @Override
//    public boolean giveOre(BlockState oreBlockState, World world, Direction direction, BlockPos blockPos) {
//        BlockPos inventoryBlockPos = getFacingBlockPos(blockPos, world.getBlockState(blockPos));
//        TileEntity tileEntity = world.getTileEntity(inventoryBlockPos);
//        AtomicReference<Boolean> atomicReference = new AtomicReference<>(false);
//        if(tileEntity != null) {
//            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).ifPresent(iItemHandler -> {
//                atomicReference.set(ItemHandlerHelper.insertItem(iItemHandler, new ItemStack(oreBlockState.getBlock()), false).isEmpty());
//            });
//        }
//        return atomicReference.get();
//    }
//
//    @Override
//    public boolean hasAir(BlockState oreBlockState, World world, Direction direction, BlockPos blockPos) {
//        BlockPos inventoryBlockPos = getFacingBlockPos(blockPos, world.getBlockState(blockPos));
//        TileEntity tileEntity = world.getTileEntity(inventoryBlockPos);
//        AtomicReference<Boolean> atomicReference = new AtomicReference<>(false);
//        if(tileEntity != null) {
//            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, direction).ifPresent(iItemHandler ->
//                    atomicReference.set(ItemHandlerHelper.insertItem(iItemHandler, new ItemStack(oreBlockState.getBlock()), true).isEmpty()));
//        }
//        return atomicReference.get();
//    }
//
//    @Override
//    public ServerConfig.UpgradeConfig getConfig() {
//        return SimplyTech.getInstance().getConfig().CHEST_OUTPUT_UPGRADE;
//    }
//
//    @Override
//    public TransferType getType() {
//        return TransferType.OUT;
//    }
//
//    @Override
//    public String getUpgradeName() {
//        return "chest";
//    }
//
//    @Override
//    public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {
//        ShapedRecipeBuilder.shapedRecipe(item)
//                .patternLine("WTW")
//                .patternLine("WCW")
//                .patternLine("WHW")
//                .key('W', ItemTags.PLANKS)
//                .key('H', Items.HOPPER)
//                .key('C', SimplyItems.QUARRY_CORE.get())
//                .key('T', Tags.Items.CHESTS)
//                .addCriterion("has_planks", RecipeProvider.hasItem(ItemTags.PLANKS))
//                .addCriterion("has_hopper", RecipeProvider.hasItem(Items.HOPPER))
//                .addCriterion("has_quarry_core", RecipeProvider.hasItem(SimplyItems.QUARRY_CORE.get()))
//                .addCriterion("has_chest", RecipeProvider.hasItem(Tags.Items.CHESTS))
//                .build(iFinishedRecipeConsumer);
//        ShapelessRecipeBuilder.shapelessRecipe(item)
//                .addIngredient(SimplyBlocks.CHEST_INPUT_UPGRADE)
//                .addCriterion("has_input", RecipeProvider.hasItem(SimplyBlocks.CHEST_INPUT_UPGRADE))
//                .build(iFinishedRecipeConsumer, item.getRegistryName().toString() + "_convert");
//    }
//}
