package com.dragn0007.permafrost.entities.mammoth;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

import java.util.HashMap;
import java.util.Map;

public class MammothModel extends GeoModel<Mammoth> {

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_black.png")),
        DARK_BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_dark_brown.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_brown.png")),
        CHESTNUT(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_chestnut.png")),
        TAN(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_tan.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/mammoth_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/mammoth.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/mammoth.animation.json");

    @Override
    public ResourceLocation getModelResource(Mammoth object) {
        return MODEL;
    }

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(Mammoth object) {
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(Mammoth animatable) {
        return ANIMATION;
    }
}

