package com.dragn0007.permafrost.entities.quagga;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class QuaggaModel extends GeoModel<Quagga> {

    public enum Variant {
        BUCKSKIN(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_buckskin.png")),
        CHESTNUT(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_chestnut.png")),
        CREAM(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_cream.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_grey.png")),
        GRULLO(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_grullo.png")),
        PEACH(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_peach.png")),
        STRAWBERRY(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_strawberry.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/quagga_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(LivestockOverhaul.MODID, "animations/horse_overhaul.animation.json");
    public static final ResourceLocation BABY_MODEL = new ResourceLocation(LivestockOverhaul.MODID, "geo/baby_horse_overhauled.geo.json");
    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/quagga.geo.json");

    @Override
    public ResourceLocation getModelResource(Quagga object) {
        if (object.isBaby()) {
            return BABY_MODEL;
        }
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Quagga object) {
        return object.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(Quagga animatable) {
        return ANIMATION;
    }
}

