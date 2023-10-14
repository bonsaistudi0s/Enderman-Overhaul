package tech.alexnijjar.endermanoverhaul.fabric;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModBiomeTags;

import java.util.function.Predicate;

public class EndermanOverhaulFabric {

    public static void init() {
        EndermanOverhaul.init();
        ModEntityTypes.registerAttributes((type, builder) -> FabricDefaultAttributeRegistry.register(type.get(), builder.get()));
        addCustomSpawns();
        removeDefaultSpawns();
    }

    private static void addCustomSpawns() {
        addSpawn(ModEntityTypes.BADLANDS_ENDERMAN, ModBiomeTags.BADLANDS_SPAWNS);
        addSpawn(ModEntityTypes.CAVE_ENDERMAN, ModBiomeTags.CAVE_SPAWNS);
        addSpawnWithCost(ModEntityTypes.CRIMSON_FOREST_ENDERMAN, tag(ModBiomeTags.CRIMSON_FOREST_SPAWNS), 2, 1, 2, 0.5, 0.1);
        addSpawn(ModEntityTypes.DARK_OAK_ENDERMAN, ModBiomeTags.DARK_OAK_SPAWNS);
        addSpawn(ModEntityTypes.DESERT_ENDERMAN, ModBiomeTags.DESERT_SPAWNS);
        addSpawnWithCost(ModEntityTypes.END_ENDERMAN, tag(ModBiomeTags.END_SPAWNS), 2, 1, 2, 0.6, 0.1);
        addSpawnWithCost(ModEntityTypes.END_ISLANDS_ENDERMAN, tag(ModBiomeTags.END_ISLANDS_SPAWNS), 1, 1, 1, 0.5, 0.08);
        addSpawn(ModEntityTypes.FLOWER_FIELDS_ENDERMAN, ModBiomeTags.FLOWER_FIELDS_SPAWNS);
        addSpawn(ModEntityTypes.ICE_SPIKES_ENDERMAN, ModBiomeTags.ICE_SPIKES_SPAWNS);
        addSpawnWithCost(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN, tag(ModBiomeTags.MUSHROOM_SPAWNS), 3, 1, 2, 0.7, 0.15);
        addSpawn(ModEntityTypes.NETHER_WASTES_ENDERMAN, tag(ModBiomeTags.NETHER_WASTES_SPAWNS), 1, 4, 4);
        addSpawnWithCost(ModEntityTypes.OCEAN_ENDERMAN, tag(ModBiomeTags.OCEAN_SPAWNS), 2, 1, 2, 0.7, 0.12);
        addSpawn(ModEntityTypes.SAVANNA_ENDERMAN, ModBiomeTags.SAVANNA_SPAWNS);
        addSpawn(ModEntityTypes.SNOWY_ENDERMAN, ModBiomeTags.SNOWY_SPAWNS);
        addSpawnWithCost(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN, tag(ModBiomeTags.SOUL_SAND_VALLEY_SPAWNS), 2, 1, 2, 0.7, 0.15);
        addSpawn(ModEntityTypes.SWAMP_ENDERMAN, ModBiomeTags.SWAMP_SPAWNS);
        addSpawnWithCost(ModEntityTypes.WARPED_FOREST_ENDERMAN, tag(ModBiomeTags.WARPED_FOREST_SPAWNS), 1, 4, 4, 1.0, 0.12);
        addSpawn(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN, ModBiomeTags.WINDSWEPT_HILLS_SPAWNS);
    }

    public static void removeDefaultSpawns() {
        BiomeModifications.create(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ENDERMAN))
            .add(ModificationPhase.REMOVALS, tag(ModBiomeTags.ALL_SPAWNS),
                context -> {
                    context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.ENDERMAN);
                    context.getSpawnSettings().clearSpawnCost(EntityType.ENDERMAN);
                });
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
    }

    private static <T extends EnderMan> void addSpawn(RegistryEntry<EntityType<T>> type, TagKey<Biome> tag) {
        addSpawn(type, tag(tag), 10, 1, 4);
    }

    private static <T extends EnderMan> void addSpawn(RegistryEntry<EntityType<T>> type, Predicate<BiomeSelectionContext> biomeSelector, int weight, int min, int max) {
        BiomeModifications.addSpawn(
            biomeSelector,
            type.get().getCategory(),
            type.get(), weight,
            min, max);
    }

    public static <T extends EnderMan> void addSpawnWithCost(RegistryEntry<EntityType<T>> type, Predicate<BiomeSelectionContext> biomeSelector, int weight, int min, int max, double charge, double energyBudget) {
        BiomeModifications.create(type.getId()).add(ModificationPhase.ADDITIONS, biomeSelector, context -> {
            context.getSpawnSettings().addSpawn(type.get().getCategory(), new MobSpawnSettings.SpawnerData(type.get(), weight, min, max));
            context.getSpawnSettings().setSpawnCost(type.get(), charge, energyBudget);
        });
    }
}
