package com.unrealdinnerbone.simplytech.machines.test;

import com.unrealdinnerbone.lib.api.TransferType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.MultipartBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class MachineBlockModel implements IDynamicBakedModel {

    private final MultipartBakedModel bakedModel;

    public MachineBlockModel(MultipartBakedModel bakedModel) {
        this.bakedModel = bakedModel;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand, IModelData modelData) {
        List<BakedQuad> quads = bakedModel.getQuads(state, side, rand, modelData);
        if(modelData.getData(MachineModelData.UPGRADE_PROPERTY) != null) {
            for (Map.Entry<Direction, TransferType> entry : modelData.getData(MachineModelData.UPGRADE_PROPERTY).getTransferTypes().entrySet()) {
                Direction direction = entry.getKey();
                TransferType transferType = entry.getValue();
                if(transferType != TransferType.NONE) {
                    quads.addAll(ModelEvent.MODEL_TABLE.get(direction, transferType).getQuads(state, side, rand, modelData));
                }
            }
        }else {
            System.out.println("ACKAE");
        }
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return bakedModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return bakedModel.isGui3d();
    }

    @Override
    public boolean isSideLit() {
        return bakedModel.isSideLit();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return bakedModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return bakedModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return bakedModel.getOverrides();
    }
}
