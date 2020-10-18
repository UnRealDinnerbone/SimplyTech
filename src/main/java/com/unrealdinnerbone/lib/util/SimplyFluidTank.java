package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.INBTStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class SimplyFluidTank extends FluidTank implements INBTStorage {

    public SimplyFluidTank(int capacity) {
        super(capacity);
    }

    public boolean isFluidValid(FluidStack stack) {
        return getFluid().isEmpty() || stack.isFluidEqual(getFluid());
    }

    @Override
    public String getName() {
        return "fluid";
    }

    @Override
    public CompoundNBT toNBT() {
        return writeToNBT(new CompoundNBT());
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        readFromNBT(nbt);
    }
}
