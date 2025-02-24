package com.dragn0007.permafrost.entities;

import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.permafrost.Permafrost.MODID;

public class EntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<Mammoth>> MAMMOTH_ENTITY = ENTITY_TYPES.register("mammoth",
            () -> EntityType.Builder.of(Mammoth::new,
                    MobCategory.CREATURE)
                    .sized(2f,4f)
                    .build(new ResourceLocation(MODID,"mammoth").toString()));
}

