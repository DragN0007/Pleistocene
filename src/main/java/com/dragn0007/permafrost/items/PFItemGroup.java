package com.dragn0007.permafrost.items;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PFItemGroup {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Permafrost.MODID);

    public static final RegistryObject<CreativeModeTab> PERMAFROST_GROUP = CREATIVE_MODE_TABS.register("permafrost",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(PFItems.PERMAFROST.get())).title(Component.translatable("itemGroup.permafrost"))
                    .displayItems((displayParameters, output) -> {

                        output.accept(PFItems.MAMMOTH_SPAWN_EGG.get());
                        output.accept(PFItems.DEINOTHERIUM_SPAWN_EGG.get());
                        output.accept(PFItems.DIREWOLF_SPAWN_EGG.get());
                        output.accept(PFItems.QUAGGA_SPAWN_EGG.get());
                        output.accept(PFItems.CERVALCES_SPAWN_EGG.get());
                        output.accept(PFItems.PARACERATHERIUM_SPAWN_EGG.get());
                        output.accept(PFItems.DINOFELIS_SPAWN_EGG.get());
                        output.accept(PFItems.TITANIS_SPAWN_EGG.get());

                        output.accept(PFItems.MAMMOTH.get());
                        output.accept(PFItems.COOKED_MAMMOTH.get());
                        output.accept(PFItems.DEINOTHERIUM.get());
                        output.accept(PFItems.COOKED_DEINOTHERIUM.get());
                        output.accept(PFItems.DIREWOLF.get());
                        output.accept(PFItems.COOKED_DIREWOLF.get());
                        output.accept(PFItems.CERVID.get());
                        output.accept(PFItems.COOKED_CERVID.get());
                        output.accept(PFItems.PARACERATHERIUM.get());
                        output.accept(PFItems.COOKED_PARACERATHERIUM.get());
                        output.accept(PFItems.DINOFELIS.get());
                        output.accept(PFItems.COOKED_DINOFELIS.get());
                        output.accept(PFItems.MAMMOTH_MILK_BUCKET.get());
                        output.accept(PFItems.MAMMOTH_MILK_JUG.get());
                        output.accept(PFItems.MAMMOTH_CHEESE.get());

                        output.accept(PFItems.TITANIS_EGG.get());
                        output.accept(PFItems.FERTILIZED_TITANIS_EGG.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
