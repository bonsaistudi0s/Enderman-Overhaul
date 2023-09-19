package tech.alexnijjar.endermanoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.ReplacedEnderman;

public class ReplacedEnderEyesLayer extends GeoRenderLayer<ReplacedEnderman> {
    private static final RenderType ENDERMAN_EYES = RenderType.eyes(new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman_glow.png"));

    public ReplacedEnderEyesLayer(GeoRenderer<ReplacedEnderman> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, ReplacedEnderman animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        VertexConsumer vertexConsumer = bufferSource.getBuffer(ENDERMAN_EYES);
        getRenderer().reRender(
            getDefaultBakedModel(animatable),
            poseStack, bufferSource, animatable,
            ENDERMAN_EYES, vertexConsumer,
            partialTick,
            packedLight, OverlayTexture.NO_OVERLAY,
            1, 1, 1, 1
        );
    }
}
