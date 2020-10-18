package com.unrealdinnerbone.simplytech.machines.block;

import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TankBlock extends Block {

    public TankBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            if (FluidUtil.interactWithFluidHandler(player, handIn, world, pos, hit.getFace())) {
                sendTankFillMessage(player, world, pos);
                return ActionResultType.SUCCESS;
            }else {
                sendTankFillMessage(player, world, pos);
                return ActionResultType.PASS;
            }
        }
        return ActionResultType.SUCCESS;
    }


    public void sendTankFillMessage(PlayerEntity entity, World world, BlockPos pos) {
        world.getTileEntity(pos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(iFluidHandler -> {
            int total = iFluidHandler.getTankCapacity(0);
            FluidStack stored = iFluidHandler.getFluidInTank(0);
            entity.sendStatusMessage(new TranslationTextComponent(SimplyTech.MOD_ID + ".block.tank.stored", stored.getAmount(), total).append(new TranslationTextComponent(stored.getTranslationKey())), true);
        });
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TankTileEntity(90000);
    }
}
