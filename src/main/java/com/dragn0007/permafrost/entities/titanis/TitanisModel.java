package com.dragn0007.permafrost.entities.titanis;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TitanisModel extends GeoModel<Titanis> {

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_black.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_brown.png")),
        BLUE(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_blue.png")),
        BURGUNDY(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_burgundy.png")),
        ORANGE(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_orange.png")),
        TAN(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_tan.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/titanis/titanis_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/titanis.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/titanis.animation.json");

    @Override
    public ResourceLocation getModelResource(Titanis object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Titanis object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Titanis animatable) {
        return ANIMATION;
    }
}

