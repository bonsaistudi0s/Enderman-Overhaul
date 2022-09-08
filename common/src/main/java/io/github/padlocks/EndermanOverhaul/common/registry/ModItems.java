package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorItem;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorMaterials;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, EndermanOverhaul.MOD_ID);
    //private static final Map<ResourceLocation, Supplier<ItemModeledArmor>> MODELED_ARMOR = new WeakHashMap<>();


    public static final Item BADLANDS_HOOD = ITEMS.register("badlands_hood", () -> new ModArmorItem(ModArmorMaterials.BADLANDS_HOOD, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT))).get();
    //public static final Supplier<Item> BADLANDS_ENDERMAN_SPAWN_EGG = ITEMS.register("badlands_enderman_spawn_egg", () -> new SpawnEggItemBase<>(ModEntities.BADLANDS_ENDERMAN, 0x74A837, 0xB76CCB, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final Supplier<Item> CAVE_ENDERMAN_SPAWN_EGG = ITEMS.register("cave_enderman_spawn_egg", () -> new SpawnEggItemBase<>(ModEntities.CAVE_ENDERMAN, 0x74A837, 0xB76CCB, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final Supplier<Item> FLOWER_FIELDS_ENDERMAN_SPAWN_EGG = ITEMS.register("flower_fields_enderman_spawn_egg", () -> new SpawnEggItemBase<>(ModEntities.FLOWER_ENDERMAN, 0x74A837, 0xB76CCB, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final Supplier<Item> DEFAULT_ENDERMAN_SPAWN_EGG = ITEMS.register("enderman_spawn_egg", () -> new SpawnEggItemBase<>(ModEntities.DEFAULT_ENDERMAN, 0x74A837, 0xB76CCB, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    //public static final Supplier<Item> BADLANDS_HOOD = ITEMS.register("badlands_hood", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    //public static final Supplier<Item> BADLANDS_HOOD = ITEMS.register("badlands_hood", () -> new ArmorItem(HoodMaterial.HOOD_MATERIAL, EquipmentSlot.HEAD, new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    //public static final Supplier<Item> BADLANDS_HOOD_ITEM = ITEMS.register("badlands_hood_item", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    //public static final Supplier<ItemModeledArmor> item = ITEMS.register("badlands_hood", () -> new ItemBadlandsHood());
    //public static final Supplier<ItemModeledArmor> BADLANDS_HOOD = modelArmor("badlands_hood", item);

//    protected static Supplier<ItemModeledArmor> modelArmor(String id, Supplier<ItemModeledArmor> armor) {
//        MODELED_ARMOR.put(EndermanOverhaul.resourceLocation("textures/armor/hood/" + id + ".png"), armor);
//        return armor;
//    }

//    public static Map<ResourceLocation, Supplier<ItemModeledArmor>> getModeledArmor() {
//        return MODELED_ARMOR;
//    }
}
