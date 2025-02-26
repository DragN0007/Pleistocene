package com.dragn0007.permafrost.entities.quagga;

import com.dragn0007.dragnlivestock.LivestockOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class QuaggaMarkingLayer extends GeoRenderLayer<Quagga> {
    public QuaggaMarkingLayer(GeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Quagga animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType renderMarkingType = RenderType.entityCutout(((Quagga)animatable).getOverlayLocation());
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
        NONE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_none.png")),
        APPALOOSA(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_appaloosa.png")),
        BALD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_bald.png")),
        BLANKET_APPALOOSA(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_blanket_appaloosa.png")),
        BLAZE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_blaze.png")),
        BLIND_EYE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_blind_eye.png")),
        BLIND_EYES(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_blind_eyes.png")),
        BLUE_EYE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_blue_eye.png")),
        BROWN_EYE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_brown_eye.png")),
        FLEABITTEN(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_fleabitten.png")),
        FULL_SOCKS(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_full_socks.png")),
        FULL_SOCKS_FEATHERING(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_full_socks_feathering.png")),
        GOLD_EYE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_gold_eye.png")),
        HALF_SOCKS(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_half_socks.png")),
        HALF_SOCKS_FEATHERING(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_half_socks_feathering.png")),
        OVERO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_overo.png")),
        OVERO_SPLASH(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_overo_splash.png")),
        PAINT(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_paint.png")),
        PINK_BELLY(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_belly.png")),
        REVERSED_HALF_SOCKS(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_reversed_half_socks.png")),
        REVERSED_FULL_SOCKS(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_reversed_full_socks.png")),
        REVERSED_FULL_SOCKS_FEATHERING(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_reversed_full_socks_feathering.png")),
        REVERSED_HALF_SOCKS_FEATHERING(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_reversed_half_socks_feathering.png")),
        ROAN(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_roan.png")),
        SNIP(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_snip.png")),
        SPLASH_OVERO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_splash_overo.png")),
        SPLASHED(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_splashed.png")),
        SPLASHED_PAINT(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_splashed_paint.png")),
        SPOTTED(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_spotted.png")),
        STAR(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_star.png")),
        TOBIANO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_tobiano.png")),
        ZORSE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_zorse.png")),
        HALF_SILVER(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_half_silver.png")),
        FULL_SILVER(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_full_silver.png")),

        CORONET(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_coronet.png")),
        FEW_SPOT_LEOPARD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_few_spot_leopard.png")),
        LEOPARD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_leopard.png")),
        PURE_WHITE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pure_white.png")),
        RABICANO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_rabicano.png")),
        SNOWCAP(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_snowcap.png")),
        HALF_BALD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_half_bald.png")),
        PINK_NOSE_APPALOOSA(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_appaloosa.png")),
        PINK_NOSE_BALD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_bald.png")),
        PINK_NOSE_BLAZE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_blaze.png")),
        PINK_NOSE_FEW_SPOT_LEOPARD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_few_spot_leopard.png")),
        PINK_NOSE_HALF_BALD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_half_bald.png")),
        PINK_NOSE_LEOPARD(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_leopard.png")),
        PINK_NOSE_OVERO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_overo.png")),
        PINK_NOSE_PURE_WHITE(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_pure_white.png")),
        PINK_NOSE_SNIP(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_snip.png")),
        PINK_NOSE_SPLASH_OVERO(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_splash_overo.png")),
        PINK_NOSE_SPOTTED(new ResourceLocation(LivestockOverhaul.MODID, "textures/entity/horse/overlay/overlay_pink_spotted.png"));

        public final ResourceLocation resourceLocation;
        Overlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Overlay overlayFromOrdinal(int overlay) { return Overlay.values()[overlay % Overlay.values().length];
        }
    }

}
