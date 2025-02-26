package com.dragn0007.permafrost.world;

import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.dragn0007.permafrost.entities.deinotherium.DeinotheriumModel;
import com.dragn0007.permafrost.entities.direwolf.Direwolf;
import com.dragn0007.permafrost.entities.direwolf.DirewolfModel;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import com.dragn0007.permafrost.entities.mammoth.MammothModel;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import com.dragn0007.permafrost.entities.quagga.QuaggaModel;
import com.dragn0007.permafrost.util.PermafrostCommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Permafrost.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModdedSpawnInjector {

    /*
    Takes animals and spawns mine on top of them to give the effect that they're spawning naturally.

    TFC spawning is complex and I don't feel like figuring it out because im stupid
     */

    @SubscribeEvent
    public static void onModdedSpawn(EntityJoinLevelEvent event) {

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

                    int randomVariant = event.getLevel().getRandom().nextInt(QuaggaModel.Variant.values().length);
                    permafrostAnimal.setVariant(randomVariant);

                    int randomGender = event.getLevel().getRandom().nextInt(AbstractOMount.Gender.values().length);
                    permafrostAnimal.setGender(randomGender);

                    event.setCanceled(true);
                }
            }



        }
    }
}