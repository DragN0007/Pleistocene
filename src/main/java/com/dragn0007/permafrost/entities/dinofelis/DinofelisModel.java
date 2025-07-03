package com.dragn0007.permafrost.entities.dinofelis;

import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DinofelisModel extends DefaultedEntityGeoModel<Dinofelis> {

    public DinofelisModel() {
        super(new ResourceLocation(Permafrost.MODID, "dinofelis"), true);
    }

    @Override
    public void setCustomAnimations(Dinofelis animatable, long instanceId, AnimationState<Dinofelis> animationState) {

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

