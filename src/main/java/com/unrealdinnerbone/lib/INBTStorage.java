package com.unrealdinnerbone.lib;

import net.minecraft.nbt.CompoundNBT;

public interface INBTStorage {
    String getName();
    CompoundNBT toNBT();
    void fromNBT(CompoundNBT nbt);
}
