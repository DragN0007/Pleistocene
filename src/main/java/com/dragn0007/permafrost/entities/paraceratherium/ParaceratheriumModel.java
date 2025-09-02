package com.dragn0007.permafrost.entities.paraceratherium;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.HashMap;
import java.util.Map;

public class ParaceratheriumModel extends DefaultedEntityGeoModel<Paraceratherium> {

    public ParaceratheriumModel() {
        super(new ResourceLocation(Permafrost.MODID, "paracer"), true);
    }

    @Override
    public void setCustomAnimations(Paraceratherium animatable, long instanceId, AnimationState<Paraceratherium> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

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

    public static final Map<String, ResourceLocation> TEXTURE_CACHE = new HashMap<>();

    @Override
    public ResourceLocation getTextureResource(Paraceratherium object) {
        return TEXTURE_CACHE.computeIfAbsent(object.getTextureResource(), ResourceLocation::tryParse);
    }

    @Override
    public ResourceLocation getAnimationResource(Paraceratherium animatable) {
        return ANIMATION;
    }
}

