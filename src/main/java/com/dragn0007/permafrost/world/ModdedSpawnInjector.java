package com.dragn0007.permafrost.world;

import com.dragn0007.dragnlivestock.entities.horse.OHorseModel;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.dragnlivestock.entities.util.marking_layer.EquineEyeColorOverlay;
import com.dragn0007.dragnlivestock.entities.util.marking_layer.EquineMarkingOverlay;
import com.dragn0007.dragnlivestock.util.LivestockOverhaulCommonConfig;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.aurochs.Aurochs;
import com.dragn0007.permafrost.entities.aurochs.AurochsModel;
import com.dragn0007.permafrost.entities.cervalces_latifrons.Cervalces;
import com.dragn0007.permafrost.entities.cervalces_latifrons.CervalcesModel;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.dragn0007.permafrost.entities.deinotherium.DeinotheriumModel;
import com.dragn0007.permafrost.entities.dinofelis.Dinofelis;
import com.dragn0007.permafrost.entities.dinofelis.DinofelisModel;
import com.dragn0007.permafrost.entities.direwolf.Direwolf;
import com.dragn0007.permafrost.entities.direwolf.DirewolfModel;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import com.dragn0007.permafrost.entities.mammoth.MammothModel;
import com.dragn0007.permafrost.entities.paraceratherium.Paraceratherium;
import com.dragn0007.permafrost.entities.paraceratherium.ParaceratheriumModel;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import com.dragn0007.permafrost.entities.titanis.Titanis;
import com.dragn0007.permafrost.entities.titanis.TitanisModel;
import com.dragn0007.permafrost.util.PermafrostCommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Permafrost.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModdedSpawnInjector {

    /*
    Takes animals and spawns mine on top of them to give the effect that they're spawning naturally.

    TFC spawning is complex and I don't feel like figuring it out because im stupid
     */

    @SubscribeEvent
    public static void onModdedSpawn(EntityJoinLevelEvent event) {

        Random random = new Random();

        if (ModList.get().isLoaded("tfc")) {

            if (PermafrostCommonConfig.SPAWN_MAMMOTHS.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "musk_ox"))) {
                Entity entity = event.getEntity();
                Mammoth permafrostAnimal = EntityTypes.MAMMOTH_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) { //spawn a Mammoth alongside oxen 30% of the time
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(MammothModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_MAMMOTHS.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "alpaca"))) {
                Entity entity = event.getEntity();
                Mammoth permafrostAnimal = EntityTypes.MAMMOTH_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(MammothModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_DEINOTHERIUM.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "horse"))) {
                Entity entity = event.getEntity();
                Deinotherium permafrostAnimal = EntityTypes.DEINOTHERIUM_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) {
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(DeinotheriumModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_DEINOTHERIUM.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "cow"))) {
                Entity entity = event.getEntity();
                Deinotherium permafrostAnimal = EntityTypes.DEINOTHERIUM_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) {
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(DeinotheriumModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_DIREWOLVES.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "direwolf"))) {
                Entity entity = event.getEntity();
                Direwolf permafrostAnimal = EntityTypes.DIREWOLF_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null) {
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(DirewolfModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(Direwolf.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_QUAGGA.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "horse"))) {
                Entity entity = event.getEntity();
                Quagga permafrostAnimal = EntityTypes.QUAGGA_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.50) {
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    permafrostAnimal.setGender(random.nextInt(AbstractOMount.Gender.values().length));

                    permafrostAnimal.setManeType(2);
                    permafrostAnimal.setTailType(1 + random.nextInt(4));

                    if (LivestockOverhaulCommonConfig.SPAWN_BY_BREED.get()) {
                        permafrostAnimal.setColorByBreed();
                        permafrostAnimal.setMarkingByBreed();
                    } else {
                        permafrostAnimal.setVariant(random.nextInt(OHorseModel.Variant.values().length));
                        permafrostAnimal.setOverlayVariant(random.nextInt(EquineMarkingOverlay.values().length));
                    }

                    if (LivestockOverhaulCommonConfig.EYES_BY_COLOR.get()) {
                        permafrostAnimal.setEyeColorByChance();
                    } else {
                        permafrostAnimal.setEyeVariant(random.nextInt(EquineEyeColorOverlay.values().length));
                    }

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_CERVALCES.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "deer"))) {
                Entity entity = event.getEntity();
                Cervalces permafrostAnimal = EntityTypes.CERVALCES_LATIFRONS_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.25) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(CervalcesModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_CERVALCES.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "yak"))) {
                Entity entity = event.getEntity();
                Paraceratherium permafrostAnimal = EntityTypes.PARACERATHERIUM_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.40) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(ParaceratheriumModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_DINOFELIS.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "sabertooth"))) {
                Entity entity = event.getEntity();
                Dinofelis permafrostAnimal = EntityTypes.DINOFELIS_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(DinofelisModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_TITANIS.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "turkey"))) {
                Entity entity = event.getEntity();
                Titanis permafrostAnimal = EntityTypes.TITANIS_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(TitanisModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

            if (PermafrostCommonConfig.SPAWN_AUROCHS.get() && ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "cow"))) {
                Entity entity = event.getEntity();
                Aurochs permafrostAnimal = EntityTypes.AUROCHS_ENTITY.get().create(event.getLevel());
                if (permafrostAnimal != null && event.getLevel().getRandom().nextDouble() < 0.30) {
                    permafrostAnimal.copyPosition(entity);
                    permafrostAnimal.copyPosition(entity);
                    event.getLevel().addFreshEntity(permafrostAnimal);

                    int randomVariant = event.getLevel().getRandom().nextInt(AurochsModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }

        }
    }
}