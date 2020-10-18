package com.unrealdinnerbone.lib.data;

import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(net.minecraft.data.DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        RegistryUtils.getForType(ForgeRegistries.ITEMS).forEach(deferredRegister -> {
            deferredRegister.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .filter(itemRegistryObject -> itemRegistryObject.get() instanceof IRecipeProvider)
                    .map(itemRegistryObject -> (IRecipeProvider) itemRegistryObject.get())
                    .forEach(iRecipeProvider -> iRecipeProvider.createRecipe(((Item) iRecipeProvider).asItem(), consumer));
        });
        RegistryUtils.getForType(ForgeRegistries.BLOCKS).forEach(deferredRegister -> {
            deferredRegister.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .filter(itemRegistryObject -> itemRegistryObject.get() instanceof IRecipeProvider)
                    .map(itemRegistryObject -> (IRecipeProvider) itemRegistryObject.get())
                    .filter(iRecipeProvider -> ((Block) iRecipeProvider).asItem() != Items.AIR)
                    .forEach(iRecipeProvider -> iRecipeProvider.createRecipe(((Block) iRecipeProvider).asItem(), consumer));
        });
    }

}
