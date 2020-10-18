package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.util.text.ITextComponent;

import java.util.Optional;

public class LargeSlotWidget<T extends BaseTileEntity & ISimplyInventory> implements IWidget<T> {

    protected final int xPos;
    protected final int yPos;

    public LargeSlotWidget(int xPos, int yPos) {
        this.xPos = xPos - 5;
        this.yPos = yPos - 5;
    }

    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(ICONS);
        drawer.drawTexture(matrixStack, xPos + this.xPos, yPos + this.yPos, 0, 16, 26, 26);
    }

    @Override
    public Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container) {
        return Optional.empty();
    }



}
