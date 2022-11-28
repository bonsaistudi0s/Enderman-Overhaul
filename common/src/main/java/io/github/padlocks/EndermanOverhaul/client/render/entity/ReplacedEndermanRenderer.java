package io.github.padlocks.EndermanOverhaul.client.render.entity;

import io.github.padlocks.EndermanOverhaul.client.model.entity.ReplacedEndermanModel;
import io.github.padlocks.EndermanOverhaul.common.entity.ReplacedEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
@Environment(EnvType.CLIENT)
public class ReplacedEndermanRenderer extends GeoReplacedEntityRenderer<ReplacedEnderman> {
    public ReplacedEndermanRenderer(EntityRendererFactory.Context context) {
        super(context, new ReplacedEndermanModel(), new ReplacedEnderman());
        EndermanOverhaul.registerReplacedEntity(ReplacedEnderman.class, this);
    }

//    @Override
//    public RenderLayer getRenderType(Object animatable, float partialTicks, MatrixStack stack,
//                                     VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
//                                     Identifier textureLocation) {
//        return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
//    }
}
