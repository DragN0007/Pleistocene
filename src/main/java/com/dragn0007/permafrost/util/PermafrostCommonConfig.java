package com.dragn0007.permafrost.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class PermafrostCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue SPAWN_MAMMOTHS;
    public static final ForgeConfigSpec.BooleanValue SPAWN_DEINOTHERIUM;
    public static final ForgeConfigSpec.BooleanValue SPAWN_DIREWOLVES;
    public static final ForgeConfigSpec.BooleanValue SPAWN_QUAGGA;
    public static final ForgeConfigSpec.BooleanValue SPAWN_CERVALCES;
    public static final ForgeConfigSpec.BooleanValue SPAWN_PARACERATHERIUM;
    public static final ForgeConfigSpec.BooleanValue SPAWN_DINOFELIS;
    public static final ForgeConfigSpec.BooleanValue SPAWN_TITANIS;
    public static final ForgeConfigSpec.BooleanValue SPAWN_AUROCHS;


    static {
        BUILDER.push("Spawning (ONLY APPLIES IF TFC IS INSTALLED, please use a mob spawn controller mod otherwise.)");

        SPAWN_MAMMOTHS = BUILDER.comment("Should Mammoths spawn alongside Musk Ox and Alpacas?")
                .define("Spawn Mammoths", true);

        SPAWN_DEINOTHERIUM = BUILDER.comment("Should Deinotherium spawn alongside Horses and Cows?")
                .define("Spawn Deinotherium", true);

        SPAWN_DIREWOLVES = BUILDER.comment("Should Permafrost Direwolves replace TFC Direwolves?")
                .define("Direwolf Replacer", true);

        SPAWN_QUAGGA = BUILDER.comment("Should Quagga spawn alongside Horses?")
                .define("Spawn Quagga", true);

        SPAWN_CERVALCES = BUILDER.comment("Should Cervalces Latifrons spawn alongside Deer?")
                .define("Spawn Cervalces Latifrons", true);

        SPAWN_PARACERATHERIUM = BUILDER.comment("Should Paraceratherium spawn alongside Yaks?")
                .define("Spawn Paraceratherium", true);

        SPAWN_DINOFELIS = BUILDER.comment("Should Permafrost Dinofelis replace TFC Sabertooths?")
                .define("Dinofelis Replacer", true);

        SPAWN_TITANIS = BUILDER.comment("Should Titanis Walleri spawn alongside Turkeys?")
                .define("Spawn Titanis Walleri", true);

        SPAWN_AUROCHS = BUILDER.comment("Should Titanis Walleri spawn alongside Cows?")
                .define("Spawn Aurochs", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
