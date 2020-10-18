package com.unrealdinnerbone.lib.item;

import com.unrealdinnerbone.lib.data.api.IRecipeProvider;
import com.unrealdinnerbone.lib.data.ItemModelProvider;
import com.unrealdinnerbone.simplytech.registries.SimplyItems;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class AutoFeederItem extends SimplyItem implements IRecipeProvider {

    private final Type type;

    public AutoFeederItem(Type type) {
        super(new Item.Properties().maxStackSize(1));
        this.type = type;
    }

    @Override
    public void registerItemModel(Item item, ItemModelProvider provider) {
        provider.itemGenerated(item, new ResourceLocation(SimplyTech.MOD_ID, "item/auto_feeder/" + type.name().toLowerCase()));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!worldIn.isRemote && entityIn instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            if(playerEntity.getFoodStats().getFoodLevel() <= 19) {
                float progress = getProgress(stack);
                float amountToRemove = 20 - playerEntity.getFoodStats().getFoodLevel();
                if(progress >= amountToRemove) {
                    playerEntity.getFoodStats().addStats(1, type.getSaturationLevel());
                    setProgress(stack, --progress);
                }
            }
        }
        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getProgress(stack) != type.maxStorage;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1 - ((double) getProgress(stack) / (double) type.maxStorage);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent(SimplyTech.MOD_ID + ".item.auto_feeder.tooltip.saturation_level", type.getSaturationLevel()));
        tooltip.add(new StringTextComponent(String.valueOf(getProgress(stack))));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public static float getProgress(ItemStack itemStack) {
        if(itemStack.getItem() instanceof AutoFeederItem) {
            CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("foodLevel");
            if(compoundNBT.contains("level")) {
                return compoundNBT.getFloat("level");
            }else {
                setProgress(itemStack, 0);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(!worldIn.isRemote) {
            ItemStack itemstack = playerIn.getHeldItem(handIn);
            float storageLeft = type.maxStorage - getProgress(itemstack);
            if(storageLeft > 0) {
                for (ItemStack itemStack : playerIn.inventory.mainInventory) {
                    if(itemStack.getItem().getRegistryName().equals(SimplyItems.COMPOST.get().getRegistryName())) {
                        int amountToTake = storageLeft - itemStack.getCount() < 0 ? (int) storageLeft : itemStack.getCount();
                        itemStack.shrink(amountToTake);
                        storageLeft -= amountToTake;
                        if(storageLeft <= 0) {
                            break;
                        }
                    }
                }
            }
            setProgress(itemstack, type.maxStorage - storageLeft);
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }


    public static void setProgress(ItemStack itemStack, float level) {
        if(itemStack.getItem() instanceof AutoFeederItem) {
            AutoFeederItem feedItem = (AutoFeederItem) itemStack.getItem();
            itemStack.getOrCreateChildTag("foodLevel").putFloat("level", Math.min(level, feedItem.type.maxStorage));
        }
    }

    @Override
    public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {
        if(this.type.craftingItem != null) {
            ShapedRecipeBuilder.shapedRecipe(item)
                    .patternLine("MBM")
                    .patternLine("MGM")
                    .patternLine(" M ")
                    .key('M', type.craftingItem)
                    .key('G', Tags.Items.GLASS)
                    .key('B', Items.BUCKET)
                    .addCriterion("has_material", RecipeProvider.hasItem(type.craftingItem))
                    .addCriterion("has_glass", RecipeProvider.hasItem(Items.GLASS))
                    .addCriterion("has_bucket", RecipeProvider.hasItem(Items.BUCKET))
                    .build(iFinishedRecipeConsumer);
        }else {
            RecipeProvider.smithingReinforce(iFinishedRecipeConsumer, SimplyItems.AUTO_FEEDER.get(Type.DIAMOND).get(), item);
        }
    }

    public enum Type {
        WOODEN(32, 0, ItemTags.PLANKS),
        STONE(64, 0.1f, Tags.Items.STONE),
        IRON(128, 0.25f, Tags.Items.INGOTS_IRON),
        GOLDEN(256,  1.0f/3f, Tags.Items.INGOTS_GOLD),
        EMERALD(512, 0.5f, Tags.Items.GEMS_EMERALD),
        DIAMOND(1024,  1f, Tags.Items.GEMS_DIAMOND),
        NETHERITE(3072,  2f, null);

        private final int maxStorage;
        private final float saturationLevel;
        private final ITag<Item> craftingItem;

        Type(int maxStorage, float saturationLevel, ITag<Item> craftingItem) {
            this.maxStorage = maxStorage;
            this.saturationLevel = saturationLevel;
            this.craftingItem = craftingItem;
        }

        public int getMaxStorage() {
            return maxStorage;
        }

        public ITag<Item> getCraftingItem() {
            return craftingItem;
        }

        public float getSaturationLevel() {
            return saturationLevel;
        }
    }
}
