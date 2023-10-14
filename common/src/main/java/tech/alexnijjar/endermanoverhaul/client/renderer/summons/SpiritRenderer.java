package tech.alexnijjar.endermanoverhaul.client.renderer.summons;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Spirit;

public class SpiritRenderer extends GeoEntityRenderer<Spirit> {
    public static final ResourceLocation ASSET_SUBPATH = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/entity/spirit.geo.json");
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "animations/entity/spirit.animation.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/spirit/spirit.png");
    public static final ResourceLocation GLOW = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/spirit/spirit_glow.png");

    public SpiritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SpiritModel());
        addLayer(new CustomEnderEyesLayer<>(this, GLOW, false));
        addLayer(new CustomEnderEyesLayer<>(this, GLOW, true));
    }

    private static class SpiritModel extends AnimatedGeoModel<Spirit> {
        @Override
        public ResourceLocation getAnimationResource(Spirit entity) {
            return ANIMATION;
        }

        @Override
        public ResourceLocation getModelResource(Spirit entity) {
            return ASSET_SUBPATH;
        }

        @Override
        public ResourceLocation getTextureResource(Spirit entity) {
            return TEXTURE;
        }
    }
}
