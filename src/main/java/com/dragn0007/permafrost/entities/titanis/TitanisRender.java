package com.dragn0007.permafrost.entities.titanis;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TitanisRender extends GeoEntityRenderer<Titanis> {

    public TitanisRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TitanisModel());
    }

    @Override
    public void preRender(PoseStack poseStack, Titanis entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if(entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        if(!entity.isBaby() && entity.isMale()) {
            model.getBone("crest").ifPresent(b -> b.setHidden(false));
        }

        if(entity.isBaby() || entity.isFemale()) {
            model.getBone("crest").ifPresent(b -> b.setHidden(true));
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}


