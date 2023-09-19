package tech.alexnijjar.endermanoverhaul.client.renderer.replaced;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.ReplacedEnderman;

public class ReplacedEndermanModel extends GeoModel<ReplacedEnderman> {
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "animations/entity/enderman.animation.json");
    public static final ResourceLocation MODEL = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/entity/default_enderman.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman.png");

    @Override
    public ResourceLocation getModelResource(ReplacedEnderman animatable) {
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ReplacedEnderman animatable) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(ReplacedEnderman animatable) {
        return ANIMATION;
    }
}
