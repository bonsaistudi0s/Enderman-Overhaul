package tech.alexnijjar.endermanoverhaul.client.renderer.summons;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Spirit;

public class SpiritRenderer extends GeoEntityRenderer<Spirit> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "spirit/spirit");
        public static final ResourceLocation GLOW = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/spirit/spirit_glow.png");

    public SpiritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<Spirit>(new ResourceLocation(EndermanOverhaul.MOD_ID, "spirit"))
            .withAltTexture(TEXTURE)
            .withAltAnimations(new ResourceLocation(EndermanOverhaul.MOD_ID, "spirit")));
        addRenderLayer(new CustomEnderEyesLayer<>(this, GLOW, false));
        addRenderLayer(new CustomEnderEyesLayer<>(this, GLOW, true));
    }
}
