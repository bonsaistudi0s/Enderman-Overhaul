package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.item.BadlandsHoodItem;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorItem;
import io.github.padlocks.EndermanOverhaul.common.item.ModArmorMaterials;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, EndermanOverhaul.MOD_ID);
    //public static final Supplier<ModArmorItem> BADLANDS_HOOD_B = ITEMS.register("badlands_hood", () -> new BadlandsHoodItem(ModArmorMaterials.BADLANDS_HOOD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));

//    public static final Item BADLANDS_HOOD = registerItem("badlands_hood",
//        new BadlandsHoodItem(ModArmorMaterials.BADLANDS_HOOD, EquipmentSlot.HEAD,
//                    new Item.Settings().group(ItemGroup.COMBAT)));

    public static final BadlandsHoodItem BADLANDS_HOOD = registerItem("badlands_hood", new BadlandsHoodItem(
            ModArmorMaterials.BADLANDS_HOOD, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));


    private static <I extends Item> I registerItem(String name, I item) {
        return ITEMS.register(name, () -> item).get();
        //return Registry.register(Registry.ITEM, new Identifier(EndermanOverhaul.MOD_ID, name), item);
    }
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
