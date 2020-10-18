package com.unrealdinnerbone.lib.api;

import net.minecraft.util.IStringSerializable;

public enum UpgradeType implements IStringSerializable {
    NONE,
    IRON,
    GOLD,
    DIAMOND,
    NETHERITE;

    @Override
    public String getString() {
        return name().toLowerCase();
    }

    public static final UpgradeType[] TEXTURES = {IRON, GOLD, DIAMOND, NETHERITE};
}
