package com.dragn0007.permafrost.entities.deinotherium;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DeinotheriumModel extends GeoModel<Deinotherium> {

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_black.png")),
        BLUE(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_blue.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_brown.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_grey.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/deinotherium.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/mammoth.animation.json");

    @Override
    public ResourceLocation getModelResource(Deinotherium object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Deinotherium object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Deinotherium animatable) {
        return ANIMATION;
    }
}

