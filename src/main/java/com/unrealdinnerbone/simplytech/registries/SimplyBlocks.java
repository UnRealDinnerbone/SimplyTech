package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.lib.block.BaseTileEntityBlock;
import com.unrealdinnerbone.simplytech.machines.TestBlock;
import com.unrealdinnerbone.simplytech.machines.block.TankBlock;
//import com.unrealdinnerbone.simplytech.machines.quarry.QuarryBlock;
import com.unrealdinnerbone.lib.item.SimplyBlockItem;
import com.unrealdinnerbone.lib.tile.generator.CreativeGeneratorTileEntity;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.lib.builders.BlockBuilder;
import com.unrealdinnerbone.lib.util.pair.BlockItemPair;
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.block.BlockInputUpgradeBlock;
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.block.BlockOutputUpgradeBlock;
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.chest.ChestInputUpgradeBlock;
//import com.unrealdinnerbone.simplytech.machines.quarry.upgrades.chest.ChestOutputUpgradeBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SimplyBlocks implements ILoading
{
    private static final DeferredRegister<Block> BLOCK_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.BLOCKS);
    private static final DeferredRegister<Item> ITEM_BLOCK_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.ITEMS);
//    public static final BlockItemPair<QuarryBlock> QUARRY = createBlockAndItem("quarry", QuarryBlock::new);
    public static final BlockItemPair<TestBlock> TEST = createBlockAndItem("test", TestBlock::new);
//    public static final BlockItemPair<BlockInputUpgradeBlock> BLOCK_INPUT_UPGRADE = createBlockAndItem("block_input_upgrade", BlockInputUpgradeBlock::new);
//    public static final BlockItemPair<BlockOutputUpgradeBlock> BLOCK_OUTPUT_UPGRADE = createBlockAndItem("block_output_upgrade", BlockOutputUpgradeBlock::new);
//    public static final BlockItemPair<ChestInputUpgradeBlock> CHEST_INPUT_UPGRADE = createBlockAndItem("chest_input_upgrade", ChestInputUpgradeBlock::new);
//    public static final BlockItemPair<ChestOutputUpgradeBlock> CHEST_OUTPUT_UPGRADE = createBlockAndItem("chest_output_upgrade", ChestOutputUpgradeBlock::new);
    public static final BlockItemPair<BaseTileEntityBlock> CREATIVE_GENERATOR = createBlockAndItem("creative_generator", BlockBuilder.createTileEntityBlock(CreativeGeneratorTileEntity::new));

    public static final BlockItemPair<TankBlock> TEST_TANK = createBlockAndItem("test_tank", TankBlock::new);

    public static <B extends Block> BlockItemPair<B> createBlockAndItem(String name, Supplier<B> bSupplier) {
        RegistryObject<B> bRegistryObject = BLOCK_REGISTER.register(name, bSupplier);
        RegistryObject<SimplyBlockItem> iRegistryObject = ITEM_BLOCK_REGISTER.register(name, () -> new SimplyBlockItem(bRegistryObject));
        return BlockItemPair.of(bRegistryObject, iRegistryObject);
    }

}
