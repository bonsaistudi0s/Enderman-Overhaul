package io.github.padlocks.EndermanOverhaul.client.model.armor;

import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BadlandsHoodModel extends AnimatedGeoModel<BadlandsHoodItem> {

    @Override
    public Identifier getModelLocation(BadlandsHoodItem object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "geo/badlands_hood.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BadlandsHoodItem object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "textures/models/armor/badlands_hood.png");
    }

    @Override
    public Identifier getAnimationFileLocation(BadlandsHoodItem animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, "animations/dummy_armor.json");
    }
}
