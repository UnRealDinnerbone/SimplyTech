package com.unrealdinnerbone.simplytech.registries;

import com.mojang.datafixers.util.Pair;
import com.unrealdinnerbone.lib.item.SimplyItem;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.TagUtils;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.block.Block;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SimplyTags implements ILoading {

    public static final Tags.IOptionalNamedTag<Item> WRENCH = TagUtils.createItemTag("forge", "wrench", itemBuilder -> itemBuilder.add(SimplyItems.WRENCH.get()));

    public static TagPair createTag(String name, Consumer<TagsProvider.Builder<Block>> blockConsumer, Consumer<TagsProvider.Builder<Item>> itemConsumer) {
        Tags.IOptionalNamedTag<Block> blockIOptionalNamedTag = TagUtils.createBlockTag(name, blockConsumer);
        Tags.IOptionalNamedTag<Item> itemIOptionalNamedTag = TagUtils.createItemTag(name, itemConsumer);
        return new TagPair(blockIOptionalNamedTag, itemIOptionalNamedTag);
    }


    public static class TagPair extends Pair<Tags.IOptionalNamedTag<Block>, Tags.IOptionalNamedTag<Item>> {

        public TagPair(Tags.IOptionalNamedTag<Block> first, Tags.IOptionalNamedTag<Item> second) {
            super(first, second);
        }

        public Tags.IOptionalNamedTag<Block> getBlockTag() {
            return super.getFirst();
        }

        public Tags.IOptionalNamedTag<Item> getItemTag() {
            return super.getSecond();
        }
    }
}
