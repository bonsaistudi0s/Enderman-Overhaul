package tech.alexnijjar.endermanoverhaul.client.renderer.summons;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Scarab;

public class ScarabRenderer extends GeoEntityRenderer<Scarab> {
    public static final ResourceLocation MODEL = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/entity/scarab.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "animations/entity/scarab.animation.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/scarab/scarab.png");

    public ScarabRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScarabModel());
    }

    private static class ScarabModel extends AnimatedGeoModel<Scarab> {
        @Override
        public ResourceLocation getAnimationResource(Scarab entity) {
            return ANIMATION;
        }

        @Override
        public ResourceLocation getModelResource(Scarab entity) {
            return MODEL;
        }

        @Override
        public ResourceLocation getTextureResource(Scarab entity) {
            return TEXTURE;
        }
    }
}
