package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import org.apache.commons.lang3.NotImplementedException;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.items.HoodItem;
import tech.alexnijjar.endermanoverhaul.common.items.pearls.*;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedBladeItem;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShieldItem;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, EndermanOverhaul.MOD_ID);
    public static final ResourcefulRegistry<Item> SPAWN_EGGS = ResourcefulRegistries.create(ITEMS);
    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(EndermanOverhaul.MOD_ID, "main"))
        .setItemIcon(() -> ModItems.CORRUPTED_PEARL.get())
        .addRegistry(ITEMS)
        .build();

    public static final RegistryEntry<Item> BADLANDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("badlands_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.BADLANDS_ENDERMAN, 0xa75537, 0x5a7a60, new Item.Properties()));
    public static final RegistryEntry<Item> CAVE_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("cave_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.CAVE_ENDERMAN, 0xc8c5b6, 0xa88554, new Item.Properties()));
    public static final RegistryEntry<Item> CRIMSON_FOREST_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("crimson_forest_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.CRIMSON_FOREST_ENDERMAN, 0x5e3735, 0x660627, new Item.Properties()));
    public static final RegistryEntry<Item> DARK_OAK_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("dark_oak_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.DARK_OAK_ENDERMAN, 0x0d0e0c, 0x305429, new Item.Properties()));
    public static final RegistryEntry<Item> DESERT_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("desert_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.DESERT_ENDERMAN, 0xf2e3ac, 0x684d45, new Item.Properties()));
    public static final RegistryEntry<Item> END_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("end_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.END_ENDERMAN, 0x141218, 0xefc5f9, new Item.Properties()));
    public static final RegistryEntry<Item> END_ISLANDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("end_islands_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.END_ISLANDS_ENDERMAN, 0x1c2227, 0xb965af, new Item.Properties()));
    public static final RegistryEntry<Item> FLOWER_FIELDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("flower_fields_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.FLOWER_FIELDS_ENDERMAN, 0x6ecf50, 0xffed49, new Item.Properties()));
    public static final RegistryEntry<Item> ICE_SPIKES_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("ice_spikes_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.ICE_SPIKES_ENDERMAN, 0x819ec8, 0xdeede5, new Item.Properties()));
    public static final RegistryEntry<Item> MUSHROOM_FIELDS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("mushroom_fields_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN, 0xbf3942, 0xefe8d2, new Item.Properties()));
    public static final RegistryEntry<Item> NETHER_WASTES_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("nether_wastes_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.NETHER_WASTES_ENDERMAN, 0x552f30, 0x2b323c, new Item.Properties()));
    public static final RegistryEntry<Item> CORAL_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("coral_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.CORAL_ENDERMAN, 0x2b334a, 0x38a8a6, new Item.Properties()));
    public static final RegistryEntry<Item> SAVANNA_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("savanna_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.SAVANNA_ENDERMAN, 0xc66f43, 0xcecdbb, new Item.Properties()));
    public static final RegistryEntry<Item> SNOWY_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("snowy_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.SNOWY_ENDERMAN, 0x133450, 0xd5d8d4, new Item.Properties()));
    public static final RegistryEntry<Item> SOULSAND_VALLEY_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("soulsand_valley_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN, 0x5b4538, 0x5be3e8, new Item.Properties()));
    public static final RegistryEntry<Item> SWAMP_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("swamp_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.SWAMP_ENDERMAN, 0x202820, 0x6a7026, new Item.Properties()));
    public static final RegistryEntry<Item> WARPED_FOREST_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("warped_forest_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.WARPED_FOREST_ENDERMAN, 0x543636, 0x167e86, new Item.Properties()));
    public static final RegistryEntry<Item> WINDSWEPT_HILLS_ENDERMAN_SPAWN_EGG = SPAWN_EGGS.register("windswept_hills_enderman_spawn_egg", createSpawnEggItem(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN, 0x515053, 0xa59a87, new Item.Properties()));

    public static final RegistryEntry<Item> SCARAB_SPAWN_EGG = SPAWN_EGGS.register("scarab_spawn_egg", createSpawnEggItem(ModEntityTypes.SCARAB, 0x05070c, 0x1c2326, new Item.Properties()));
    public static final RegistryEntry<Item> SPIRIT_SPAWN_EGG = SPAWN_EGGS.register("spirit_spawn_egg", createSpawnEggItem(ModEntityTypes.SPIRIT, 0xdbd1a6, 0x7cf2f5, new Item.Properties()));


    public static final RegistryEntry<Item> TINY_SKULL = ITEMS.register("tiny_skull", () -> new BlockItem(ModBlocks.TINY_SKULL.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryEntry<Item> BADLANDS_HOOD = ITEMS.register("badlands_hood", () -> new HoodItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> SAVANNAS_HOOD = ITEMS.register("savanna_hood", () -> new HoodItem(new Item.Properties().stacksTo(1)));
    public static final RegistryEntry<Item> SNOWY_HOOD = ITEMS.register("snowy_hood", () -> new HoodItem(new Item.Properties().stacksTo(1)));

    public static final RegistryEntry<Item> CORRUPTED_PEARL = ITEMS.register("corrupted_pearl", () -> new CorruptedPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> SOUL_PEARL = ITEMS.register("soul_pearl", () -> new SoulPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> ANCIENT_PEARL = ITEMS.register("ancient_pearl", () -> new AncientPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> BUBBLE_PEARL = ITEMS.register("bubble_pearl", () -> new BubblePearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> SUMMONER_PEARL = ITEMS.register("summoner_pearl", () -> new SummonerPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> ICY_PEARL = ITEMS.register("icy_pearl", () -> new IcyPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> CRIMSON_PEARL = ITEMS.register("crimson_pearl", () -> new CrimsonPearlItem(new Item.Properties().stacksTo(16)));
    public static final RegistryEntry<Item> WARPED_PEARL = ITEMS.register("warped_pearl", () -> new WarpedPearlItem(new Item.Properties().stacksTo(16)));

    public static final RegistryEntry<Item> ENDERMAN_TOOTH = ITEMS.register("enderman_tooth", () -> new Item(new Item.Properties()));
    public static final RegistryEntry<Item> CORRUPTED_BLADE = ITEMS.register("corrupted_blade", () -> new CorruptedBladeItem(Tiers.IRON, 3, -2.4f, new Item.Properties()));
    public static final RegistryEntry<Item> CORRUPTED_SHIELD = ITEMS.register("corrupted_shield", () -> new CorruptedShieldItem(new Item.Properties().durability(672)));


    @ExpectPlatform
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        throw new NotImplementedException();
    }
}
