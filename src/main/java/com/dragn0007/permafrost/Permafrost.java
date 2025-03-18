package com.dragn0007.permafrost;

import com.dragn0007.permafrost.blocks.PFBlocks;
import com.dragn0007.permafrost.entities.EntityTypes;
import com.dragn0007.permafrost.gui.PFMenuTypes;
import com.dragn0007.permafrost.items.PFItemGroup;
import com.dragn0007.permafrost.items.PFItems;
import com.dragn0007.permafrost.util.PFSoundEvents;
import com.dragn0007.permafrost.util.PermafrostCommonConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;


@Mod(Permafrost.MODID)
public class Permafrost
{
    public static final String MODID = "permafrost";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Permafrost()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        PFItems.register(eventBus);
        PFItemGroup.register(eventBus);
        PFBlocks.register(eventBus);
        EntityTypes.ENTITY_TYPES.register(eventBus);
        PFMenuTypes.register(eventBus);
        PFSoundEvents.register(eventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PermafrostCommonConfig.SPEC, "permafrost-common.toml");

        if (ModList.get().isLoaded("tfc")) {
            LOGGER.info("[Permafrost] Couldn't find TerraFirmaCraft.");
        } else {
            LOGGER.info("[Permafrost] Found TerraFirmaCraft!");
        }
    }

}