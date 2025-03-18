package com.dragn0007.permafrost.entities.aurochs;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AurochsModel extends GeoModel<Aurochs> {

    public enum Variant {
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_brown.png")),
        BLUE(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_blue.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_grey.png")),
        CHESTNUT(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_chestnut.png")),
        ROSE(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_rose.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/aurochs/aurochs_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/aurochs.animation.json");
    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/aurochs.geo.json");

    @Override
    public ResourceLocation getModelResource(Aurochs object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Aurochs object) {
        return object.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(Aurochs animatable) {
        return ANIMATION;
    }
}

