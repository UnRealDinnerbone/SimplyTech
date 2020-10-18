package com.unrealdinnerbone.lib.world;

import com.unrealdinnerbone.lib.util.RandomList;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DecoratedFeatureConfig;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Supplier;

public class OreDistributions {


    //Todo Add Cache clearing
    private static final HashMap<Biome, RandomList<BlockState>> oreBiomeMap = new HashMap<>();

    private static List<Entry> getOresFromBiomeLocal(Biome biome) {
        List<Entry> entries = new ArrayList<>();
        List<List<Supplier<ConfiguredFeature<?, ?>>>> features = biome.getGenerationSettings().getFeatures();
        for (int i = 0, features_cSize = features.size(); i < features_cSize; i++) {
            List<Supplier<ConfiguredFeature<?, ?>>> configuredFeatures = features.get(i);
            for (int i1 = 0, configuredFeaturesSize = configuredFeatures.size(); i1 < configuredFeaturesSize; i1++) {
                Supplier<ConfiguredFeature<?, ?>> configuredFeatureSupplier = configuredFeatures.get(i1);
                ConfiguredFeature<?, ?> configuredFeature = configuredFeatureSupplier.get();
                if (configuredFeature.config instanceof DecoratedFeatureConfig) {
                    DecoratedFeatureConfig decoratedFeatureConfig = (DecoratedFeatureConfig) configuredFeature.config;
                    IFeatureConfig iFeatureConfig = getConfig(decoratedFeatureConfig);
                    if (iFeatureConfig instanceof OreFeatureConfig) {
                        OreFeatureConfig oreFeatureConfig = (OreFeatureConfig) iFeatureConfig;
                        entries.add(new Entry(oreFeatureConfig.state, oreFeatureConfig.size, blockState ->
                                oreFeatureConfig.target.test(blockState, ThreadLocalRandom.current())));
                    }
                }
            }
        }
        return entries;
    }

    private static RandomList<BlockState> getOresForBiome(Biome biome) {
        if(!oreBiomeMap.containsKey(biome)) {
            oreBiomeMap.put(biome, getRandomCollection(biome));
        }
        return oreBiomeMap.get(biome);
    }

    private static RandomList<BlockState> getRandomCollection(Biome biome) {
        RandomList<BlockState> randomCollection = new RandomList<>();
        getOresFromBiomeLocal(biome).forEach(entry -> randomCollection.add(entry.getSize(), entry.getOreBlockState()));
        return randomCollection;
    }


    public static Optional<BlockState> getRandomOre(Biome biome) {
        return Optional.ofNullable(getOresForBiome(biome).next());
    }

    private static IFeatureConfig getConfig(DecoratedFeatureConfig decoratedFeatureConfig) {
        return decoratedFeatureConfig.feature.get().config instanceof DecoratedFeatureConfig ? getConfig((DecoratedFeatureConfig) decoratedFeatureConfig.feature.get().config) : decoratedFeatureConfig.feature.get().config;
    }


    public static class Entry {
        private final BlockState oreBlockState;
        private final int size;
        private final Function<BlockState, Boolean> allowedBlocks;

        public Entry(BlockState oreBlockState, int size, Function<BlockState, Boolean> allowedBlocks) {
            this.oreBlockState = oreBlockState;
            this.size = size;
            this.allowedBlocks = allowedBlocks;
        }

        public BlockState getOreBlockState() {
            return oreBlockState;
        }

        public boolean isStateAllowed(BlockState blockState) {
            return allowedBlocks.apply(blockState);
        }

        public int getSize() {
            return size;
        }
    }

}
