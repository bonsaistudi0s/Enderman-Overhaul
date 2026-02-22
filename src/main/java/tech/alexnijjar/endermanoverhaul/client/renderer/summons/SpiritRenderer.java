package tech.alexnijjar.endermanoverhaul.client.renderer.summons;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Spirit;

public class SpiritRenderer extends GeoEntityRenderer<Spirit> {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EndermanOverhaul.MOD_ID, "spirit/spirit");
    public static final ResourceLocation GLOW = ResourceLocation.fromNamespaceAndPath(EndermanOverhaul.MOD_ID, "textures/entity/spirit/spirit_glow.png");

    public SpiritRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<Spirit>(ResourceLocation.fromNamespaceAndPath(EndermanOverhaul.MOD_ID, "spirit"))
            .withAltTexture(TEXTURE)
            .withAltAnimations(ResourceLocation.fromNamespaceAndPath(EndermanOverhaul.MOD_ID, "spirit")));
        addRenderLayer(new CustomEnderEyesLayer<>(this, GLOW, false, true));
        addRenderLayer(new CustomEnderEyesLayer<>(this, GLOW, true, true));
    }
}
