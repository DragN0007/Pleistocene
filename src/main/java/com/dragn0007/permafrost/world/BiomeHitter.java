package com.dragn0007.permafrost.world;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class BiomeHitter {
    public static final ResourceKey<BiomeModifier> SPAWN_MAMMOTH_COLD = registerKey("spawn_mammoth_cold");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);


        context.register(SPAWN_MAMMOTH_COLD, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_COLD),
                List.of(new MobSpawnSettings.SpawnerData(EntityTypes.MAMMOTH_ENTITY.get(),
                        10,
                        1,
                        6
                ))));

    }

    public static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Permafrost.MODID, name));
    }
}