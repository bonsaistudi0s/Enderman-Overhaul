package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.items.HoodItem;
import tech.alexnijjar.endermanoverhaul.common.items.pearls.*;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedBladeItem;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShieldItem;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, EndermanOverhaul.MOD_ID);
    public static final ResourcefulRegistry<Item> PEARLS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<Item> SPAWN_EGGS = ResourcefulRegistries.create(ITEMS);
    public static final ResourcefulRegistry<CreativeModeTab> TABS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, EndermanOverhaul.MOD_ID);
    public static final RegistryEntry<CreativeModeTab> TAB = TABS.register("main", () -> new ResourcefulCreativeModeTab(ResourceLocation.fromNamespaceAndPath(EndermanOverhaul.MOD_ID, "main"))
        .setItemIcon(ModItems.CORRUPTED_PEARL)
        .addRegistry(ITEMS)
        .build());

    public static final RegistryEntry<Item> BADLANDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("badlands_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.BADLANDS_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> CAVE_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("cave_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.CAVE_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> CRIMSON_FOREST_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("crimson_forest_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.CRIMSON_FOREST_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> DARK_OAK_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("dark_oak_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.DARK_OAK_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> DESERT_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("desert_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.DESERT_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> END_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("end_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.END_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> END_ISLANDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("end_islands_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.END_ISLANDS_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> FLOWER_FIELDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("flower_fields_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.FLOWER_FIELDS_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> ICE_SPIKES_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("ice_spikes_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.ICE_SPIKES_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> MUSHROOM_FIELDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("mushroom_fields_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> NETHER_WASTES_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("nether_wastes_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.NETHER_WASTES_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> CORAL_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("coral_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.CORAL_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> SAVANNA_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("savanna_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SAVANNA_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> SNOWY_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("snowy_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SNOWY_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> SOULSAND_VALLEY_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("soulsand_valley_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> SWAMP_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("swamp_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SWAMP_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> WARPED_FOREST_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("warped_forest_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.WARPED_FOREST_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> WINDSWEPT_HILLS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("windswept_hills_enderman_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryEntry<Item> SCARAB_SPAWN_EGG = SPAWN_EGGS.register("scarab_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SCARAB, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    public static final RegistryEntry<Item> SPIRIT_SPAWN_EGG = SPAWN_EGGS.register("spirit_spawn_egg", () -> new DeferredSpawnEggItem(ModEntityTypes.SPIRIT, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));

    public static final RegistryEntry<Item> TINY_SKULL = ITEMS.register("tiny_skull", () -> new BlockItem(ModBlocks.TINY_SKULL.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryEntry<Item> BADLANDS_HOOD = ITEMS.register("badlands_hood", () -> new HoodItem(new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final RegistryEntry<Item> SAVANNAS_HOOD = ITEMS.register("savanna_hood", () -> new HoodItem(new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(15))));
    public static final RegistryEntry<Item> SNOWY_HOOD = ITEMS.register("snowy_hood", () -> new HoodItem(new Item.Properties().stacksTo(1).durability(ArmorItem.Type.HELMET.getDurability(15))));

    public static final RegistryEntry<Item> CORRUPTED_PEARL = PEARLS.register("corrupted_pearl", () -> new CorruptedPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> SOUL_PEARL = PEARLS.register("soul_pearl", () -> new SoulPearlItem(new Item.Properties().stacksTo(16).component(ModDataComponents.BOUND_ENTITY.get(), -1)));
    public static final RegistryEntry<Item> ANCIENT_PEARL = PEARLS.register("ancient_pearl", () -> new AncientPearlItem(new Item.Properties().stacksTo(16).component(DataComponents.ENTITY_DATA, CustomData.EMPTY)));
    public static final RegistryEntry<Item> BUBBLE_PEARL = PEARLS.register("bubble_pearl", () -> new BubblePearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> SUMMONER_PEARL = PEARLS.register("summoner_pearl", () -> new SummonerPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> ICY_PEARL = PEARLS.register("icy_pearl", () -> new IcyPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> CRIMSON_PEARL = PEARLS.register("crimson_pearl", () -> new CrimsonPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> WARPED_PEARL = PEARLS.register("warped_pearl", () -> new WarpedPearlItem(new Item.Properties().stacksTo(16)));

    public static final RegistryEntry<Item> ENDERMAN_TOOTH = ITEMS.register("enderman_tooth", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CORRUPTED_BLADE = ITEMS.register("corrupted_blade", () -> new CorruptedBladeItem(Tiers.IRON, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4f))));
    public static final RegistryEntry<Item> CORRUPTED_SHIELD = ITEMS.register("corrupted_shield", () -> new CorruptedShieldItem(new Item.Properties().durability(672)));
}
