package com.unrealdinnerbone.intergation.jei;

import com.unrealdinnerbone.intergation.jei.base.BasicRecipeCategory;
import com.unrealdinnerbone.lib.builders.ContainerBuilder;
import com.unrealdinnerbone.lib.builders.ScreenBuilder;
import com.unrealdinnerbone.lib.container.BaseContainer;
import com.unrealdinnerbone.lib.screen.BaseScreen;
import com.unrealdinnerbone.lib.util.RecipeUtils;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.SimplyTechClient;
import com.unrealdinnerbone.simplytech.machines.extruder.ExtruderTileEntity;
import com.unrealdinnerbone.simplytech.registries.JEIIntegrations;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static com.unrealdinnerbone.intergation.jei.base.SimplyRecipeCategory.createID;

@JeiPlugin
public class SimplyJEIPlugin implements IModPlugin {

    private final ResourceLocation ID = new ResourceLocation(SimplyTech.MOD_ID, SimplyTech.MOD_ID);

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SimplyMachines.POWERED_BLAST_FURNACE), VanillaRecipeCategoryUid.BLASTING);
        registration.addRecipeCatalyst(new ItemStack(SimplyMachines.POWERED_FURNACE), VanillaRecipeCategoryUid.FURNACE);
        registration.addRecipeCatalyst(new ItemStack(SimplyMachines.POWERED_SMOKER), VanillaRecipeCategoryUid.SMOKING);
        registration.addRecipeCatalyst(new ItemStack(SimplyMachines.POWERED_SMITING_TABLE), VanillaRecipeCategoryUid.SMITHING);
        JEIIntegrations.getIntegrations().forEach(jedIntegration -> {
            for (Supplier<ItemStack> icon : jedIntegration.getIcons()) {
                registration.addRecipeCatalyst(icon.get(), createID(jedIntegration.getType().toString()));
            }
        });
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        JEIIntegrations.getIntegrations().forEach(jedIntegration -> {
            registration.addRecipeCategories(new BasicRecipeCategory<>(jedIntegration.getType().toString(), () -> new ItemStack(Items.ACACIA_LEAVES), jedIntegration.getRClass(), registration.getJeiHelpers().getGuiHelper()));
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        JEIIntegrations.getIntegrations().forEach(jedIntegration -> registration.addRecipes(RecipeUtils.getRecipes(jedIntegration.getType()), createID(jedIntegration.getType().toString())));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGenericGuiContainerHandler(ScreenBuilder.BuilderScreen.class, new Handler());
    }
    
    public static class Handler implements IGuiContainerHandler<ScreenBuilder.BuilderScreen<?, ?>> {
        @Override
        public Collection<IGuiClickableArea> getGuiClickableAreas(ScreenBuilder.BuilderScreen<?, ?> containerScreen, double mouseX, double mouseY) {
            BaseScreen.JEIIntegration jeiIntegration = containerScreen.getJEIIntegration().orElse(null);
            return jeiIntegration != null ? Collections.singleton(IGuiClickableArea.createBasic(jeiIntegration.getXPos(), jeiIntegration.getYPos(), jeiIntegration.getWidth(), jeiIntegration.getHeight(), jeiIntegration.getRecipeCategoryID())) : Collections.emptySet();
        }

        @Override
        public List<Rectangle2d> getGuiExtraAreas(ScreenBuilder.BuilderScreen<?, ?> containerScreen) {
            List<Rectangle2d> rectangle2ds = containerScreen.getJEIIntegration().map(BaseScreen.JEIIntegration::getJeiAreas).orElse(Collections.emptyList());
            return rectangle2ds;
        }
    }

}
