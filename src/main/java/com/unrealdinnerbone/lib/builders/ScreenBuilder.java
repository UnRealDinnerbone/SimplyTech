package com.unrealdinnerbone.lib.builders;

import com.unrealdinnerbone.lib.gui.IWidget;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.screen.BaseScreen;
import com.unrealdinnerbone.lib.screen.slot.InputSlot;
import com.unrealdinnerbone.lib.screen.slot.OutputSlot;
import com.unrealdinnerbone.lib.screen.widgets.BackgroundWidget;
import com.unrealdinnerbone.lib.screen.widgets.LargeSlotWidget;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScreenBuilder {

    public static class Builder<T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>> {

        private final C container;
        private final PlayerInventory playerInventory;
        private final ITextComponent textComponent;
        private final List<IWidget<T>> widgetList;
        private BaseScreen.JEIIntegration jeiIntegration = null;


        private Builder(C container, PlayerInventory playerInventory, ITextComponent textComponent) {
            this.container = container;
            this.playerInventory = playerInventory;
            this.textComponent = textComponent;
            this.widgetList = new ArrayList<>();
        }

        public Builder<T, C> withWidget(IWidget<T> widget) {
            this.widgetList.add(widget);
            return this;
        }

        public Builder<T, C> withAutoWidgets() {
            withWidget(new BackgroundWidget<>());
            List<Slot> customSlots = container.getCustomSlots();
            for (int i = 0, customSlotsSize = customSlots.size(); i < customSlotsSize; i++) {
                Slot customSlot = customSlots.get(i);
                if(customSlot instanceof OutputSlot || customSlot instanceof InputSlot) {
                    withWidget(new LargeSlotWidget<>(customSlot.xPos, customSlot.yPos));
                }
            }
            return this;
        }

        public Builder<T, C> withJEIIntegration(BaseScreen.JEIIntegration jeiIntegration) {
            this.jeiIntegration = jeiIntegration;
            widgetList.forEach(tiWidget -> jeiIntegration.addArea(tiWidget.getJEIAreas()));
            return this;
        }

        public BuilderScreen<T, C> build() {
            BuilderScreen<T, C> builderScreen = new BuilderScreen<>(container, playerInventory, textComponent);
            widgetList.forEach(builderScreen::registerWidget);
            builderScreen.setJeiIntegration(jeiIntegration);
            return builderScreen;
        }
    }

    public static <T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>> Builder<T, C> create(C container, PlayerInventory playerInventory, ITextComponent title) {
        return new Builder<>(container, playerInventory, title);
    }

    public static class BuilderScreen<T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>> extends BaseScreen<T, C> {

        private BaseScreen.JEIIntegration jeiIntegration = null;

        public BuilderScreen(C c, PlayerInventory playerInventory, ITextComponent titleIn) {
            super(c, playerInventory, titleIn);
        }

        @Override
        public Optional<JEIIntegration> getJEIIntegration() {
            return Optional.ofNullable(jeiIntegration);
        }

        public void setJeiIntegration(JEIIntegration jeiIntegration) {
            this.jeiIntegration = jeiIntegration;
        }
    }
}

