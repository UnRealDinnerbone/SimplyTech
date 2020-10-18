package com.unrealdinnerbone.lib.api;

public interface IHasButton {
    ButtonState getButtonState(char name);

    void setButtonState(char name, ButtonState state);
}
