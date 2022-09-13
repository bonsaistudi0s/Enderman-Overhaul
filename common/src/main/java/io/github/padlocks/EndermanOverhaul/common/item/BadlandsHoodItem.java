package io.github.padlocks.EndermanOverhaul.common.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BadlandsHoodItem extends ModArmorItem implements IAnimatable {
    public BadlandsHoodItem(ArmorMaterial material, EquipmentSlot slot, Settings properties) {
        super(material, slot, properties);
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
