package io.github.padlocks.EndermanOverhaul.client.model.armor;

import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BadlandsHoodModel extends AnimatedGeoModel<BadlandsHoodItem> {
    @Override
    public ResourceLocation getModelResource(BadlandsHoodItem object) {
        return EndermanOverhaul.resourceLocation("pinwheel/geometry/badlands_hood.json");
    }

    @Override
    public ResourceLocation getTextureResource(BadlandsHoodItem object) {
        return EndermanOverhaul.resourceLocation("textures/armor/hood/badlands_hood.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BadlandsHoodItem animatable) {
        return null;
    }
}
