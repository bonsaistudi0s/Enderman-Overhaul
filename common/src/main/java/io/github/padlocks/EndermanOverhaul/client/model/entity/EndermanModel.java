package io.github.padlocks.EndermanOverhaul.client.model.entity;

import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EndermanModel <E extends BaseEnderman> extends AnimatedGeoModel<E> {
    private final EndermanType type;
    public boolean carryingBlock;
    public boolean angry;

    public EndermanModel(EndermanType type) {
        this.type = type;
    }

    @Override
    public Identifier getModelLocation(E entity) {
        return type.model();
    }

    @Override
    public Identifier getTextureLocation(E entity) {
        return type.texture();
    }

    @Override
    public Identifier getAnimationFileLocation(E animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, "animations/enderman.animation.json");
    }
}
