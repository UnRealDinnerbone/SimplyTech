package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.block.BaseTileEntityBlock;
import com.unrealdinnerbone.lib.builders.BlockBuilder;
import com.unrealdinnerbone.lib.builders.ContainerBuilder;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.item.SimplyBlockItem;
import com.unrealdinnerbone.lib.item.upgrade.EnergyCostUpgradeItem;
import com.unrealdinnerbone.lib.item.upgrade.SpeedUpgradeItem;
import com.unrealdinnerbone.lib.screen.slot.InputSlot;
import com.unrealdinnerbone.lib.screen.slot.OutputSlot;
import com.unrealdinnerbone.lib.screen.slot.UpgradeSlot;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.tile.BasicRecipeTileEntity;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderTileEntity;
import com.unrealdinnerbone.simplytech.machines.foodprocessor.FoodProcessorTileEntity;
import com.unrealdinnerbone.simplytech.machines.minecraft.PoweredBlastFurnaceTileEntity;
import com.unrealdinnerbone.simplytech.machines.minecraft.PoweredFurnaceTileEntity;
import com.unrealdinnerbone.simplytech.machines.minecraft.PoweredSmokerTileEntity;
import com.unrealdinnerbone.simplytech.machines.minecraft.smiting.PoweredSmitingTableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.crypto.Mac;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class SimplyMachines implements ILoading {
    private static final DeferredRegister<ContainerType<?>> CONTAINER_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.CONTAINERS);
    private static final DeferredRegister<Block> BLOCK_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.BLOCKS);
    private static final DeferredRegister<Item> ITEM_BLOCK_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.ITEMS);
    private static final DeferredRegister<TileEntityType<?>> TILE_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.TILE_ENTITIES);

    public static final List<Machine<?>> MACHINES = new ArrayList<>();

    public static final Machine<PoweredBlastFurnaceTileEntity> POWERED_BLAST_FURNACE = createMachine("powered_blast_furnace", PoweredBlastFurnaceTileEntity::new, PoweredBlastFurnaceTileEntity.class, SimplyMachines::registerBasicSlots);
    public static final Machine<ExtruderTileEntity> EXTRUDER = createMachine("extruder", ExtruderTileEntity::new, ExtruderTileEntity.class, SimplyMachines::registerBasicSlots);
    public static final Machine<FoodProcessorTileEntity> FOOD_PROCESSOR = createMachine("food_processor", FoodProcessorTileEntity::new, FoodProcessorTileEntity.class, SimplyMachines::registerBasicSlots);
    public static final Machine<PoweredSmokerTileEntity> POWERED_SMOKER = createMachine("powered_smoker", PoweredSmokerTileEntity::new, PoweredSmokerTileEntity.class, SimplyMachines::registerBasicSlots);
    public static final Machine<PoweredFurnaceTileEntity> POWERED_FURNACE = createMachine("powered_furnace", PoweredFurnaceTileEntity::new, PoweredFurnaceTileEntity.class, SimplyMachines::registerBasicSlots);
    public static final Machine<PoweredSmitingTableTileEntity> POWERED_SMITING_TABLE = createMachine("powered_smithing_table", PoweredSmitingTableTileEntity::new, PoweredSmitingTableTileEntity.class, poweredSmitingTableFactory ->
            poweredSmitingTableFactory.addSlot(extruderTileEntity -> new InputSlot(extruderTileEntity, 0, 47, 32))
                                      .addSlot(tile -> new InputSlot(tile, 1, 79, 60))
                                      .addSlot(extruderTileEntity -> new OutputSlot(extruderTileEntity, 2, 113, 32)));

    private static <T extends BaseTileEntity & ISimplyInventory> Machine<T> createMachine(String theName, Supplier<T> tileEntitySupplier, Class<T> tClass, Function<ContainerBuilder.Factory<T>, ContainerBuilder.Factory<T>> factoryFactoryFunction) {
        RegistryObject<BaseTileEntityBlock> blockRegistryObject = BLOCK_REGISTER.register(theName, () -> BlockBuilder.createFacingTileBlock(theName, tileEntitySupplier).build());
        RegistryObject<SimplyBlockItem> iRegistryObject = ITEM_BLOCK_REGISTER.register(theName, () -> new SimplyBlockItem(blockRegistryObject));
        RegistryObject<TileEntityType<T>> tileEntityTypeRegistryObject = TILE_REGISTER.register(theName, () -> TileEntityType.Builder.create(tileEntitySupplier, blockRegistryObject.get()).build(null));
        ContainerBuilder.FactoryObject<T> factoryObject = factoryFactoryFunction.apply(ContainerBuilder.create(tClass)).build();
        RegistryObject<ContainerType<BaseContainer<T>>> register = CONTAINER_REGISTER.register(theName, () -> IForgeContainerType.create(factoryObject::create));
        factoryObject.setContainerTypeRegistryObject(register);
        Machine<T> tMachine = new Machine<>(blockRegistryObject, iRegistryObject, tileEntityTypeRegistryObject, factoryObject, register);
        MACHINES.add(tMachine);
        return tMachine;
    }

    public static class Machine<T extends BaseTileEntity & ISimplyInventory> implements IItemProvider {
        private final RegistryObject<BaseTileEntityBlock> blockRegistryObject;
        private final RegistryObject<SimplyBlockItem> blockItemRegistryObject;
        private final RegistryObject<TileEntityType<T>> tileEntityTypeRegistryObject;
        private final ContainerBuilder.FactoryObject<T> factoryObject;
        private final RegistryObject<ContainerType<BaseContainer<T>>> containerTypeRegistryObject;

        public Machine(RegistryObject<BaseTileEntityBlock> blockRegistryObject, RegistryObject<SimplyBlockItem> blockItemRegistryObject, RegistryObject<TileEntityType<T>> tileEntityTypeRegistryObject, ContainerBuilder.FactoryObject<T> factoryObject, RegistryObject<ContainerType<BaseContainer<T>>> containerTypeRegistryObject) {
            this.blockRegistryObject = blockRegistryObject;
            this.blockItemRegistryObject = blockItemRegistryObject;
            this.tileEntityTypeRegistryObject = tileEntityTypeRegistryObject;
            this.factoryObject = factoryObject;
            this.containerTypeRegistryObject = containerTypeRegistryObject;
        }

        public RegistryObject<BaseTileEntityBlock> getBlockRegistryObject() {
            return blockRegistryObject;
        }

        public ContainerBuilder.FactoryObject<T> getFactoryObject() {
            return factoryObject;
        }

        public RegistryObject<ContainerType<BaseContainer<T>>> getContainerTypeRegistryObject() {
            return containerTypeRegistryObject;
        }

        public RegistryObject<TileEntityType<T>> getTileEntityTypeRegistryObject() {
            return tileEntityTypeRegistryObject;
        }

        public RegistryObject<SimplyBlockItem> getBlockItemRegistryObject() {
            return blockItemRegistryObject;
        }

        public ResourceLocation getID() {
            return blockItemRegistryObject.getId();
        }

        @Override
        public Item asItem() {
            return blockItemRegistryObject.get();
        }

    }

    public static <T extends BasicRecipeTileEntity<?, ?, ?> & ISimplyInventory> ContainerBuilder.Factory<T> registerBasicSlots(ContainerBuilder.Factory<T> factory) {
        return factory
                .addSlot(t -> new UpgradeSlot(SpeedUpgradeItem.class, t.getUpgradeInventory().getItemHandlerInventory(), 0, 177, 14 - 2))
                .addSlot(t -> new UpgradeSlot(EnergyCostUpgradeItem.class, t.getUpgradeInventory().getItemHandlerInventory(), 1, 177, 35 - 2))
                .addSlot(extruderTileEntity -> new InputSlot(extruderTileEntity, 0, 47, 32))
                .addSlot(extruderTileEntity -> new OutputSlot(extruderTileEntity, 1, 113, 32));
    }
}
