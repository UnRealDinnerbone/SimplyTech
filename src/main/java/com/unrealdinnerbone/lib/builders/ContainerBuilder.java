package com.unrealdinnerbone.lib.builders;

import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.util.WorldUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ContainerBuilder
{

    public static <T extends BaseTileEntity & ISimplyInventory> Factory<T> create(Class<T> tClass) {
        return new Factory<>(tClass);
    }

    public static class Factory<T extends BaseTileEntity & ISimplyInventory> {

        private final List<Function<T, Slot>> slots;
        private final Class<T> tClass;

        private Factory(Class<T> tClass) {
            this.slots = new ArrayList<>();
            this.tClass = tClass;
        }

        public Factory<T> addSlot(Function<T, Slot> slot) {
            this.slots.add(slot);
            return this;
        }

        public FactoryObject<T> build() {
            return new FactoryObject<>(this);
        }

    }

    public static class FactoryObject<T extends BaseTileEntity & ISimplyInventory> {
        private final Factory<T> factory;
        private RegistryObject<ContainerType<BaseContainer<T>>> containerTypeRegistryObject;

        private FactoryObject(Factory<T> factory) {
            this.factory = factory;
        }

        public BaseContainer<T> create(int windowId, PlayerInventory inv, PacketBuffer data) {
            return new BaseContainer<>(containerTypeRegistryObject.get(), factory.slots, windowId, WorldUtil.getTileEntity(factory.tClass, inv, data), inv);
        }

        public BaseContainer<T> create(int id, PlayerInventory playerInventory, T tileEntity) {
            return new BaseContainer<>(containerTypeRegistryObject.get(), factory.slots, id, tileEntity, playerInventory);
        }

        public void setContainerTypeRegistryObject(RegistryObject<ContainerType<BaseContainer<T>>> containerTypeRegistryObject) {
            this.containerTypeRegistryObject = containerTypeRegistryObject;
        }
    }
}
