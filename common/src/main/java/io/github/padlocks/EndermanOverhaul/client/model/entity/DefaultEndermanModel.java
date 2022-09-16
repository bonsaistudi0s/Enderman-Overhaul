package io.github.padlocks.EndermanOverhaul.client.model.entity;

import io.github.padlocks.EndermanOverhaul.common.entity.DefaultEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DefaultEndermanModel <E extends DefaultEnderman> extends AnimatedGeoModel<E> {
    @Override
    public Identifier getModelLocation(DefaultEnderman object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "geo/badlands_hood.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DefaultEnderman object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DefaultEnderman animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, "animations/enderman.animation.json");
    }
}
