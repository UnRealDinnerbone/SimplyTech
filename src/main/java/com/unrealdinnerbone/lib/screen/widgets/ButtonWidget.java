package com.unrealdinnerbone.lib.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.lib.api.ButtonState;
import com.unrealdinnerbone.lib.api.IHasButton;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.gui.IButton;
import com.unrealdinnerbone.lib.gui.ITextureDrawer;
import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ButtonWidget<T extends BaseTileEntity & ISimplyInventory & IHasButton> implements IWidget<T>, IButton<T> {

    private static final int BUTTON_WIDTH = 18;

    private final int xPos;
    private final int yPos;
    private final char name;
    private BiConsumer<Character, T> tSupplier;

    public ButtonWidget(int xPos, int yPos, char name, BiConsumer<Character, T> tSupplier) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
        this.tSupplier = tSupplier;
    }


    @Override
    public void render(BaseContainer<T> container, MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY, int xPos, int yPos, int xSize, int ySize, ITextureDrawer drawer) {
        drawer.bindTexture(new ResourceLocation(SimplyTech.MOD_ID, "textures/gui/button.png"));
        int value = isInRange(xPos, yPos, mouseX, mouseY) ? 20 : 0;
        drawer.drawTexture(matrixStack, xPos + this.xPos, yPos + this.yPos, 0, value, 20, 20);
        ButtonState buttonState = container.getTileEntity().getButtonState(name);
        ItemStack stack = new ItemStack(Items.HOPPER);
        if(buttonState == ButtonState.ON) {
            stack.addEnchantment(Enchantments.INFINITY, 1);
        }
        drawer.getItemRender().renderItemAndEffectIntoGUI(stack, xPos + this.xPos + 2, yPos + this.yPos + 1);
    }

    @Override
    public Optional<ITextComponent> getTooltip(int xPos, int yPos, int mouseX, int mouseY, BaseContainer<T> container) {
        if(isInRange(xPos, yPos, mouseX, mouseY)) {
            ButtonState buttonState = container.getTileEntity().getButtonState(name);
            return Optional.of(new TranslationTextComponent(SimplyTech.MOD_ID + ".gui."+ name + ".button_mode", buttonState.name()));
        }else {
            return Optional.empty();
        }
    }

    @Override
    public void onPressed(T t, double xPos, double yPos) {
        Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        tSupplier.accept(name, t);
    }

    @Override
    public boolean isInRange(double xPos, double yPos, double mouseX, double mouseY) {
        double minX = this.xPos + xPos;
        double minY = this.yPos + yPos;
        if(mouseX >= minX && mouseY >= minY) {
            if(mouseX <= minX + BUTTON_WIDTH && mouseY <= minY + BUTTON_WIDTH) {
                return true;
            }
        }
        return false;
    }
}
