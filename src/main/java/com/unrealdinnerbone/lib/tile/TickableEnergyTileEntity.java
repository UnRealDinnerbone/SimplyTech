package com.unrealdinnerbone.lib.tile;

import com.unrealdinnerbone.lib.config.IEnergyConfig;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public abstract class TickableEnergyTileEntity<EC extends IEnergyConfig> extends EnergyTileEntity<EC> implements ISimplyTickableTileEntity {

    private int lastEnergy = -1;

    public TickableEnergyTileEntity(RegistryObject<? extends TileEntityType<?>> tRegistryObject, EC config) {
        super(tRegistryObject, config);
    }

    @Override
    public final void tick() {
        ISimplyTickableTileEntity.super.tick();
        if (isFirstTick.get()) {
            if(isServer()) {
                lastEnergy = getEnergyStorage().getEnergyStored();
                markForUpdate();
            }
        } else {
            if (lastEnergy != getEnergyStorage().getEnergyStored()) {
                this.markForUpdate();
                lastEnergy = getEnergyStorage().getEnergyStored();
            }
        }
    }

    @Override
    public final void onTick() {
        if(isServer()) {
            onServerTick();
        }
    }

    public abstract void onServerTick();

    @Override
    public void onFirstTick() {

    }


}
