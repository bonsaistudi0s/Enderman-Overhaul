package tech.alexnijjar.endermanoverhaul.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModBiomeTags {
    public static final TagKey<Biome> ALL_SPAWNS = tag("all_spawns");
    public static final TagKey<Biome> BADLANDS_SPAWNS = tag("badlands_spawns");
    public static final TagKey<Biome> CAVE_SPAWNS = tag("cave_spawns");
    public static final TagKey<Biome> CRIMSON_FOREST_SPAWNS = tag("crimson_forest_spawns");
    public static final TagKey<Biome> DARK_OAK_SPAWNS = tag("dark_oak_spawns");
    public static final TagKey<Biome> DESERT_SPAWNS = tag("desert_spawns");
    public static final TagKey<Biome> END_ISLANDS_SPAWNS = tag("end_islands_spawns");
    public static final TagKey<Biome> END_SPAWNS = tag("end_spawns");
    public static final TagKey<Biome> FLOWER_FIELDS_SPAWNS = tag("flower_fields_spawns");
    public static final TagKey<Biome> ICE_SPIKES_SPAWNS = tag("ice_spikes_spawns");
    public static final TagKey<Biome> MUSHROOM_SPAWNS = tag("mushroom_fields_spawns");
    public static final TagKey<Biome> NETHER_WASTES_SPAWNS = tag("nether_wastes_spawns");
    public static final TagKey<Biome> CORAL_SPAWNS = tag("coral_spawns");
    public static final TagKey<Biome> SAVANNA_SPAWNS = tag("savanna_spawns");
    public static final TagKey<Biome> SNOWY_SPAWNS = tag("snowy_spawns");
    public static final TagKey<Biome> SOUL_SAND_VALLEY_SPAWNS = tag("soulsand_valley_spawns");
    public static final TagKey<Biome> SWAMP_SPAWNS = tag("swamp_spawns");
    public static final TagKey<Biome> WARPED_FOREST_SPAWNS = tag("warped_forest_spawns");
    public static final TagKey<Biome> WINDSWEPT_HILLS_SPAWNS = tag("windswept_hills_spawns");

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(EndermanOverhaul.MOD_ID, name));
    }
}
