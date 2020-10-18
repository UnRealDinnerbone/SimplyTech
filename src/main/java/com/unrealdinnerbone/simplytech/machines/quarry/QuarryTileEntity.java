//package com.unrealdinnerbone.simplytech.machines.quarry;
//
//import com.unrealdinnerbone.lib.api.UpgradeType;
//import com.unrealdinnerbone.lib.tile.TickableEnergyTileEntity;
//import com.unrealdinnerbone.simplytech.SimplyTech;
//import com.unrealdinnerbone.simplytech.config.ServerConfig;
//import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
//import com.unrealdinnerbone.lib.api.quarry.IBlockSuppler;
//import com.unrealdinnerbone.lib.api.quarry.IOreGiver;
//import com.unrealdinnerbone.lib.world.OreDistributions;
//import com.unrealdinnerbone.lib.storage.RegistryObjectStorage;
//import com.unrealdinnerbone.lib.util.SidedBlockHandler;
//import com.unrealdinnerbone.lib.storage.SimplyEnergyStorage;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.BlockPos;
//import net.minecraftforge.common.util.LazyOptional;
//
//import javax.crypto.Mac;
//import java.util.ArrayList;
//import java.util.List;
//
//public class QuarryTileEntity extends TickableEnergyTileEntity<ServerConfig.MachineConfig> {
//
//    private final List<SidedBlockHandler<IBlockSuppler>> inputHandles;
//    private final List<SidedBlockHandler<IOreGiver>> outputHandles;
//    private final LazyOptional<RegistryObjectStorage<Block>> oreBlockState;
//
//    public QuarryTileEntity() {
//        super(SimplyTileEntities.QUARRY_TYPE, SimplyTech.getInstance().getConfig().QUARRY_CONFIG);
//        inputHandles = new ArrayList<>();
//        outputHandles = new ArrayList<>();
//        oreBlockState = registerNBTStorage(LazyOptional.of(() -> new RegistryObjectStorage<>("ore_state", Blocks.AIR)));
//    }
//
//    @Override
//    public void onFirstTick() {
//        if(isServer()) {
//            updateUpgradesCache();
//            updateOreBlockState();
//        }
//    }
//
//    @Override
//    public void onServerTick() {
//        SimplyEnergyStorage<ServerConfig.MachineConfig> energyStorage = getEnergyStorage();
//        int totalEnergyNeed = energyStorage.getConfig().getCostPerOperation();
//        if (energyStorage.hasEnergy(totalEnergyNeed)) {
//            for (SidedBlockHandler<IBlockSuppler> inputHandle : inputHandles) {
//                totalEnergyNeed += inputHandle.get().getConfig().getCostPerOperation();
//                if (energyStorage.hasEnergy(totalEnergyNeed)) {
//                    for (SidedBlockHandler<IOreGiver> outputHandle : outputHandles) {
//                        totalEnergyNeed += outputHandle.get().getConfig().getCostPerOperation();
//                        if (energyStorage.hasEnergy(totalEnergyNeed)) {
//                            BlockState oreBlockState = getOreBlockState();
//                            if (inputHandle.get().hasStone(world, inputHandle.getDirection(), pos.offset(inputHandle.getDirection()))) {
//                                if (outputHandle.get().hasAir(oreBlockState, world, outputHandle.getDirection(), pos.offset(outputHandle.getDirection()))) {
//                                    //Todo THERE A SMALL CHANGE THAT PLAYERS CAN LOSE STONE -_-
//                                    if (inputHandle.get().supplyStone(world, inputHandle.getDirection(), pos.offset(inputHandle.getDirection()))) {
//                                        if (outputHandle.get().giveOre(oreBlockState, world, outputHandle.getDirection(), pos.offset(outputHandle.getDirection()))) {
//                                            updateOreBlockState();
//                                            energyStorage.removeEnergy(totalEnergyNeed);
//                                            return;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//    private BlockState getOreBlockState() {
//        oreBlockState.filter(blockRegistryObjectStorage -> blockRegistryObjectStorage.get() == Blocks.AIR)
//                .ifPresent(blockRegistryObjectStorage -> updateOreBlockState());
//
//        return oreBlockState.map(RegistryObjectStorage::get).filter(block -> block != Blocks.AIR).orElseThrow(() -> new RuntimeException("")).getDefaultState();
//    }
//
//    public void updateOreBlockState() {
//        oreBlockState.ifPresent(blockRegistryObjectStorage ->
//                OreDistributions.getRandomOre(world.getBiome(pos))
//                        .ifPresent(blockState -> blockRegistryObjectStorage
//                                .set(blockState.getBlock())));
//    }
//
//    public void updateUpgradesCache() {
//        outputHandles.clear();
//        inputHandles.clear();
//        Direction[] values = Direction.values();
//        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
//            Direction direction = values[i];
//            BlockPos blockPos = pos.offset(direction);
//            BlockState blockState = world.getBlockState(blockPos);
//            Block block = blockState.getBlock();
//            if (block instanceof IOreGiver) {
//                outputHandles.add(new SidedBlockHandler<>((IOreGiver) block, direction));
//            }
//            if (block instanceof IBlockSuppler) {
//                inputHandles.add(new SidedBlockHandler<>((IBlockSuppler) block, direction));
//            }
//        }
//    }
//}
