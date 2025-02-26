package com.dragn0007.permafrost.entities.deinotherium;

import com.dragn0007.permafrost.Permafrost;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class DeinotheriumCarpetLayer extends GeoRenderLayer<Deinotherium> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/white.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/orange.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/magenta.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/light_blue.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/yellow.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/lime.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/pink.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/grey.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/light_grey.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/cyan.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/purple.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/blue.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/brown.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/green.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/red.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/deinotherium/carpet/black.png")
    };

    public DeinotheriumCarpetLayer(GeoRenderer<Deinotherium> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Deinotherium animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        DyeColor dyeColor = animatable.getCarpet();
        ResourceLocation resourceLocation = null;

        if (dyeColor != null) {
            resourceLocation = TEXTURE_LOCATION[dyeColor.getId()];
        }

        if (resourceLocation == null) {
            return;
        }

        RenderType renderType1 = RenderType.entityCutout(resourceLocation);
        poseStack.pushPose();
        poseStack.scale(1.0f, 1.0f, 1.0f);
        poseStack.translate(0.0d, 0.0d, 0.0d);
        poseStack.popPose();
        getRenderer().reRender(getDefaultBakedModel(animatable),
                poseStack,
                bufferSource,
                animatable,
                renderType1,
                bufferSource.getBuffer(renderType1), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                1, 1, 1, 1);
    }
}
