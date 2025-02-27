package com.dragn0007.permafrost.entities.paraceratherium;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ParaceratheriumModel extends GeoModel<Paraceratherium> {

    public enum Variant {
        TAN(new ResourceLocation(Permafrost.MODID, "textures/entity/paraceratherium/paraceratherium_tan.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/paraceratherium/paraceratherium_grey.png")),
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/paraceratherium/paraceratherium_black.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/paraceratherium/paraceratherium_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/paraceratherium.animation.json");
    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/paraceratherium.geo.json");

    @Override
    public ResourceLocation getModelResource(Paraceratherium object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Paraceratherium object) {
        return object.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(Paraceratherium animatable) {
        return ANIMATION;
    }
}

