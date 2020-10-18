package com.unrealdinnerbone.simplytech.items;

import com.unrealdinnerbone.lib.api.IUpgradeInfo;
import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.block.FacingTileDirectionBlock;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import com.unrealdinnerbone.lib.item.SimplyItem;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.simplytech.registries.SimplyItems;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.command.impl.TagCommand;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public abstract class UpgradeItem extends SimplyItem
{
    private final UpgradeType upgradeType;

    public UpgradeItem(UpgradeType type) {
        this.upgradeType = type;
    }

    private UpgradeType getUpgradeType() {
        return upgradeType;
    }

    public abstract IUpgradeInfo getInfo();

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(getInfo().getSpeed() != 0) {
            String key = "tooltip.upgrade.speed." + (getInfo().getEnergyCost() > 0 ? "positive" : "negative");
            tooltip.add(new TranslationTextComponent(key, "" + getInfo().getSpeed()));
        }
        if(getInfo().getEnergyCost() != 0) {
            String key = "tooltip.upgrade.energy." + (getInfo().getEnergyCost() > 0 ? "positive" : "negative");
            tooltip.add(new TranslationTextComponent(key, getInfo().getEnergyCost() + ""));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void registerItemModel(Item item, ItemModelProvider provider) {
        System.out.println("FIX ME");
    }

    //    @Override
//    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
//        if(!context.getWorld().isRemote()) {
//            BlockState blockState = context.getWorld().getBlockState(context.getPos());
//            if(blockState.getBlock() instanceof FacingTileDirectionBlock) {
//                TileEntity tileEntity = context.getWorld().getTileEntity(context.getPos());
//                if(tileEntity instanceof BasicRecipeTileEntity) {
//                    BasicRecipeTileEntity<?, ?, ?> recipeTileEntity = (BasicRecipeTileEntity<?, ?, ?>) tileEntity;
//                    BlockState blockState1 = SimplyMachines.EXTRUDER.get(UpgradeType.NETHERITE).getBlockRegistryObject().get().getDefaultState().with(FacingTileDirectionBlock.FACING, blockState.get(FacingTileDirectionBlock.FACING));
//                    CompoundNBT tagCommand = recipeTileEntity.getTag();
//                    context.getWorld().setBlockState(context.getPos(), blockState1, Constants.BlockFlags.DEFAULT_AND_RERENDER | Constants.BlockFlags.IS_MOVING);
//                    TileEntity tileEntity1 = context.getWorld().getTileEntity(context.getPos());
//                    if(tileEntity1 instanceof BasicRecipeTileEntity) {
//                        ((BasicRecipeTileEntity<?, ?, ?>) tileEntity1).handleTag(tagCommand);
//                    }
//                }
//            }
//        }
//        return ActionResultType.PASS;
//    }

//    @Override
//    public void registerItemModel(Item item, ItemModelProvider provider) {
//        provider.itemGenerated(item, new ResourceLocation(SimplyReference.MOD_ID, "item/upgrade/" + this.getRegistryName().getPath().toLowerCase().replace("_upgrade", "")));
//    }

}
