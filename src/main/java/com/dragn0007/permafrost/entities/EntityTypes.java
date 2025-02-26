package com.dragn0007.permafrost.entities;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.permafrost.entities.cervalces_latifrons.Cervalces;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.dragn0007.permafrost.entities.direwolf.Direwolf;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import com.dragn0007.permafrost.entities.quagga.Quagga;
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

    public static final RegistryObject<EntityType<Deinotherium>> DEINOTHERIUM_ENTITY = ENTITY_TYPES.register("deinotherium",
            () -> EntityType.Builder.of(Deinotherium::new,
                            MobCategory.CREATURE)
                    .sized(2f,5f)
                    .build(new ResourceLocation(MODID,"deinotherium").toString()));

    public static final RegistryObject<EntityType<Direwolf>> DIREWOLF_ENTITY = ENTITY_TYPES.register("direwolf",
            () -> EntityType.Builder.of(Direwolf::new,
                            MobCategory.CREATURE)
                    .sized(1.5f,1.5f)
                    .build(new ResourceLocation(MODID,"direwolf").toString()));

    public static final RegistryObject<EntityType<Quagga>> QUAGGA_ENTITY = ENTITY_TYPES.register("quagga",
            () -> EntityType.Builder.of(Quagga::new,
                            MobCategory.CREATURE)
                    .sized(1.5f,2f)
                    .build(new ResourceLocation(LivestockOverhaul.MODID,"quagga").toString()));

    public static final RegistryObject<EntityType<Cervalces>> CERVALCES_LATIFRONS_ENTITY = ENTITY_TYPES.register("cervalces_latifrons",
            () -> EntityType.Builder.of(Cervalces::new,
                            MobCategory.CREATURE)
                    .sized(1.5f,3f)
                    .build(new ResourceLocation(LivestockOverhaul.MODID,"cervalces_latifrons").toString()));
}

