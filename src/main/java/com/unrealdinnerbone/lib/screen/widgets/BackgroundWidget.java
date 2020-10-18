package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Optional;

public class BackgroundWidget<T extends BaseTileEntity & ISimplyInventory> implements IWidget<T> {

    private final ResourceLocation BLANK = new ResourceLocation(SimplyReference.MOD_ID, "textures/gui/blank.png");

    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(BLANK);
        drawer.drawTexture(matrixStack, xPos, yPos, 0, 0, xSize, ySize);
    }

    @Override
    public Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container) {
        return Optional.empty();
    }
}
