package com.dragn0007.permafrost.entities.deinotherium;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DeinotheriumRender extends GeoEntityRenderer<Deinotherium> {

    public DeinotheriumRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DeinotheriumModel());
        this.addRenderLayer(new DeinotheriumSaddleLayer(this));
        this.addRenderLayer(new DeinotheriumCarpetLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, Deinotherium entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if (!entity.isBaby()) {
            if (entity.hasChest()) {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(true));
            }

            if (entity.isSaddled()) {
                model.getBone("saddle").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("saddle").ifPresent(b -> b.setHidden(true));
            }
        }

        if(entity.isBaby()) {
            model.getBone("tusks").ifPresent(b -> b.setHidden(true));
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            model.getBone("tusks").ifPresent(b -> b.setHidden(false));
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}


