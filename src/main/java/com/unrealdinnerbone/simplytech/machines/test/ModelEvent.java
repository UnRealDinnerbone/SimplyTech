package com.unrealdinnerbone.simplytech.machines.test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.MultipartBakedModel;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.SimpleModelTransform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ModelEvent {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final Table<Direction, TransferType, IBakedModel> MODEL_TABLE = HashBasedTable.create();

    public static void onModelRegister(ModelBakeEvent event) {
        for (Map.Entry<ResourceLocation, IBakedModel> entry : event.getModelRegistry().entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            IBakedModel iBakedModel = entry.getValue();
            for (SimplyMachines.Machine<?> machine : SimplyMachines.MACHINES) {
                if(machine.getID().equals(resourceLocation)) {
                    if(iBakedModel instanceof MultipartBakedModel) {
                        MultipartBakedModel bakedModel = (MultipartBakedModel) iBakedModel;
                        event.getModelRegistry().put(resourceLocation, new MachineBlockModel(bakedModel));
                    }else {
                        if(!(iBakedModel instanceof SimpleBakedModel)) {
                            LOGGER.error("{} does model is not a MultipartBakedModel side rendering will not work", resourceLocation.toString());
                        }
                    }
                }
            }
        }

        for (Direction direction : Direction.values()) {
            for (TransferType type : TransferType.TEXTURES) {
                try {
                    ResourceLocation resourceLocation = new ResourceLocation(SimplyTech.MOD_ID, "block/machine/transfer/" + type.getString() + "/" + direction.getString());
                    IUnbakedModel unbakedModel = event.getModelLoader().getUnbakedModel(resourceLocation);
                    MODEL_TABLE.put(direction, type, unbakedModel.bakeModel(event.getModelLoader(), ModelLoader.defaultTextureGetter(), new SimpleModelTransform(ImmutableMap.of()), resourceLocation));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void textureStitch(TextureStitchEvent.Pre pre) {
        if (pre.getMap().getTextureLocation().equals(PlayerContainer.LOCATION_BLOCKS_TEXTURE)) {
            for (TransferType texture : TransferType.TEXTURES) {
                pre.addSprite(new ResourceLocation(SimplyTech.MOD_ID, "block/machine/transfer/" + texture.getString()));
            }
        }
    }
}
