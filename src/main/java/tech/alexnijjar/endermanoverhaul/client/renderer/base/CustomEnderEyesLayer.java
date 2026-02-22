package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class CustomEnderEyesLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final RenderType glow;
    private final boolean shader;

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture) {
        this(entityRendererIn, glowTexture, true, false);
    }

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture, boolean shader) {
        this(entityRendererIn, glowTexture, shader, false);
    }

    public CustomEnderEyesLayer(GeoRenderer<T> entityRendererIn, ResourceLocation glowTexture, boolean shader, boolean noCull) {
        super(entityRendererIn);
        this.glow = noCull ? EyesNoCull.create(glowTexture) : RenderType.eyes(glowTexture);
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
            0xA6A6A6FF
        );
    }

    private static class EyesNoCull extends RenderStateShard {
        private static final String RENDER_TYPE_NAME = "%s:eyes_no_cull".formatted(EndermanOverhaul.MOD_ID);

        private EyesNoCull() { super("", () -> {}, () -> {}); }

        static RenderType create(ResourceLocation texture) {
            return RenderType.create(RENDER_TYPE_NAME,
                DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder()
                    .setShaderState(RENDERTYPE_EYES_SHADER)
                    .setTextureState(new TextureStateShard(texture, false, false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setCullState(NO_CULL)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));
        }
    }
}
