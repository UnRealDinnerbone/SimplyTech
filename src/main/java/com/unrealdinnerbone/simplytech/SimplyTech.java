package com.unrealdinnerbone.simplytech;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.event.DataGatherEvent;
import com.unrealdinnerbone.simplytech.config.ClientConfig;
import com.unrealdinnerbone.simplytech.config.CommonConfig;
import com.unrealdinnerbone.simplytech.config.ServerConfig;
import com.unrealdinnerbone.simplytech.event.WrenchEvent;
import com.unrealdinnerbone.simplytech.machines.test.ModelEvent;
import com.unrealdinnerbone.simplytech.packets.SimplyPackets;
import com.unrealdinnerbone.simplytech.registries.*;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.TagUtils;
import net.minecraft.command.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@Mod(SimplyTech.MOD_ID)
public class SimplyTech
{
    public static final String MOD_ID = SimplyReference.MOD_ID;
    private static final Logger LOGGER = LogManager.getLogger();
    private static SimplyTech instance;
    private final ServerConfig config;
    private final ClientConfig clientConfig;
    private final CommonConfig commonConfig;

    public SimplyTech() {
        instance = this;
        config = new ServerConfig(new ForgeConfigSpec.Builder());
        clientConfig = new ClientConfig(new ForgeConfigSpec.Builder());
        commonConfig = new CommonConfig(new ForgeConfigSpec.Builder());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        imHere(SimplyBlocks.class, SimplyMachines.class, SimplyItems.class, SimplyRecipes.class, SimplyTileEntities.class, SimplyTags.class, SimplyPackets.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, config.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, clientConfig.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonConfig.build());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGatherEvent::onDataGen);
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(SimplyTechClient::setup));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModelEvent::onModelRegister);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModelEvent::textureStitch);
        MinecraftForge.EVENT_BUS.addListener(WrenchEvent::onWrenched);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Hello");
        TagUtils.blockTags.clear();
        TagUtils.itemTags.clear();

    }

    public static void imHere(Class<? extends ILoading>... clazz) {
        try {
            for (Class<? extends ILoading> aClass : clazz) {
                aClass.newInstance().load();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static SimplyTech getInstance() {
        return instance;
    }

    public ServerConfig getConfig() {
        return config;
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }
}
