package com.dragn0007.permafrost.world;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class BiomeHitter {
    public static final ResourceKey<BiomeModifier> SPAWN_MAMMOTH_COLD = registerKey("spawn_mammoth_cold");
    public static final ResourceKey<BiomeModifier> SPAWN_DEINOTHERIUM_HOT = registerKey("spawn_deinotherium_hot");
    public static final ResourceKey<BiomeModifier> SPAWN_DIREWOLF_COLD = registerKey("spawn_direwolf_cold");
    public static final ResourceKey<BiomeModifier> SPAWN_QUAGGA_PLAINS = registerKey("spawn_quagga_plains");
    public static final ResourceKey<BiomeModifier> SPAWN_CERVALCES_TAIGA = registerKey("spawn_cervalces_taiga");
    public static final ResourceKey<BiomeModifier> SPAWN_CERVALCES_SWAMP = registerKey("spawn_cervalces_swamp");
    public static final ResourceKey<BiomeModifier> SPAWN_PARACERATHERIUM_PLAINS = registerKey("spawn_paraceratherium_plains");
    public static final ResourceKey<BiomeModifier> SPAWN_DINOFELIS_HOT = registerKey("spawn_dinofelis_hot");
    public static final ResourceKey<BiomeModifier> SPAWN_TITANIS_HOT = registerKey("spawn_titanis_hot");
    public static final ResourceKey<BiomeModifier> SPAWN_AUROCHS_PLAINS = registerKey("spawn_aurochs_plains");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);


        context.register(SPAWN_MAMMOTH_COLD, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.MAMMOTH_ENTITY.get(),
                        2,
                        2,
                        6
                ))));

        context.register(SPAWN_DEINOTHERIUM_HOT, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_HOT),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.DEINOTHERIUM_ENTITY.get(),
                        1,
                        1,
                        3
                ))));

        context.register(SPAWN_DIREWOLF_COLD, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD_OVERWORLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.DIREWOLF_ENTITY.get(),
                        2,
                        1,
                        5
                ))));

        context.register(SPAWN_QUAGGA_PLAINS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.QUAGGA_ENTITY.get(),
                        1,
                        1,
                        3
                ))));

        context.register(SPAWN_CERVALCES_TAIGA, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_TAIGA),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.CERVALCES_LATIFRONS_ENTITY.get(),
                        2,
                        1,
                        1
                ))));

        context.register(SPAWN_CERVALCES_SWAMP, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.CERVALCES_LATIFRONS_ENTITY.get(),
                        1,
                        1,
                        1
                ))));

        context.register(SPAWN_PARACERATHERIUM_PLAINS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.PARACERATHERIUM_ENTITY.get(),
                        1,
                        1,
                        1
                ))));

        context.register(SPAWN_DINOFELIS_HOT, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_HOT),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.DINOFELIS_ENTITY.get(),
                        2,
                        1,
                        1
                ))));

        context.register(SPAWN_TITANIS_HOT, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_HOT),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.TITANIS_ENTITY.get(),
                        3,
                        1,
                        3
                ))));

        context.register(SPAWN_AUROCHS_PLAINS, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.AUROCHS_ENTITY.get(),
                        2,
                        1,
                        3
                ))));

    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Permafrost.MODID, name));
    }
}