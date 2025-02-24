package com.dragn0007.permafrost.world;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
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
    Takes TFC animals and spawns mine on top of them to give the effect that they're spawning naturally.

    TFC spawning is complex and I don't feel like figuring it out because im stupid
     */

    @SubscribeEvent
    public static void onModdedSpawn(EntityJoinLevelEvent event) {

        if (ModList.get().isLoaded("tfc")) {

            if (ForgeRegistries.ENTITY_TYPES.getKey(event.getEntity().getType()).equals(new ResourceLocation("tfc", "deer"))) {
                Entity entity = event.getEntity();
                Mammoth mammoth = EntityTypes.MAMMOTH_ENTITY.get().create(event.getLevel());
                if (mammoth != null && event.getLevel().getRandom().nextDouble() < 0.25) { //spawn a Mammoth alongside a deer 25% of the time
                    mammoth.copyPosition(entity);
                    event.getLevel().addFreshEntity(mammoth);
                    event.setCanceled(true);
                }
            }

        }
    }
}