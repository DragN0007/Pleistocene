package com.dragn0007.permafrost.items;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PFItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Permafrost.MODID);

    public static final RegistryObject<Item> MAMMOTH_SPAWN_EGG = ITEMS.register("mammoth_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.MAMMOTH_ENTITY, 0x624b40, 0x553e34, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> MAMMOTH_MILK_BUCKET = ITEMS.register("mammoth_milk_bucket",
         () -> new MilkBucketItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1).build()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MAMMOTH_MILK_JUG = ITEMS.register("mammoth_milk_jug",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAMMOTH_CHEESE = ITEMS.register("mammoth_cheese",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationMod(1).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));
    public static final RegistryObject<Item> MAMMOTH = ITEMS.register("mammoth",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).build())));
    public static final RegistryObject<Item> COOKED_MAMMOTH = ITEMS.register("cooked_mammoth",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(1).build())));


    public static final RegistryObject<Item> PERMAFROST = ITEMS.register("permafrost",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}