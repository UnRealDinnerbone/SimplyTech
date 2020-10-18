package com.unrealdinnerbone.lib.event;

import com.unrealdinnerbone.lib.data.*;
import com.unrealdinnerbone.lib.util.TagUtils;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGatherEvent
{
    public static void onDataGen(GatherDataEvent event) {
        event.getGenerator().addProvider(new RecipeProvider(event.getGenerator()));
        event.getGenerator().addProvider(new BlockModelProvider(event.getGenerator(), event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModelProvider(event.getGenerator(), event.getExistingFileHelper()));
        BlockTagProvider blockTagsProvider = new BlockTagProvider(event.getGenerator(), event.getExistingFileHelper());
        event.getGenerator().addProvider(blockTagsProvider);
        event.getGenerator().addProvider(new ItemTagProvider(event.getGenerator(), blockTagsProvider, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ExtruderDataGenerator(event.getGenerator()));
        event.getGenerator().addProvider(new FoodProcessorDataGenerator(event.getGenerator()));
   }


    public static class ItemTagProvider extends ItemTagsProvider {

        public ItemTagProvider(DataGenerator generatorIn, BlockTagProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
            super(generatorIn, blockTagProvider, SimplyTech.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerTags() {
            TagUtils.itemTags.forEach((blockIOptionalNamedTag, consumer) -> consumer.accept(this.getOrCreateBuilder(blockIOptionalNamedTag)));
        }
    }

    public static class BlockTagProvider extends BlockTagsProvider {

        public BlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, SimplyTech.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerTags() {
            TagUtils.blockTags.forEach((blockIOptionalNamedTag, consumer) -> consumer.accept(this.getOrCreateBuilder(blockIOptionalNamedTag)));
        }
    }
}
