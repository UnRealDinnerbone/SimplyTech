package com.unrealdinnerbone.lib.storage;

import com.unrealdinnerbone.lib.INBTStorage;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.*;

public class RegistryObjectStorage<V extends IForgeRegistryEntry<V>> implements INBTStorage {

    private final String name;
    private final IForgeRegistry<V> forgeRegistry;
    private V v;

    public RegistryObjectStorage(String name, V v) {
        this.name = name;
        this.forgeRegistry = RegistryManager.ACTIVE.getRegistry(v.getRegistryType());
        this.v = v;
    }

    public void set(V v) {
        this.v = v;
    }

    public V get() {
        return v;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("value", v.getRegistryName().toString());
        return compoundNBT;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        v = forgeRegistry.getValue(new ResourceLocation(nbt.getString("value")));
    }
}
