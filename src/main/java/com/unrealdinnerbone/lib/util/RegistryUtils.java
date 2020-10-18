package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.api.SimplyReference;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.*;

public class RegistryUtils {

    private static final HashMap<IForgeRegistry<?>, List<DeferredRegister<?>>> registries = new HashMap<>();

    public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createRegistry(IForgeRegistry<T> registryEntry) {
        DeferredRegister<T> deferredRegister = DeferredRegister.create(registryEntry, SimplyReference.MOD_ID);
        if(!registries.containsKey(registryEntry)) {
            registries.put(registryEntry, new ArrayList<>());
        }
        registries.get(registryEntry).add(deferredRegister);
        deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        return deferredRegister;
    }

    public static <T extends IForgeRegistryEntry<T>> List<DeferredRegister<?>> getForType(IForgeRegistry<T> registryEntries) {
        return registries.getOrDefault(registryEntries, Collections.emptyList());
    }
}
