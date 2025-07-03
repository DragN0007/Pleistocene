package com.dragn0007.permafrost.entities.titanis;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class TitanisModel extends DefaultedEntityGeoModel<Titanis> {

    public TitanisModel() {
        super(new ResourceLocation(Permafrost.MODID, "titanis"), true);
    }

    @Override
    public void setCustomAnimations(Titanis animatable, long instanceId, AnimationState<Titanis> animationState) {

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

