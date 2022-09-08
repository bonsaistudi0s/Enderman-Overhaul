package io.github.padlocks.EndermanOverhaul.client.render.entity;

import io.github.padlocks.EndermanOverhaul.client.model.armor.BadlandsHoodModel;
import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class BadlandsHoodRenderer extends GeoArmorRenderer<BadlandsHoodItem> {
    public BadlandsHoodRenderer() {
        super(new BadlandsHoodModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";
    }
}
