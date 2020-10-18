package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.lib.tile.generator.CreativeGeneratorTileEntity;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.lib.util.pair.BlockItemPair;
import com.unrealdinnerbone.simplytech.machines.block.TankTileEntity;
//import com.unrealdinnerbone.simplytech.machines.quarry.QuarryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SimplyTileEntities implements ILoading
{
    private static final DeferredRegister<TileEntityType<?>> TILE_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.TILE_ENTITIES);

//    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY_TYPE = create("quarry", QuarryTileEntity::new, SimplyBlocks.QUARRY);
    public static final RegistryObject<TileEntityType<CreativeGeneratorTileEntity>> CREATIVE_GENERATOR = create("creative_generator", CreativeGeneratorTileEntity::new, SimplyBlocks.CREATIVE_GENERATOR);
    public static final RegistryObject<TileEntityType<TankTileEntity>> TEST_TANK = create("test_tank", () -> new TankTileEntity(90000), SimplyBlocks.TEST_TANK);


    private static <T extends TileEntity, B extends Block> RegistryObject<TileEntityType<T>> create(String name, Supplier<T> tileSuppler, BlockItemPair<B> registryObject) {
        return TILE_REGISTER.register(name, () -> TileEntityType.Builder.create(tileSuppler, registryObject.getBlock()).build(null));
    }
}
