package io.github.padlocks.EndermanOverhaul.client.model.armor;

import io.github.padlocks.EndermanOverhaul.common.item.ModArmorItem;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HoodArmorModel<T extends ModArmorItem & IAnimatable> extends AnimatedGeoModel<T> {

    private final String model_path;
    private final String texture_path;
    private final String animation_path;

    public HoodArmorModel(String model_path, String texture_path, String animation_path) {
        super();
        this.model_path = model_path;
        this.texture_path = texture_path;
        this.animation_path = animation_path;

    }

    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(EndermanOverhaul.MOD_ID, model_path);
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(EndermanOverhaul.MOD_ID, texture_path);
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(EndermanOverhaul.MOD_ID, animation_path);
    }
}
