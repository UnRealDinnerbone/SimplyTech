package com.unrealdinnerbone.lib.tile;

import com.unrealdinnerbone.lib.config.IEnergyConfig;
import com.unrealdinnerbone.lib.storage.SimplyEnergyStorage;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EnergyTileEntity<EC extends IEnergyConfig> extends BaseTileEntity {

    private final LazyOptional<SimplyEnergyStorage<EC>> energyStorage;

    public EnergyTileEntity(RegistryObject<? extends TileEntityType<?>> tRegistryObject, EC config) {
        super(tRegistryObject);
        energyStorage = registerNBTStorage(LazyOptional.of(() -> new SimplyEnergyStorage<>(config)));
    }

    @Nonnull
    @Override
    public <B> LazyOptional<B> getCapability(@Nonnull Capability<B> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? energyStorage.cast() : super.getCapability(cap, side);
    }

    public SimplyEnergyStorage<EC> getEnergyStorage() {
        return energyStorage.orElseThrow(() -> new IllegalArgumentException("Energy Storage not yet created"));
    }

}
