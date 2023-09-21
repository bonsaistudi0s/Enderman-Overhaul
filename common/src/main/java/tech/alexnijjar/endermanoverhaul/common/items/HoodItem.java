package tech.alexnijjar.endermanoverhaul.common.items;

import software.bernie.geckolib.core.animation.AnimatableManager;
import tech.alexnijjar.endermanoverhaul.common.items.base.CustomGeoArmorItem;

public class HoodItem extends CustomGeoArmorItem {
    public HoodItem(Properties properties) {
        super(HoodMaterial.MATERIAL, Type.CHESTPLATE, properties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}
}
