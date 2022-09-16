package io.github.padlocks.EndermanOverhaul.client.render.entity;

import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

import javax.annotation.Nullable;

public class EndermanGlowLayer<E extends BaseEnderman> extends GeoLayerRenderer<E> {
    public EndermanGlowLayer(IGeoRenderer<E> entityRendererIn) {
        super(entityRendererIn);
    }

    public RenderLayer getRenderType(Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(textureLocation);
    }

    @Override
    public void render(MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn, E enderman, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        EndermanType type = enderman.getEndermanType();
        GeoModel normalModel = this.getEntityModel().getModel(type.model());
        VertexConsumer glowConsumer = bufferIn.getBuffer(getRenderType(type.glowingTexture()));

            getRenderer().render(normalModel, enderman, partialTicks,
                    null, matrixStackIn, null, glowConsumer,
                    packedLightIn, OverlayTexture.DEFAULT_UV,
                    1f, 1f, 1f, 1f);
    }
}
