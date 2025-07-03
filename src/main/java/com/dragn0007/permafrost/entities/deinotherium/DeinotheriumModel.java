package com.dragn0007.permafrost.entities.deinotherium;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DeinotheriumModel extends DefaultedEntityGeoModel<Deinotherium> {

    public DeinotheriumModel() {
        super(new ResourceLocation(Permafrost.MODID, "deinotherium"), true);
    }

    @Override
    public void setCustomAnimations(Deinotherium animatable, long instanceId, AnimationState<Deinotherium> animationState) {

        CoreGeoBone neck = getAnimationProcessor().getBone("neck");

        if (neck != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            neck.setRotX(neck.getRotX() + (entityData.headPitch() * Mth.DEG_TO_RAD));
            float maxYaw = Mth.clamp(entityData.netHeadYaw(), -25.0f, 25.0f);
            neck.setRotY(neck.getRotY() + (maxYaw * Mth.DEG_TO_RAD));
        }
    }

    public enum Variant {
        BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_black.png")),
        BLUE(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_blue.png")),
        BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_brown.png")),
        GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_grey.png")),
        WHITE(new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/deinotherium_white.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation MODEL = new ResourceLocation(Permafrost.MODID, "geo/deinotherium.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(Permafrost.MODID, "animations/mammoth.animation.json");

    @Override
    public ResourceLocation getModelResource(Deinotherium object) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(Deinotherium object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Deinotherium animatable) {
        return ANIMATION;
    }
}

