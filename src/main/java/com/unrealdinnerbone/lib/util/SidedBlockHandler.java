package com.unrealdinnerbone.lib.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Direction;

public class SidedBlockHandler<T> extends Pair<T, Direction> {

    public SidedBlockHandler(T t, Direction direction) {
        super(t, direction);
    }

    public Direction getDirection() {
        return getSecond();
    }

    public T get() {
        return getFirst();
    }

}
