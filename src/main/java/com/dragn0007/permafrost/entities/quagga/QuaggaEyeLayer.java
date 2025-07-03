package com.dragn0007.permafrost.entities.quagga;

import com.dragn0007.dragnlivestock.entities.marking_layer.EquineEyeColorOverlay;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class QuaggaEyeLayer extends GeoRenderLayer<Quagga> {
    public QuaggaEyeLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Quagga animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        if (animatable.getDecompVariant() > 0) {
            return;
        }

        EquineEyeColorOverlay eyes = EquineEyeColorOverlay.eyesFromOrdinal(animatable.getEyeVariant());
        RenderType renderEyeType = RenderType.entityCutout(eyes.resourceLocation);

        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderEyeType,
                bufferSource.getBuffer(renderEyeType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

}
