package com.unrealdinnerbone.simplytech.machines.block;

import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.util.SimplyFluidTank;
import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TankTileEntity extends BaseTileEntity {

    private final LazyOptional<SimplyFluidTank> fluidHandlerLazyOptional;

    public TankTileEntity(int cap) {
        super(SimplyTileEntities.TEST_TANK);
        this.fluidHandlerLazyOptional = registerNBTStorage(LazyOptional.of(() -> new SimplyFluidTank(cap)));

    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if(cap ==  CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidHandlerLazyOptional.cast();
        }else {
            return super.getCapability(cap, side);
        }
    }
}
