package tech.alexnijjar.endermanoverhaul.client.renderer.items;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.alexnijjar.endermanoverhaul.common.items.HoodItem;

public class HoodRenderer extends GeoArmorRenderer<HoodItem> {
    public HoodRenderer(Item item) {
        super(new DefaultedItemGeoModel<>(BuiltInRegistries.ITEM.getKey(item)));
    }
}
