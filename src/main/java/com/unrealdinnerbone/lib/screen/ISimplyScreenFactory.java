package com.unrealdinnerbone.lib.screen;

import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.tile.BaseTileEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public interface ISimplyScreenFactory<T extends BaseTileEntity & ISimplyInventory, C extends BaseContainer<T>, S extends BaseScreen<T, C>> extends ScreenManager.IScreenFactory<C, S> {

}
