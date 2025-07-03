package com.dragn0007.permafrost.entities.mammoth;

import com.dragn0007.permafrost.Permafrost;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@OnlyIn(Dist.CLIENT)
public class MammothCarpetLayer extends GeoRenderLayer<Mammoth> {
    public static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[]{
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/white.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/orange.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/magenta.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/light_blue.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/yellow.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/lime.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/pink.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/grey.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/light_grey.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/cyan.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/purple.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/blue.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/brown.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/green.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/red.png"),
            new ResourceLocation(Permafrost.MODID, "textures/entity/mammoth/carpet/black.png")
    };

    public MammothCarpetLayer(GeoRenderer<Mammoth> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, Mammoth animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        ItemStack itemStack = animatable.getDecorItem();

        ResourceLocation resourceLocation = null;

        if(!itemStack.isEmpty()) {
            if (itemStack.is(ItemTags.WOOL_CARPETS)) {
                resourceLocation = MammothCarpetLayer.TEXTURE_LOCATION[((WoolCarpetBlock) Block.byItem(itemStack.getItem())).getColor().getId()];
            }
        }

        if(resourceLocation != null) {
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
}
