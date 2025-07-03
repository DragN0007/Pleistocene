package com.dragn0007.permafrost.entities.direwolf;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.aurochs.Aurochs;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DirewolfModel extends DefaultedEntityGeoModel<Direwolf> {

    public DirewolfModel() {
        super(new ResourceLocation(Permafrost.MODID, "direwolf"), true);
    }

    @Override
    public void setCustomAnimations(Direwolf animatable, long instanceId, AnimationState<Direwolf> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        CoreGeoBone left_ear = getAnimationProcessor().getBone("left_ear");
        CoreGeoBone right_ear = getAnimationProcessor().getBone("right_ear");
        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (neck != null) {
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }

        if (head != null) {
            head.setRotX(head.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            head.setRotY(head.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_black.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_brown.png")),
        SILVER(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_silver.png")),
        RED(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_red.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/direwolf/direwolf_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/direwolf.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/direwolf.animation.json");

    @Override
    public ResourceLocation getModelResource(Direwolf object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Direwolf object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Direwolf animatable) {
        return ANIMATION;
    }
}

