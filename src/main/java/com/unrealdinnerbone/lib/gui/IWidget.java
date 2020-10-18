package com.unrealdinnerbone.lib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface IWidget<T extends BaseTileEntity & ISimplyInventory> {

    ResourceLocation ICONS = new ResourceLocation(SimplyReference.MOD_ID, "textures/gui/icons.png");

    void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer);

    Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container);

    default List<Rectangle2d> getJEIAreas() {
        return Collections.emptyList();
    }

}
