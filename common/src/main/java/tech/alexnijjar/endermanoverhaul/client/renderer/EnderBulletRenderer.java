package tech.alexnijjar.endermanoverhaul.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.EnderBullet;

public class EnderBulletRenderer extends GeoProjectilesRenderer<EnderBullet> {
    public static final ResourceLocation MODEL = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/entity/ender_bullet.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/ender_bullet.png");
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "animations/entity/ender_bullet.animation.json");

    public EnderBulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EnderBulletModel());
    }

    @Override
    public RenderType getRenderType(EnderBullet animatable, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucentEmissive(texture);
    }

    private static class EnderBulletModel extends AnimatedGeoModel<EnderBullet> {
        @Override
        public ResourceLocation getAnimationResource(EnderBullet entity) {
            return ANIMATION;
        }

        @Override
        public ResourceLocation getModelResource(EnderBullet entity) {
            return MODEL;
        }

        @Override
        public ResourceLocation getTextureResource(EnderBullet entity) {
            return TEXTURE;
        }
    }
}
