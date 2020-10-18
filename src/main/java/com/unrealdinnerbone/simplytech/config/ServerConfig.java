package com.unrealdinnerbone.simplytech.config;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.config.IEnergyConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class ServerConfig {

    private final ForgeConfigSpec.Builder builder;
    public final MachineConfig QUARRY_CONFIG;
    public final UpgradeConfig BLOCK_INPUT_UPGRADE;
    public final UpgradeConfig BLOCK_OUTPUT_UPGRADE;
    public final UpgradeConfig CHEST_INPUT_UPGRADE;
    public final UpgradeConfig CHEST_OUTPUT_UPGRADE;
    public final Map<UpgradeType, UpgradeConfig> UPGRADE_CONFIG = new HashMap<>();
    public final Map<UpgradeType, SpeedUpgradeConfig> SPEED_CONFIG = new HashMap<>();
    public final Map<UpgradeType, EnergyUpgradeConfig> ENERGY_CONFIG = new HashMap<>();


    public final MachineConfig EXTRUDER;
    public final MachineConfig FOOD_PROCESSOR;
    public final MachineConfig POWERED_BLAST_FURNACE;
    public final MachineConfig POWERED_FURNACE;
    public final MachineConfig POWERED_SMOKER;
    public final MachineConfig POWERED_SMITING_TABLE;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        this.builder = builder;
        QUARRY_CONFIG = new MachineConfig(builder, "quarry", 10000, 1000, 1000000, 1);
        BLOCK_INPUT_UPGRADE = new UpgradeConfig(builder, "block_input", 100000, 1000, 1000000, 1);
        BLOCK_OUTPUT_UPGRADE = new UpgradeConfig(builder, "block_output", 100000, 1000, 1000000, 1);
        CHEST_INPUT_UPGRADE = new UpgradeConfig(builder, "chest_input", 100000, 1000, 1000000, 1);
        CHEST_OUTPUT_UPGRADE = new UpgradeConfig(builder, "chest_output", 100000, 1000, 1000000, 1);

        EXTRUDER = new MachineConfig(builder, "extruder", 100000, 1000, 1000000, 1);
        FOOD_PROCESSOR = new MachineConfig(builder, "food_processor", 100000, 1000,  1000000, 1);
        POWERED_BLAST_FURNACE = new MachineConfig(builder, "powered_blast_furnace", 100000, 1000, 1000000, 1);
        POWERED_FURNACE = new MachineConfig(builder, "powered_blast_furnace", 100000, 1000, 1000000, 1);
        POWERED_SMOKER = new MachineConfig(builder, "powered_blast_furnace", 100000, 1000, 1000000, 1);
        POWERED_SMITING_TABLE = new MachineConfig(builder, "powered_blast_furnace", 100000, 1000, 1000000, 1);

        UPGRADE_CONFIG.put(UpgradeType.NONE, new UpgradeConfig(builder, "none", 1, 1, 1, 1));
        UPGRADE_CONFIG.put(UpgradeType.IRON, new UpgradeConfig(builder, "iron", 1.5, 1.5, 1.5, 1.5));
        UPGRADE_CONFIG.put(UpgradeType.GOLD, new UpgradeConfig(builder, "gold", 2, 2, 2, 2));
        UPGRADE_CONFIG.put(UpgradeType.DIAMOND, new UpgradeConfig(builder, "diamond", 3, 3, 3, 3));
        UPGRADE_CONFIG.put(UpgradeType.NETHERITE, new UpgradeConfig(builder, "netherite", 5, 5, 5, 5));

        SPEED_CONFIG.put(UpgradeType.IRON, new SpeedUpgradeConfig(builder, UpgradeType.IRON, 50, 25));
        SPEED_CONFIG.put(UpgradeType.GOLD, new SpeedUpgradeConfig(builder, UpgradeType.GOLD, 100, 50));
        SPEED_CONFIG.put(UpgradeType.DIAMOND, new SpeedUpgradeConfig(builder, UpgradeType.DIAMOND, 150, 75));
        SPEED_CONFIG.put(UpgradeType.NETHERITE, new SpeedUpgradeConfig(builder, UpgradeType.NETHERITE, 200, 100));


        ENERGY_CONFIG.put(UpgradeType.IRON, new EnergyUpgradeConfig(builder, UpgradeType.IRON, 20));
        ENERGY_CONFIG.put(UpgradeType.GOLD, new EnergyUpgradeConfig(builder, UpgradeType.GOLD, 45));
        ENERGY_CONFIG.put(UpgradeType.DIAMOND, new EnergyUpgradeConfig(builder, UpgradeType.DIAMOND, 70));
        ENERGY_CONFIG.put(UpgradeType.NETHERITE, new EnergyUpgradeConfig(builder, UpgradeType.NETHERITE, 95));

    }

    public ForgeConfigSpec build() {
        return builder.build();
    }


    public Map<UpgradeType, SpeedUpgradeConfig> getSpeedConfigs() {
        return SPEED_CONFIG;
    }

    public static class SpeedUpgradeConfig {

        private final ForgeConfigSpec.DoubleValue speedChange;
        private final ForgeConfigSpec.DoubleValue energyChange;

        public SpeedUpgradeConfig(ForgeConfigSpec.Builder builder, UpgradeType type, double speedChange, double energyChange) {
            builder.push("upgrade");
            builder.push("speed");
            builder.push(type.getString());
            this.speedChange = builder.comment("What should the speed change of the machine be in %").defineInRange("speed", speedChange, Integer.MIN_VALUE, Integer.MAX_VALUE);
            this.energyChange = builder.comment("What should the energy change of the machine be with this upgrade in %").defineInRange("energy", energyChange, Integer.MIN_VALUE, Integer.MAX_VALUE);
            builder.pop(3);
        }

        public double getEnergyChange() {
            return energyChange.get();
        }

        public double getSpeedChange() {
            return speedChange.get();
        }
    }

    public static class EnergyUpgradeConfig {

        private final ForgeConfigSpec.DoubleValue energyChange;

        public EnergyUpgradeConfig(ForgeConfigSpec.Builder builder, UpgradeType type, double energyChange) {
            builder.push("upgrade");
            builder.push("energy");
            builder.push(type.getString());
            this.energyChange = builder.comment("What should the energy change of the machine be with this upgrade in %").defineInRange("energy", energyChange, Integer.MIN_VALUE, Integer.MAX_VALUE);
            builder.pop(3);
        }

        public double getEnergyChange() {
            return energyChange.get();
        }

    }



    public static class MachineConfig implements IEnergyConfig {

        private final ForgeConfigSpec.IntValue maxInput;
        private final ForgeConfigSpec.IntValue cost;
        private final ForgeConfigSpec.IntValue storage;
        private final ForgeConfigSpec.IntValue speed;

        public MachineConfig(ForgeConfigSpec.Builder builder, String name, int defaultInput, int costPerOperation, int defaultStorage, int speed) {
            builder.push("machine");
            builder.push(name);
            this.maxInput = builder.comment("How much energy can it receive per tick").defineInRange("input", defaultInput, 1, Integer.MAX_VALUE);
            this.cost = builder.comment("How much energy can is used per tick").defineInRange("cost", costPerOperation, 1, Integer.MAX_VALUE);
            this.storage = builder.comment("How much energy can stored").defineInRange("storage", defaultStorage, 1, Integer.MAX_VALUE);
            this.speed = builder.comment("How much progress should be done each tick").defineInRange("speed", speed, 1, Integer.MAX_VALUE);
            builder.pop(2);
        }


        public int getMaxInput() {
            return maxInput.get();
        }


        public int getMaxOutput() {
            return 0;
        }


        public int getStorageAmount() {
            return storage.get();
        }

        public int getSpeed() {
            return speed.get();
        }

        public int getCostPerOperation() {
            return cost.get();
        }

    }

    public static class UpgradeConfig {

        private final ForgeConfigSpec.DoubleValue maxInputIncreases;
        private final ForgeConfigSpec.DoubleValue costIncreases;
        private final ForgeConfigSpec.DoubleValue storageIncreases;
        private final ForgeConfigSpec.DoubleValue speedIncreases;

        public UpgradeConfig(ForgeConfigSpec.Builder builder, String name, double defaultInput, double costPerOperation, double defaultStorage, double speed) {
            builder.push("machine");
            builder.push(name);
            this.maxInputIncreases = builder.comment("Energy input incress").defineInRange("input", defaultInput, 1.0, Double.MAX_VALUE);
            this.costIncreases = builder.comment("Energy cost incress").defineInRange("cost", costPerOperation, 1.0, Double.MAX_VALUE);
            this.storageIncreases = builder.comment("Storge amount incress").defineInRange("storage", defaultStorage, 1.0, Double.MAX_VALUE);
            this.speedIncreases = builder.comment("Speed incress amount").defineInRange("speed", speed, 1.0, Double.MAX_VALUE);
            builder.pop(2);
        }

        public double getCostIncreases() {
            return costIncreases.get();
        }

        public double getMaxInputIncreases() {
            return maxInputIncreases.get();
        }

        public double getSpeedIncreases() {
            return speedIncreases.get();
        }

        public double getStorageIncreases() {
            return storageIncreases.get();
        }

    }

}
