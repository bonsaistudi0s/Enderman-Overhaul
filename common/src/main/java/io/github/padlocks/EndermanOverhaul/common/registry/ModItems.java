package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.client.model.armor.HoodArmorModel;
import io.github.padlocks.EndermanOverhaul.common.item.HoodArmorItem;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorMaterials;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, EndermanOverhaul.MOD_ID);
    public static final HoodArmorModel BADLANDS_HOOD_MODEL = new HoodArmorModel("geo/badlands_hood.geo.json", "textures/models/armor/badlands_hood.png", "animations/dummy_armor.json");
    public static final HoodArmorItem BADLANDS_HOOD = registerItem("badlands_hood", new HoodArmorItem(
            ModArmorMaterials.HOOD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));

    public static final HoodArmorModel SAVANNA_HOOD_MODEL = new HoodArmorModel("geo/savanna_hood.geo.json", "textures/models/armor/savanna_hood.png", "animations/dummy_armor.json");
    public static final HoodArmorItem SAVANNA_HOOD = registerItem("savanna_hood", new HoodArmorItem(
            ModArmorMaterials.HOOD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));

    public static final HoodArmorModel SNOWY_HOOD_MODEL = new HoodArmorModel("geo/snowy_hood.geo.json", "textures/models/armor/snowy_hood.png", "animations/dummy_armor.json");
    public static final HoodArmorItem SNOWY_HOOD = registerItem("snowy_hood", new HoodArmorItem(
            ModArmorMaterials.HOOD, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT)));

    public static final Item DEFAULT_ENDERMAN_SPAWN_EGG = registerItem("default_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.DEFAULT_ENDERMAN.get(),0x948e8d, 0x3b3635,
                    new Item.Settings().group(ItemGroup.MISC)));
    public static final Item BADLANDS_ENDERMAN_SPAWN_EGG = registerItem("badlands_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.BADLANDS_ENDERMAN.get(),0x948e8d, 0x3b3635,
                    new Item.Settings().group(ItemGroup.MISC)));
    public static final Item CAVE_ENDERMAN_SPAWN_EGG = registerItem("cave_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.CAVE_ENDERMAN.get(),0x948e8d, 0x3b3635,
                    new Item.Settings().group(ItemGroup.MISC)));
    public static final Item FLOWER_ENDERMAN_SPAWN_EGG = registerItem("flower_fields_enderman_spawn_egg",
            new SpawnEggItem(ModEntities.FLOWER_ENDERMAN.get(),0x948e8d, 0x3b3635,
                    new Item.Settings().group(ItemGroup.MISC)));

    private static <I extends Item> I registerItem(String name, I item) {
        return ITEMS.register(name, () -> item).get();
    }
}
