package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.EnergyTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Optional;

public class EnergyWidget<T extends EnergyTileEntity & ISimplyInventory> implements IWidget<T> {

    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int x, int y, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(new ResourceLocation(SimplyReference.MOD_ID, "textures/gui/power.png"));
        drawer.drawTexture(matrixStack, xPos + 10, yPos + 21, 14, 0, 14, 42, 28, 42);
        double amountFilled = (double) container.getTileEntity().getEnergyStorage().getEnergyStored() / (double) container.getTileEntity().getEnergyStorage().getMaxEnergyStored();
        int hegigt = (int) Math.ceil((double) 42 * amountFilled);
        drawer.drawTexture(matrixStack, xPos + 10, yPos + 21 + (42 - hegigt), 0, 0, 14, hegigt -1, 28, 42);
    }

    @Override
    public Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container) {
        return isMouseOver(xPos, yPos, mouseX, mouseY) ?
                Optional.of(new StringTextComponent(container.getTileEntity().getEnergyStorage().getEnergyStored() + "/" + container.getTileEntity().getEnergyStorage().getMaxEnergyStored() + " FE")) : Optional.empty();
    }

    public boolean isMouseOver(int xPos, int yPos, int mouseX, int mouseY) {
        return mouseX > xPos + 10 && mouseX < xPos + 24 && mouseY > yPos + 21 && mouseY < yPos + 21 + 42;
    }
}
