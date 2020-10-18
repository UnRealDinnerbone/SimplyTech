package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.config.IEnergyConfig;
import com.unrealdinnerbone.lib.INBTStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class SimplyEnergyStorage<EC extends IEnergyConfig> extends EnergyStorage implements INBTStorage {

    private final EC config;

    public SimplyEnergyStorage(EC energyConfig) {
        super(energyConfig.getStorageAmount(), energyConfig.getMaxInput(), energyConfig.getMaxOutput());
        this.config = energyConfig;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int removeEnergy(int amount) {
        if(amount > this.energy) {
            this.energy = 0;
            return amount - this.energy;
        }else {
            this.energy -= amount;
            return 0;
        }
    }

    public EC getConfig() {
        return config;
    }

    public boolean hasEnergy(int amount) {
        return getEnergyStored() >= amount;
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt(getName(), getEnergyStored());
        return tag;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        if(nbt.contains(getName())) {
            setEnergy(nbt.getInt(getName()));

        }
    }

    @Override
    public String getName() {
        return "energy";
    }
}
