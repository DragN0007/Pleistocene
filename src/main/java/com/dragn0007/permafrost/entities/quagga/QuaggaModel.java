package com.dragn0007.permafrost.entities.quagga;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class QuaggaModel extends DefaultedEntityGeoModel<Quagga> {

    public QuaggaModel() {
        super(new ResourceLocation(Permafrost.MODID, "quagga"), true);
    }

    @Override
    public void setCustomAnimations(Quagga animatable, long instanceId, AnimationState<Quagga> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public static final ResourceLocation ANIMATION = new ResourceLocation(LivestockOverhaul.MODID, "animations/o_horse.animation.json");
    public static final ResourceLocation BABY_MODEL = new ResourceLocation(LivestockOverhaul.MODID, "geo/baby_o_horse.geo.json");
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

