package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.client.renderer.Rectangle2d;

import java.util.Collections;
import java.util.List;

public class UpgradeStorageWidget<T extends BaseTileEntity & ISimplyInventory> extends LargeSlotWidget<T> {

    public UpgradeStorageWidget(int xPos, int yPos) {
        super(xPos, yPos);
    }

    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(ICONS);
        drawer.drawTexture(matrixStack, xPos + this.xPos, yPos + this.yPos, 0, 42, 26, 26);
    }

    @Override
    public List<Rectangle2d> getJEIAreas() {
        return Collections.singletonList(new Rectangle2d(xPos + this.xPos, yPos + this.yPos, 21, 21));
    }
}
