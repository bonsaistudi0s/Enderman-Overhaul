package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CustomEnderEyesLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType glow;

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture) {
        super(entityRendererIn);
        this.glow = RenderType.eyes(glowTexture);
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(glow);
        getRenderer().reRender(
            getDefaultBakedModel(animatable),
            poseStack, bufferSource, animatable,
            glow, vertexConsumer,
            partialTick,
            packedLight, OverlayTexture.NO_OVERLAY,
            1, 1, 1, 1
        );
    }
}
