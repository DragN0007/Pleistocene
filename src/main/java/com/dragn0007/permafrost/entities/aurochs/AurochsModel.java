package com.dragn0007.permafrost.entities.aurochs;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.dragn0007.dragnlivestock.entities.cow.OCow;
import com.dragn0007.permafrost.Permafrost;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class AurochsModel extends DefaultedEntityGeoModel<Aurochs> {

    public AurochsModel() {
        super(new ResourceLocation(Permafrost.MODID, "aurochs"), true);
    }

    @Override
    public void setCustomAnimations(Aurochs animatable, long instanceId, AnimationState<Aurochs> animationState) {

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

