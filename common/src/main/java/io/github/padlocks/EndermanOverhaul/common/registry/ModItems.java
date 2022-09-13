package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorMaterials;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, EndermanOverhaul.MOD_ID);
    public static final BadlandsHoodItem BADLANDS_HOOD = registerItem("badlands_hood", new BadlandsHoodItem(
            ModArmorMaterials.BADLANDS_HOOD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));

    private static <I extends Item> I registerItem(String name, I item) {
        return ITEMS.register(name, () -> item).get();
    }
}
