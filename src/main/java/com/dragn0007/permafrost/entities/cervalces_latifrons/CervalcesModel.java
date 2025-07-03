package com.dragn0007.permafrost.entities.cervalces_latifrons;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CervalcesModel extends DefaultedEntityGeoModel<Cervalces> {

    public CervalcesModel() {
        super(new ResourceLocation(Permafrost.MODID, "cervalces"), true);
    }

    @Override
    public void setCustomAnimations(Cervalces animatable, long instanceId, AnimationState<Cervalces> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/cervalces_latifrons/cervalces_brown.png")),
        DARK_BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/cervalces_latifrons/cervalces_dark_brown.png")),
        BAY(new ResourceLocation(Permafrost.MODID, "textures/entity/cervalces_latifrons/cervalces_bay.png")),
        CHESTNUT(new ResourceLocation(Permafrost.MODID, "textures/entity/cervalces_latifrons/cervalces_chestnut.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/cervalces_latifrons/cervalces_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(LivestockOverhaul.MODID, "animations/horse_overhaul.animation.json");
    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/cervalces_latifrons.geo.json");

    @Override
    public ResourceLocation getModelResource(Cervalces object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Cervalces object) {
        return object.getTextureResource();
    }

    @Override
    public ResourceLocation getAnimationResource(Cervalces animatable) {
        return ANIMATION;
    }
}

