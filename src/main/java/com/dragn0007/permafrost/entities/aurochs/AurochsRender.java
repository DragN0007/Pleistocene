package com.dragn0007.permafrost.entities.aurochs;

import com.dragn0007.dragnlivestock.util.LivestockOverhaulClientConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AurochsRender extends GeoEntityRenderer<Aurochs> {

    public AurochsRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AurochsModel());
        this.addRenderLayer(new AurochsSaddleLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, Aurochs entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if (!entity.isBaby()) {
            if (entity.hasChest()) {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(false));
            } else {
                model.getBone("saddlebags").ifPresent(b -> b.setHidden(true));
            }
        }

        if (entity.isBaby()) {
            model.getBone("saddlebags").ifPresent(b -> b.setHidden(true));
            model.getBone("horns").ifPresent(b -> b.setHidden(true));
            poseStack.scale(0.5F, 0.5F, 0.5F);
        } else {
            poseStack.scale(1.0F, 1.0F, 1.0F);
        }

        if (entity.isBaby() || entity.isFemale()) {
            model.getBone("horns").ifPresent(b -> b.setScaleX(0.7F));
            model.getBone("horns").ifPresent(b -> b.setScaleY(0.7F));
            model.getBone("horns").ifPresent(b -> b.setScaleZ(0.7F));
        }

        if (!entity.isBaby() && entity.isMale()) {
            model.getBone("horns").ifPresent(b -> b.setScaleX(1.0F));
            model.getBone("horns").ifPresent(b -> b.setScaleY(1.0F));
            model.getBone("horns").ifPresent(b -> b.setScaleZ(1.0F));
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }
}