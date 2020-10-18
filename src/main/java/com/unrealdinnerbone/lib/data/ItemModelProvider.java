package com.unrealdinnerbone.lib.data;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.data.api.IItemModelProvider;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemModelProvider extends net.minecraftforge.client.model.generators.ItemModelProvider {

    public ItemModelProvider(net.minecraft.data.DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, SimplyReference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        RegistryUtils.getForType(ForgeRegistries.BLOCKS).forEach(deferredRegister -> {
            deferredRegister.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .filter(itemRegistryObject -> itemRegistryObject.get() instanceof IItemModelProvider)
                    .map(itemRegistryObject -> (IItemModelProvider) itemRegistryObject.get())
                    .filter(iRecipeProvider -> ((Block) iRecipeProvider).asItem() != Items.AIR)
                    .forEach(iRecipeProvider -> iRecipeProvider.registerItemModel(((Block) iRecipeProvider).asItem(), this));
        });
        RegistryUtils.getForType(ForgeRegistries.ITEMS).forEach(deferredRegister -> {
            deferredRegister.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .filter(itemRegistryObject -> itemRegistryObject.get() instanceof IItemModelProvider)
                    .map(itemRegistryObject -> (IItemModelProvider) itemRegistryObject.get())
                    .forEach(iRecipeProvider -> iRecipeProvider.registerItemModel(((Item) iRecipeProvider).asItem(), this));
        });
    }

    public void itemGenerated(Item item, ResourceLocation texture) {
        getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", texture);
    }

}
