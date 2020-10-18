package com.unrealdinnerbone.simplytech.registries;

import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.lib.item.upgrade.EnergyCostUpgradeItem;
import com.unrealdinnerbone.lib.item.upgrade.SpeedUpgradeItem;
import com.unrealdinnerbone.simplytech.SimplyTech;

import com.unrealdinnerbone.lib.item.SimplyItem;
import com.unrealdinnerbone.lib.item.AutoFeederItem;
import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import com.unrealdinnerbone.simplytech.items.ScrewDriverItem;
import com.unrealdinnerbone.simplytech.items.UpgradeItem;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimplyItems implements ILoading
{
    private static final DeferredRegister<Item> ITEM_REGISTER = RegistryUtils.createRegistry(ForgeRegistries.ITEMS);


    public static final RegistryObject<SimplyItem> QUARRY_CORE = createItem("quarry_core", (item, consumer) ->
            ShapedRecipeBuilder.shapedRecipe(item)
                    .patternLine("OSO")
                    .patternLine("OEO")
                    .patternLine("OOO")
                    .key('O', Tags.Items.OBSIDIAN)
                    .key('S', Items.ENDER_EYE)
                    .key('E', Tags.Items.NETHER_STARS)
                    .addCriterion("has_obsidian", RecipeProvider.hasItem(Tags.Items.OBSIDIAN))
                    .addCriterion("has_nether_star", RecipeProvider.hasItem(Items.ENDER_EYE))
                    .addCriterion("has_eye_ender", RecipeProvider.hasItem(Tags.Items.NETHER_STARS))
                    .setGroup(SimplyTech.MOD_ID).build(consumer));


    public static final RegistryObject<Item> COMPOST = ITEM_REGISTER.register("compost", SimplyItem::new);
    public static final RegistryObject<SimplyItem> WRENCH = ITEM_REGISTER.register("wrench", SimplyItem::new);
    public static final RegistryObject<ScrewDriverItem> SCREW_DRIVER = ITEM_REGISTER.register("screw_driver", ScrewDriverItem::new);


    public static final Map<AutoFeederItem.Type, RegistryObject<AutoFeederItem>> AUTO_FEEDER = createItemFromEnum("{type}_auto_feeder", AutoFeederItem.Type.values(), AutoFeederItem::new);

    public static final Map<UpgradeType, RegistryObject<SpeedUpgradeItem>> SPEED_UPGRADES = createItemFromEnum("{type}_speed_upgrade", UpgradeType.TEXTURES, SpeedUpgradeItem::new);
    public static final Map<UpgradeType, RegistryObject<EnergyCostUpgradeItem>> ENERGY_UPGRADES = createItemFromEnum("{type}_energy_upgrade", UpgradeType.TEXTURES, EnergyCostUpgradeItem::new);


    public static <T extends Enum<?>, I extends Item> Map<T, RegistryObject<I>> createItemFromEnum(String name, T[] values, Function<T, I> itFunction) {
        return Arrays.stream(values).collect(Collectors.toMap(value -> value, value -> ITEM_REGISTER.register(name.replace("{type}", value.name().toLowerCase()), () -> itFunction.apply(value)), (a, b) -> b));
    }

    public static RegistryObject<SimplyItem> createItem(String name, BiConsumer<Item, Consumer<IFinishedRecipe>> consumerBiConsumer) {
        return ITEM_REGISTER.register(name, () -> new SimplyItem() {
            @Override
            public void createRecipe(Item item, Consumer<IFinishedRecipe> iFinishedRecipeConsumer) {
                consumerBiConsumer.accept(item, iFinishedRecipeConsumer);
            }
        });
    }

}
