package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.EnderBullet;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class EnderBulletRenderer extends GeoEntityRenderer<EnderBullet> {

    public EnderBulletRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntityTypes.ENDER_BULLET.get())));
    }
}
