package com.dragn0007.permafrost.gui;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PFMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Permafrost.MODID);

    public static final RegistryObject<MenuType<MammothMenu>> MAMMOTH_MENU = registerMenuType("mammoth_menu", MammothMenu::new);
    public static final RegistryObject<MenuType<DeinotheriumMenu>> DEINOTHERIUM_MENU = registerMenuType("deinotherium_menu", DeinotheriumMenu::new);

    public static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
