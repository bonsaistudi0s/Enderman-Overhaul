package io.github.padlocks.EndermanOverhaul.client.model.entity;

import io.github.padlocks.EndermanOverhaul.common.entity.ReplacedEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ReplacedEndermanModel<E extends ReplacedEnderman> extends AnimatedGeoModel<E> {
    @Override
    public Identifier getModelLocation(ReplacedEnderman object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "geo/default_enderman.geo.json");
    }

    @Override
    public Identifier getTextureLocation(ReplacedEnderman object) {
        return new Identifier(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman.png");
    }

    @Override
    public Identifier getAnimationFileLocation(ReplacedEnderman animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, "animations/enderman.animation.json");
    }
}
