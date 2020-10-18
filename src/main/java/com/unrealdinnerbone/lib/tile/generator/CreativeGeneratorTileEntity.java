package com.unrealdinnerbone.lib.tile.generator;

import com.unrealdinnerbone.simplytech.registries.SimplyTileEntities;
import com.unrealdinnerbone.lib.config.IEnergyConfig;
import com.unrealdinnerbone.lib.tile.TickableEnergyTileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.energy.CapabilityEnergy;

public class CreativeGeneratorTileEntity extends TickableEnergyTileEntity<CreativeGeneratorTileEntity.CreativeConfig> {

    private static final CreativeConfig CREATIVE_CONFIG = new CreativeConfig();

    public CreativeGeneratorTileEntity() {
        super(SimplyTileEntities.CREATIVE_GENERATOR, CREATIVE_CONFIG);
    }

    @Override
    public void onServerTick() {
        getEnergyStorage().setEnergy(Integer.MAX_VALUE);
        Direction[] values = Direction.values();
        for (int i = 0, valuesLength = values.length; i < valuesLength; i++) {
            Direction value = values[i];
            getTileEntity(pos.offset(value))
                    .flatMap(tileEntity -> tileEntity.getCapability(CapabilityEnergy.ENERGY, value.getOpposite())
                    .filter(energyStorage -> true))
                    .ifPresent(energyStorage -> energyStorage.receiveEnergy(Integer.MAX_VALUE, false));
        }
    }

    public static class CreativeConfig implements IEnergyConfig {

        @Override
        public int getMaxInput() {
            return 0;
        }

        @Override
        public int getMaxOutput() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getStorageAmount() {
            return Integer.MAX_VALUE;
        }

    }
}
