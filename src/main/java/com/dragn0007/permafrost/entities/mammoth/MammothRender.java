package com.dragn0007.permafrost.entities.mammoth;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MammothRender extends GeoEntityRenderer<Mammoth> {

    public MammothRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MammothModel());
        this.addRenderLayer(new MammothCarpetLayer(this));
        this.addRenderLayer(new MammothSaddleLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, Mammoth entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

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

        if (!entity.isBaby() && entity.isFemale()) {
            model.getBone("tusks").ifPresent(b -> b.setScaleX(0.7F));
            model.getBone("tusks").ifPresent(b -> b.setScaleY(0.7F));
            model.getBone("tusks").ifPresent(b -> b.setScaleZ(0.7F));
        }

        if (!entity.isBaby() && entity.isMale()) {
            model.getBone("tusks").ifPresent(b -> b.setScaleX(1F));
            model.getBone("tusks").ifPresent(b -> b.setScaleY(1F));
            model.getBone("tusks").ifPresent(b -> b.setScaleZ(1F));
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

}


