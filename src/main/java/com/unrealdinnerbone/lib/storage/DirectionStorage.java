package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.api.TransferType;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DirectionStorage implements INBTStorage {

    private final String name;
    private final Runnable onUpdate;
    private final HashMap<Direction, TransferType> transferTypeHashMap;

    public DirectionStorage(String name, Runnable onUpdate) {
        this.name = name;
        this.onUpdate = onUpdate;
        this.transferTypeHashMap = new HashMap<>();
        for (Direction value : Direction.values()) {
            transferTypeHashMap.put(value, TransferType.NONE);
        }
    }

    public void set(Direction direction, TransferType transferType) {
        this.transferTypeHashMap.put(direction, transferType);
    }

    public TransferType get(Direction direction) {
        return transferTypeHashMap.get(direction);
    }

    public HashMap<Direction, TransferType> getTransferTypeHashMap() {
        return transferTypeHashMap;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        for (Map.Entry<Direction, TransferType> directionTransferTypeEntry : transferTypeHashMap.entrySet()) {
            compoundNBT.putInt(directionTransferTypeEntry.getKey().getString(), directionTransferTypeEntry.getValue().getId());
        }
        return compoundNBT;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        transferTypeHashMap.clear();
        for (Direction value : Direction.values()) {
            transferTypeHashMap.put(value, TransferType.fromId(nbt.getInt(value.getString())));
        }
        onUpdate.run();
    }
}
