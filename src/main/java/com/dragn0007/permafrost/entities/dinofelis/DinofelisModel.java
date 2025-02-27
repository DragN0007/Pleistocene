package com.dragn0007.permafrost.entities.dinofelis;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DinofelisModel extends GeoModel<Dinofelis> {

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_black.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_brown.png")),
        BLUE(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_blue.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_grey.png")),
        ORANGE(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_orange.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/dinofelis/dinofelis_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/dinofelis.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/dinofelis.animation.json");

    @Override
    public ResourceLocation getModelResource(Dinofelis object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Dinofelis object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Dinofelis animatable) {
        return ANIMATION;
    }
}

