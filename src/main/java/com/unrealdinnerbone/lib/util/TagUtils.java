package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.block.Block;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.function.Consumer;

public class TagUtils
{
    public static final HashMap<Tags.IOptionalNamedTag<Block>, Consumer<TagsProvider.Builder<Block>>> blockTags = new HashMap<>();
    public static final HashMap<Tags.IOptionalNamedTag<Item>, Consumer<TagsProvider.Builder<Item>>> itemTags = new HashMap<>();

    public static Tags.IOptionalNamedTag<Block> createBlockTag(String name, Consumer<TagsProvider.Builder<Block>> consumer) {
        Tags.IOptionalNamedTag<Block> tag = BlockTags.createOptional(new ResourceLocation(SimplyReference.MOD_ID, name));
        blockTags.put(tag, consumer);
        return tag;
    }

    public static Tags.IOptionalNamedTag<Item> createItemTag(String name, Consumer<TagsProvider.Builder<Item>> consumer) {
        return createItemTag(SimplyTech.MOD_ID, name, consumer);
    }

    public static Tags.IOptionalNamedTag<Item> createItemTag(String modId, String name, Consumer<TagsProvider.Builder<Item>> consumer) {
        Tags.IOptionalNamedTag<Item> tag = ItemTags.createOptional(new ResourceLocation(modId, name));
        itemTags.put(tag, consumer);
        return tag;
    }
}
