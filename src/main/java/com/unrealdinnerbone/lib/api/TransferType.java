package com.unrealdinnerbone.lib.api;

import net.minecraft.util.IStringSerializable;
import org.lwjgl.openal.AL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public enum TransferType implements IStringSerializable
{
    NONE(0, false, false),
    INPUT(1, true, false),
    INPUT_2(3, true, false),
    OUTPUT(2, false, true);

    private final int id;
    private final boolean input;
    private final boolean output;

    TransferType(int i, boolean input, boolean output) {
        this.id = i;
        this.input = input;
        this.output = output;
    }

    public boolean isInput() {
        return input;
    }

    public boolean isOutput() {
        return output;
    }


    public int getId() {
        return id;
    }

    public static TransferType fromId(int id) {
        return Arrays.stream(TransferType.values()).filter(value -> value.id == id).findFirst().orElse(NONE);
    }

    @Override
    public String getString() {
        return name().toLowerCase();
    }

    public TransferType next(List<TransferType> transferTypes) {
        return next(this, transferTypes);
    }

    public static TransferType next(TransferType transferType, List<TransferType> transferTypes) {
        TransferType transferType1 = transferType == TransferType.NONE ? INPUT : transferType == TransferType.INPUT ? INPUT_2 : transferType == TransferType.INPUT_2 ? OUTPUT : NONE;
        return transferTypes.contains(transferType1) ? transferType1 : next(transferType1, transferTypes);
    }

    public static final List<TransferType> TEXTURES = Arrays.stream(values()).filter(transferType -> transferType != NONE).collect(Collectors.toList());
    public static final List<TransferType> DEFAULT = Arrays.asList(NONE, INPUT, OUTPUT);
    public static final List<TransferType> ALL = Arrays.asList(values());

}
