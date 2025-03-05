package com.dragn0007.permafrost.items;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.items.custom.FertilizedTitanisEggItem;
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
    public static final RegistryObject<Item> DEINOTHERIUM_SPAWN_EGG = ITEMS.register("deinotherium_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.DEINOTHERIUM_ENTITY, 0x726662, 0xa79d96, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> DIREWOLF_SPAWN_EGG = ITEMS.register("direwolf_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.DIREWOLF_ENTITY, 0x939da1, 0x6c7679, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> QUAGGA_SPAWN_EGG = ITEMS.register("quagga_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.QUAGGA_ENTITY, 0xbca995, 0xf3ebe2, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> CERVALCES_SPAWN_EGG = ITEMS.register("cervalces_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.CERVALCES_LATIFRONS_ENTITY, 0x4f3b2f, 0x362923, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PARACERATHERIUM_SPAWN_EGG = ITEMS.register("paraceratherium_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.PARACERATHERIUM_ENTITY, 0x87837f, 0x524c47, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> DINOFELIS_SPAWN_EGG = ITEMS.register("dinofelis_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.DINOFELIS_ENTITY, 0x8b471b, 0x1f1d1b, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> TITANIS_SPAWN_EGG = ITEMS.register("titanis_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.TITANIS_ENTITY, 0x5e3a35, 0xeeeae4, new Item.Properties().stacksTo(64)));

    public static final RegistryObject<Item> MAMMOTH_MILK_BUCKET = ITEMS.register("mammoth_milk_bucket",
         () -> new MilkBucketItem(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(1).build()).craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MAMMOTH_MILK_JUG = ITEMS.register("mammoth_milk_jug",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAMMOTH_CHEESE = ITEMS.register("mammoth_cheese",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(9).saturationMod(1).effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 0.8F).build())));
    public static final RegistryObject<Item> MAMMOTH = ITEMS.register("mammoth",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_MAMMOTH = ITEMS.register("cooked_mammoth",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> DEINOTHERIUM = ITEMS.register("deinotherium",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_DEINOTHERIUM = ITEMS.register("cooked_deinotherium",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(14).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> DIREWOLF = ITEMS.register("direwolf",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_DIREWOLF = ITEMS.register("cooked_direwolf",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> CERVID = ITEMS.register("cervid",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_CERVID = ITEMS.register("cooked_cervid",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> PARACERATHERIUM = ITEMS.register("paraceratherium",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_PARACERATHERIUM = ITEMS.register("cooked_paraceratherium",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(12).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> DINOFELIS = ITEMS.register("dinofelis",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(1).meat().build())));
    public static final RegistryObject<Item> COOKED_DINOFELIS = ITEMS.register("cooked_dinofelis",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(1).meat().build())));

    public static final RegistryObject<Item> TITANIS_EGG = ITEMS.register("titanis_egg",
            () -> new Item((new Item.Properties()).stacksTo(64)));
    public static final RegistryObject<Item> FERTILIZED_TITANIS_EGG = ITEMS.register("fertilized_titanis_egg",
            () -> new FertilizedTitanisEggItem((new Item.Properties()).stacksTo(1)));


    public static final RegistryObject<Item> PERMAFROST = ITEMS.register("permafrost",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}