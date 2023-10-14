package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.ReplacedEndermanRenderer;

public class ReplacedEndermanEnderEyesLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private final RenderType glow;
    private final boolean shader;


    public ReplacedEndermanEnderEyesLayer(IGeoRenderer<T> entityRendererIn, ResourceLocation glowTexture) {
        this(entityRendererIn, glowTexture, true);
    }

    public ReplacedEndermanEnderEyesLayer(IGeoRenderer<T> entityRendererIn, ResourceLocation glowTexture, boolean shader) {
        super(entityRendererIn);
        this.glow = RenderType.eyes(glowTexture);
        this.shader = shader;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLightIn, T animatable, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer vertexConsumer = shader ? bufferSource.getBuffer(glow) :
            bufferSource.getBuffer(getRenderer().getRenderType(animatable, partialTick, poseStack, bufferSource, null, packedLightIn, getEntityModel().getTextureResource(animatable)));

        getRenderer().render(
            getEntityModel().getModel(ReplacedEndermanRenderer.MODEL),
            animatable,
            partialTick,
            glow,
            poseStack,
            bufferSource,
            vertexConsumer,
            LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,
            0.65f, 0.65f, 0.65f, 1);
    }
}
