package com.dragn0007.permafrost.entities.direwolf;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DirewolfModel extends GeoModel<Direwolf> {

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_black.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_brown.png")),
        SILVER(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_silver.png")),
        RED(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_red.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/direwolf.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/direwolf.animation.json");

    @Override
    public ResourceLocation getModelResource(Direwolf object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Direwolf object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Direwolf animatable) {
        return ANIMATION;
    }
}

