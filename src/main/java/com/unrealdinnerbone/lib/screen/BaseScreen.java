package com.unrealdinnerbone.lib.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.gui.IButton;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseScreen<T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>> extends ContainerScreen<C> implements ITextureDrawer {

    private final List<IWidget<T>> widgets = new ArrayList<>();

    protected BaseScreen(C c, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(c, playerInventory, titleIn);
    }

    public void registerWidget(IWidget<T> widget) {
        widgets.add(widget);
    }

    @Override
    protected final void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        int xPos = this.guiLeft;
        int yPos = (this.height - this.ySize) / 2;
        for (int i = 0, widgetsSize = widgets.size(); i < widgetsSize; i++) {
            widgets.get(i).render(container, matrixStack, partialTicks, x, y, xPos, yPos, xSize, ySize, this);
        }
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int p_231044_5_) {
        for (IWidget<T> widget : widgets) {
            if(widget instanceof IButton) {
                IButton<T> button = (IButton<T>) widget;
                int xPos = this.guiLeft;
                int yPos = (this.height - this.ySize) / 2;
                if(button.isInRange(xPos, yPos, mouseX, mouseY)) {
                    button.onPressed(container.getTileEntity(), mouseX, mouseY);
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, p_231044_5_);
    }

    public void drawTexture(MatrixStack matrices, int x, int y, int u, int v, int width, int height) {
        blit(matrices, x, y, u, v, width, height);
    }

    public void drawTexture(MatrixStack matrices, int x, int y, int z, float u, float v, int width, int height, int textureHeight, int textureWidth) {
        blit(matrices, x, y, z, y, v, width, height, textureHeight, textureWidth);
    }

    public void drawTexture(MatrixStack matrices, int x, int y, int width, int height, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight) {
        blit(matrices, x, y, width, height, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
    }

    public void drawTexture(MatrixStack matrices, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        blit(matrices, x, y, width, height, u, v, width, height, textureWidth, textureHeight);
    }

    public ItemRenderer getItemRender() {
        return itemRenderer;
    }

    @Override
    public void bindTexture(ResourceLocation location) {
        this.getMinecraft().getTextureManager().bindTexture(location);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        int xPos = this.guiLeft;
        int yPos = (this.height - this.ySize) / 2;
        for (int i = 0, widgetsSize = widgets.size(); i < widgetsSize; i++) {
            widgets.get(i).getTooltip(xPos, yPos, mouseX, mouseY, container).ifPresent(iTextComponent ->
                    renderTooltip(matrixStack, iTextComponent, mouseX, mouseY));
        }
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    public Optional<JEIIntegration> getJEIIntegration() {
        return Optional.empty();
    }

    public static class JEIIntegration {
        private final int xPos;
        private final int yPos;
        private final int width;
        private final int height;
        private final List<Rectangle2d> jeiAreas = new ArrayList<>();
        private final ResourceLocation recipeCategoryID;

        public JEIIntegration(int xPos, int yPos, int width, int height, ResourceLocation recipeCategoryID) {
            this.xPos = xPos;
            this.yPos = yPos;
            this.width = width;
            this.height = height;
            this.recipeCategoryID = recipeCategoryID;
        }

        public JEIIntegration addArea(Rectangle2d rectangle2d) {
            this.jeiAreas.add(rectangle2d);
            return this;
        }
        public JEIIntegration addArea(List<Rectangle2d> rectangle2d) {
            this.jeiAreas.addAll(rectangle2d);
            return this;
        }

        public List<Rectangle2d> getJeiAreas() {
            return jeiAreas;
        }

        public ResourceLocation getRecipeCategoryID() {
            return recipeCategoryID;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }

        public int getYPos() {
            return yPos;
        }

        public int getXPos() {
            return xPos;
        }
    }
}
