package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
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
    private final boolean shader;

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture) {
        this(entityRendererIn, glowTexture, true);
    }

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture, boolean shader) {
        super(entityRendererIn);
        this.glow = RenderType.eyes(glowTexture);
        this.shader = shader;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = shader ? bufferSource.getBuffer(glow) : buffer;
        getRenderer().reRender(
            getDefaultBakedModel(animatable),
            poseStack, bufferSource, animatable,
            glow, vertexConsumer,
            partialTick,
            LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
            0.65f, 0.65f, 0.65f, 1
        );
    }
}
