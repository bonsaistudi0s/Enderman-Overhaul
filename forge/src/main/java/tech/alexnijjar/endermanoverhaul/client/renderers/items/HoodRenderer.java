package tech.alexnijjar.endermanoverhaul.client.renderers.items;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.items.HoodItem;

public class HoodRenderer extends GeoArmorRenderer<HoodItem> {

    public HoodRenderer(Item item) {
        super(
            new AnimatedGeoModel<>() {
                @Override
                public ResourceLocation getAnimationResource(HoodItem entity) {
                    return Registry.ITEM.getKey(item);
                }

                @Override
                public ResourceLocation getModelResource(HoodItem entity) {
                    return new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/item/" + Registry.ITEM.getKey(item).getPath() + ".geo.json");
                }

                @Override
                public ResourceLocation getTextureResource(HoodItem entity) {
                    return new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/item/armor/" + Registry.ITEM.getKey(item).getPath() + ".png");
                }
            }
        );

        headBone = "head2";
        bodyBone = "body2";
        rightArmBone = "right_arm2";
        leftArmBone = "left_arm2";
        rightLegBone = null;
        leftLegBone = null;
        rightBootBone = null;
        leftBootBone = null;
    }

    public GeoArmorRenderer<HoodItem> applySlot(EquipmentSlot slot) {
        this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(this.currentArmorItem));

        setBoneVisibility(this.headBone, false);
        setBoneVisibility(this.bodyBone, false);
        setBoneVisibility(this.rightArmBone, false);
        setBoneVisibility(this.leftArmBone, false);
        setBoneVisibility(this.rightLegBone, false);
        setBoneVisibility(this.leftLegBone, false);
        setBoneVisibility(this.rightBootBone, false);
        setBoneVisibility(this.rightBootBone, false);
        setBoneVisibility(this.leftBootBone, false);

        if (EquipmentSlot.CHEST == slot) {
            setBoneVisibility(this.headBone, true);
            setBoneVisibility(this.bodyBone, true);
            setBoneVisibility(this.rightArmBone, true);
            setBoneVisibility(this.leftArmBone, true);
        }

        return this;
    }
}
