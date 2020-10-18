package com.unrealdinnerbone.lib.api;

public enum ButtonState {

    ON((byte) 0),
    OFF((byte) 1);

    private final byte id;

    ButtonState(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static ButtonState getFromByte(byte id) {
        return id == 0 ? ON : OFF;
    }

    public ButtonState other() {
        return this == ON ? OFF : ON;
    }
}
