package com.unrealdinnerbone.lib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.ResourceLocation;

public interface ITextureDrawer {
    void drawTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height);

    void drawTexture(MatrixStack matrices, int x, int y, int z, float u, float v, int width, int height, int textureHeight, int textureWidth);

    void drawTexture(MatrixStack matrices, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight);

    void drawTexture(MatrixStack matrices, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight);

    void bindTexture(ResourceLocation location);

    ItemRenderer getItemRender();
}
