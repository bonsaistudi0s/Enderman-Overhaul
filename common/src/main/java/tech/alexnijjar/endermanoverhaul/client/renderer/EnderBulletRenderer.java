package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.EnderBullet;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class EnderBulletRenderer extends GeoEntityRenderer<EnderBullet> {

    public EnderBulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntityTypes.ENDER_BULLET.get())));
    }

    @Override
    public RenderType getRenderType(EnderBullet animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentEmissive(texture);
    }
}
