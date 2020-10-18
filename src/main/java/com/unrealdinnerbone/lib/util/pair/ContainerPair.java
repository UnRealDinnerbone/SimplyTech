package com.unrealdinnerbone.lib.util.pair;

import com.mojang.datafixers.util.Pair;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.builders.ContainerBuilder;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;

public class ContainerPair<T extends BaseTileEntity & ISimplyInventory> extends Pair<ContainerBuilder.FactoryObject<T>, RegistryObject<ContainerType<BaseContainer<T>>>> {

    private ContainerPair(ContainerBuilder.FactoryObject<T> first, RegistryObject<ContainerType<BaseContainer<T>>> second) {
        super(first, second);
    }

    public static <T extends BaseTileEntity & ISimplyInventory> ContainerPair<T> of(ContainerBuilder.FactoryObject<T> first, RegistryObject<ContainerType<BaseContainer<T>>> second) {
        return new ContainerPair<>(first, second);
    }

    public ContainerBuilder.FactoryObject<T> getFactory() {
        return super.getFirst();
    }

    public RegistryObject<ContainerType<BaseContainer<T>>> getRegistryObject() {
        return super.getSecond();
    }
}
