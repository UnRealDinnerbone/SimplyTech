package com.unrealdinnerbone.lib.tile;

import net.minecraft.tileentity.ITickableTileEntity;

import java.util.concurrent.atomic.AtomicBoolean;

public interface ISimplyTickableTileEntity extends ITickableTileEntity {

    AtomicBoolean isFirstTick = new AtomicBoolean(true);

    @Override
    default void tick() {
        if (isFirstTick.get()) {
            isFirstTick.set(false);
            onFirstTick();
        }
        onTick();

    }

    void onTick();

    void onFirstTick();


}
