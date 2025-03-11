package com.dragn0007.permafrost.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class PFTags {

    public static class Items {

        public static final TagKey<Item> DEINOTHERIUM_FOOD = forgeTag("deinotherium_food");
        public static final TagKey<Item> MAMMOTH_FOOD = forgeTag("mammoth_food");
        public static final TagKey<Item> PARACERATHERIUM_FOOD = forgeTag("paraceratherium_food");
        public static final TagKey<Item> CERVALCES_FOOD = forgeTag("cervalces_food");
        public static final TagKey<Item> DINOFELIS_FOOD = forgeTag("dinofelis_food");
        public static final TagKey<Item> DIREWOLF_FOOD = forgeTag("direwolf_food");
        public static final TagKey<Item> TITANIS_FOOD = forgeTag("titanis_food");

        public static TagKey<Item> forgeTag (String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Entity_Types {
        public static final TagKey<EntityType<?>> MEDIUM_PREDATOR_PREY = forgeTag("medium_predator_prey");
        public static final TagKey<EntityType<?>> PREDATORS = forgeTag("predators");

        public static TagKey<EntityType<?>> forgeTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {



        public static TagKey<Block> forgeTag (String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }

}
