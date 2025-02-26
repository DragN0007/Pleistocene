package com.dragn0007.permafrost.event;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.entities.deinotherium.Deinotherium;
import com.dragn0007.permafrost.entities.deinotherium.DeinotheriumRender;
import com.dragn0007.permafrost.entities.direwolf.Direwolf;
import com.dragn0007.permafrost.entities.direwolf.DirewolfRender;
import com.dragn0007.permafrost.entities.mammoth.Mammoth;
import com.dragn0007.permafrost.entities.mammoth.MammothRender;
import com.dragn0007.permafrost.gui.DeinotheriumScreen;
import com.dragn0007.permafrost.gui.MammothScreen;
import com.dragn0007.permafrost.gui.PFMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = Permafrost.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PermafrostEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityTypes.MAMMOTH_ENTITY.get(), Mammoth.createAttributes().build());
        event.put(EntityTypes.DEINOTHERIUM_ENTITY.get(), Deinotherium.createAttributes().build());
        event.put(EntityTypes.DIREWOLF_ENTITY.get(), Direwolf.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityTypes.MAMMOTH_ENTITY.get(), MammothRender::new);
        EntityRenderers.register(EntityTypes.DEINOTHERIUM_ENTITY.get(), DeinotheriumRender::new);
        EntityRenderers.register(EntityTypes.DIREWOLF_ENTITY.get(), DirewolfRender::new);

        MenuScreens.register(PFMenuTypes.MAMMOTH_MENU.get(), MammothScreen::new);
        MenuScreens.register(PFMenuTypes.DEINOTHERIUM_MENU.get(), DeinotheriumScreen::new);
    }

    @SubscribeEvent
    public static void spawnPlacementRegisterEvent(SpawnPlacementRegisterEvent event) {
        event.register(EntityTypes.MAMMOTH_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(EntityTypes.DEINOTHERIUM_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
        event.register(EntityTypes.DIREWOLF_ENTITY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }

}