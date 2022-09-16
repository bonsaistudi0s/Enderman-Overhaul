package io.github.padlocks.EndermanOverhaul.client.render.entity;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class HoodArmorRenderer<T extends ArmorItem & IAnimatable> extends GeoArmorRenderer<T> {
    private final T item;

    public HoodArmorRenderer(AnimatedGeoModel<T> model, T item) {
        super(model);

        this.item = item;
        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftLeg";
        this.leftBootBone = "armorRightLeg";
    }

    @Override
    public GeoArmorRenderer<T> applySlot(EquipmentSlot boneSlot) {
        modelProvider.getModel(modelProvider.getModelLocation(this.item));

        IBone bodyBone = this.getAndHideBone(this.bodyBone);
        IBone rightArmBone = this.getAndHideBone(this.rightArmBone);
        IBone leftArmBone = this.getAndHideBone(this.leftArmBone);
        IBone rightLegBone = this.getAndHideBone(this.rightLegBone);
        IBone leftLegBone = this.getAndHideBone(this.leftLegBone);
        IBone rightBootBone = this.getAndHideBone(this.rightBootBone);
        IBone leftBootBone = this.getAndHideBone(this.leftBootBone);

        switch (boneSlot) {
            case CHEST:
                if (bodyBone != null)
                    bodyBone.setHidden(false);
                if (rightArmBone != null)
                    rightArmBone.setHidden(false);
                if (leftArmBone != null)
                    leftArmBone.setHidden(false);
                break;
            case LEGS:
                if (rightLegBone != null)
                    rightLegBone.setHidden(false);
                if (leftLegBone != null)
                    leftLegBone.setHidden(false);
                break;
            case FEET:
                if (rightBootBone != null)
                    rightBootBone.setHidden(false);
                if (leftBootBone != null)
                    leftBootBone.setHidden(false);
                break;
        }
        return this;
    }
}
