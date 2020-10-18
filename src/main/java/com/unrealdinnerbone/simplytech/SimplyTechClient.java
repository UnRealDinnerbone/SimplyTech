package com.unrealdinnerbone.simplytech;

import com.unrealdinnerbone.lib.api.IHasButton;
import com.unrealdinnerbone.lib.api.IRenderType;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.screen.ISimplyScreenFactory;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.screen.BaseScreen;
import com.unrealdinnerbone.lib.screen.widgets.ButtonWidget;
import com.unrealdinnerbone.lib.screen.widgets.EnergyWidget;
import com.unrealdinnerbone.lib.screen.widgets.ProgressWidget;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.screen.widgets.UpgradeStorageWidget;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.simplytech.packets.ToggleButtonPacket;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.lib.builders.ScreenBuilder;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.util.ClientRecipeBook;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.function.Function;

public class SimplyTechClient
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final BaseScreen.JEIIntegration INTEGRATION = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation(SimplyTech.MOD_ID, "extruder"));
    private static final BaseScreen.JEIIntegration FOOD_PROCESSOR = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation(SimplyTech.MOD_ID, "food_processor"));
    private static final BaseScreen.JEIIntegration POWERED_BLAST_FURNACE = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation("minecraft", "blasting"));
    private static final BaseScreen.JEIIntegration POWERED_SMOKER = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation("minecraft", "smoking"));
    private static final BaseScreen.JEIIntegration POWERED_FURNACE = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation("minecraft", "furnace"));
    private static final BaseScreen.JEIIntegration SMITHING = new BaseScreen.JEIIntegration(74, 31, 24, 15, new ResourceLocation("minecraft", "smithing"));



    public static void setup(final FMLClientSetupEvent event) {
        LOGGER.info("Hello Client!");
        registerScreenFactory(SimplyMachines.EXTRUDER.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(INTEGRATION));

        registerScreenFactory(SimplyMachines.FOOD_PROCESSOR.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(FOOD_PROCESSOR));

        registerScreenFactory(SimplyMachines.POWERED_BLAST_FURNACE.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(POWERED_BLAST_FURNACE));

        registerScreenFactory(SimplyMachines.POWERED_SMOKER.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(POWERED_SMOKER));

        registerScreenFactory(SimplyMachines.POWERED_FURNACE.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(POWERED_FURNACE));


        registerScreenFactory(SimplyMachines.POWERED_SMITING_TABLE.getContainerTypeRegistryObject(), builder -> builder.withAutoWidgets()
                .withWidget(new EnergyWidget<>())
                .withWidget(new ProgressWidget<>(81, 37))
                .withWidget(new ButtonWidget<>(150, 5,  'f', SimplyTechClient::sendButtonUpdate))
                .withWidget(new UpgradeStorageWidget<>(178, 14))
                .withWidget(new UpgradeStorageWidget<>(178, 35))
                .withJEIIntegration(SMITHING));



        RegistryUtils.getForType(ForgeRegistries.BLOCKS).forEach(deferredRegister -> {
            deferredRegister.getEntries().forEach(registryObject -> {
                Block block = ((Block) registryObject.get());
                if(block instanceof IRenderType) {
                    RenderTypeLookup.setRenderLayer(block, ((IRenderType) block).getRenderType());
                }
            });
        });
    }

    public static <T extends BaseTileEntity & ISimplyInventory & IHasButton> void sendButtonUpdate(Character name, T tile) {
        ToggleButtonPacket.sendButtonChangeToServer(tile.getPos(), tile.getButtonState(name).other(), name);
    }

    private static <T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>, S extends BaseScreen<T, C>> void registerScreenFactory(RegistryObject<ContainerType<C>> registryObject, Function<ScreenBuilder.Builder<T, C>, ScreenBuilder.Builder<T, C>> builderConsumer) {
        ScreenManager.registerFactory(registryObject.get(), (ISimplyScreenFactory<T, C, S>) (c, playerInventory, textComponent) -> (S) builderConsumer.apply(ScreenBuilder.create(c, playerInventory, textComponent)).build());
    }


    private static <T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>, E extends ContainerType<C>> void registerScreenFactory(Map<UpgradeType, SimplyMachines.Machine<T>> machineMap, Function<ScreenBuilder.Builder<T, C>, ScreenBuilder.Builder<T, C>> builderConsumer) {
        machineMap.values().forEach(tMachine -> {
            RegistryObject<E> containerTypeRegistryObject = (RegistryObject<E>) tMachine.getContainerTypeRegistryObject();
            ContainerType<C> containerContainerType = containerTypeRegistryObject.get();
            ScreenManager.registerFactory(containerContainerType, new ScreenManager.IScreenFactory<C, ScreenBuilder.BuilderScreen<T, C>>() {
                @Override
                public ScreenBuilder.BuilderScreen<T, C> create(C c, PlayerInventory playerInventory, ITextComponent textComponent) {
                    return builderConsumer.apply(ScreenBuilder.create(c, playerInventory, textComponent)).build();
                }
            });

        });
    }
}
