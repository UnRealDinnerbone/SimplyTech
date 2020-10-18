package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.INBTStorage;
import net.minecraft.nbt.CompoundNBT;

public class ProgressStorage implements INBTStorage {

    private double progress;

    public ProgressStorage(double progress) {
        this.progress = progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }
    
    public void increaseProgress(double amount) {
        progress += amount;
    }

    @Override
    public String getName() {
        return "progress";
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putDouble(getName(), progress);
        return compoundNBT;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        this.progress = nbt.getDouble(getName());
    }
}
