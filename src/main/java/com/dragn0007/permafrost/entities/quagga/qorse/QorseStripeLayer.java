package com.dragn0007.permafrost.entities.quagga.qorse;

import com.dragn0007.permafrost.Permafrost;
import com.dragn0007.permafrost.entities.quagga.Quagga;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class QorseStripeLayer extends GeoRenderLayer<Qorse> {
    public QorseStripeLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Qorse animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {

        RenderType renderMarkingType = RenderType.entityCutout((animatable).getStripeTextureResource());
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderMarkingType,
                bufferSource.getBuffer(renderMarkingType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }

    public enum Overlay {
        CREAM_BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/cream_black.png")),
        CREAM_CHESTNUT(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/cream_chestnut.png")),
        CREAM_DARK_BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/cream_dark_brown.png")),
        CREAM_GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/cream_grey.png")),
        WHITE_BLACK(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/white_black.png")),
        WHITE_CREAM(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/white_cream.png")),
        WHITE_DARK_BROWN(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/white_dark_brown.png")),
        WHITE_GREY(new ResourceLocation(Permafrost.MODID, "textures/entity/quagga/white_grey.png")),
        ;

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
