package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.tile.IProgressHolder;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.util.text.ITextComponent;

import java.util.Optional;

public class ProgressWidget<T extends BaseTileEntity & ISimplyInventory & IProgressHolder> implements IWidget<T> {
    private final int xPos;
    private final int yPos;

    public ProgressWidget(int xPos, int yPos) {
        this.xPos = xPos - 5;
        this.yPos = yPos - 5;
    }

    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(ICONS);
        drawer.drawTexture(matrixStack, xPos + this.xPos, yPos + this.yPos, 22, 0, 22, 15);
        double amountFilled = Math.min(container.getTileEntity().getProgress(), 100) / (double) 100;
        drawer.drawTexture(matrixStack, xPos + this.xPos, yPos + this.yPos, 0, 0, (int) (22 * amountFilled), 15);
    }

    @Override
    public Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container) {
        return Optional.empty();
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

}
