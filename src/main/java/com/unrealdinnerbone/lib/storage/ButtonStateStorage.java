package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.api.ButtonState;
import net.minecraft.nbt.CompoundNBT;


public class ButtonStateStorage implements INBTStorage {

    private final String name;
    private ButtonState state;
    private Runnable onUpdate;

    public ButtonStateStorage(char name, ButtonState state) {
        this.name = String.valueOf(name);
        this.state = state;
    }

    public ButtonStateStorage(char name, ButtonState state, Runnable onUpdate) {
        this.name = String.valueOf(name);
        this.state = state;
        this.onUpdate = onUpdate;
    }

    public void setState(ButtonState state) {
        this.state = state;
    }

    public ButtonState getState() {
        return state;
    }

    @Override
    public String getName() {
        return String.valueOf(name);
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putByte("state", state.getId());
        return compoundNBT;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        this.state = ButtonState.getFromByte(nbt.getByte("state"));
        if(onUpdate != null) {
            onUpdate.run();
        }
    }
}
