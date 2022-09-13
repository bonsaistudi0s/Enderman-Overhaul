package io.github.padlocks.EndermanOverhaul.client.model.armor;

import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BadlandsHoodModel extends AnimatedGeoModel<BadlandsHoodItem> {
    @Override
    public Identifier getModelResource(BadlandsHoodItem object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "pinwheel/geometry/badlands_hood.json");
    }

    @Override
    public Identifier getTextureResource(BadlandsHoodItem object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "textures/models/armor/badlands_hood.png");
    }

    @Override
    public Identifier getAnimationResource(BadlandsHoodItem animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, "pinwheel/animations/dummy_armor.json");
    }
}
