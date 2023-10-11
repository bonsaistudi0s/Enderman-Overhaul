package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.*;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.AxolotlPetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.HammerheadPetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.PetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.*;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Scarab;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Spirit;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, EndermanOverhaul.MOD_ID);
    public static final ResourcefulRegistry<EntityType<?>> PEARLS = ResourcefulRegistries.create(ENTITY_TYPES);

    public static final RegistryEntry<EntityType<BadlandsEnderman>> BADLANDS_ENDERMAN = ENTITY_TYPES.register("badlands_enderman", () ->
        EntityType.Builder.of(BadlandsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.0f)
            .build("badlands_enderman"));

    public static final RegistryEntry<EntityType<CaveEnderman>> CAVE_ENDERMAN = ENTITY_TYPES.register("cave_enderman", () ->
        EntityType.Builder.of(CaveEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.6f)
            .build("cave_enderman"));

    public static final RegistryEntry<EntityType<CrimsonForestEnderman>> CRIMSON_FOREST_ENDERMAN = ENTITY_TYPES.register("crimson_forest_enderman", () ->
        EntityType.Builder.of(CrimsonForestEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.1f)
            .fireImmune()
            .build("crimson_forest_enderman"));

    public static final RegistryEntry<EntityType<DarkOakEnderman>> DARK_OAK_ENDERMAN = ENTITY_TYPES.register("dark_oak_enderman", () ->
        EntityType.Builder.of(DarkOakEnderman::new, MobCategory.MONSTER)
            .sized(0.7f, 3.5f)
            .build("dark_oak_enderman"));

    public static final RegistryEntry<EntityType<DesertEnderman>> DESERT_ENDERMAN = ENTITY_TYPES.register("desert_enderman", () ->
        EntityType.Builder.of(DesertEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.1f)
            .build("desert_enderman"));

    public static final RegistryEntry<EntityType<EndEnderman>> END_ENDERMAN = ENTITY_TYPES.register("end_enderman", () ->
        EntityType.Builder.of(EndEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.5f)
            .fireImmune()
            .build("end_enderman"));

    public static final RegistryEntry<EntityType<EndIslandsEnderman>> END_ISLANDS_ENDERMAN = ENTITY_TYPES.register("end_islands_enderman", () ->
        EntityType.Builder.of(EndIslandsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 4.4f)
            .fireImmune()
            .build("end_islands_enderman"));

    public static final RegistryEntry<EntityType<FlowerFieldsEnderman>> FLOWER_FIELDS_ENDERMAN = ENTITY_TYPES.register("flower_fields_enderman", () ->
        EntityType.Builder.of(FlowerFieldsEnderman::new, MobCategory.MONSTER)
            .sized(0.5f, 1.5f)
            .build("flower_fields_enderman"));

    public static final RegistryEntry<EntityType<IceSpikesEnderman>> ICE_SPIKES_ENDERMAN = ENTITY_TYPES.register("ice_spikes_enderman", () ->
        EntityType.Builder.of(IceSpikesEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.9f)
            .build("ice_spikes_enderman"));

    public static final RegistryEntry<EntityType<MushroomFieldsEnderman>> MUSHROOM_FIELDS_ENDERMAN = ENTITY_TYPES.register("mushroom_fields_enderman", () ->
        EntityType.Builder.of(MushroomFieldsEnderman::new, MobCategory.AMBIENT)
            .sized(0.6f, 2.6f)
            .build("mushroom_fields_enderman"));

    public static final RegistryEntry<EntityType<NetherWastesEnderman>> NETHER_WASTES_ENDERMAN = ENTITY_TYPES.register("nether_wastes_enderman", () ->
        EntityType.Builder.of(NetherWastesEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.9f)
            .fireImmune()
            .build("nether_wastes_enderman"));

    public static final RegistryEntry<EntityType<OceanEnderman>> OCEAN_ENDERMAN = ENTITY_TYPES.register("ocean_enderman", () ->
        EntityType.Builder.of(OceanEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.0f)
            .build("ocean_enderman"));

    public static final RegistryEntry<EntityType<SavannaEnderman>> SAVANNA_ENDERMAN = ENTITY_TYPES.register("savanna_enderman", () ->
        EntityType.Builder.of(SavannaEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.8f)
            .build("savanna_enderman"));

    public static final RegistryEntry<EntityType<SnowyEnderman>> SNOWY_ENDERMAN = ENTITY_TYPES.register("snowy_enderman", () ->
        EntityType.Builder.of(SnowyEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.8f)
            .build("snowy_enderman"));

    public static final RegistryEntry<EntityType<SoulsandValleyEnderman>> SOULSAND_VALLEY_ENDERMAN = ENTITY_TYPES.register("soulsand_valley_enderman", () ->
        EntityType.Builder.of(SoulsandValleyEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.5f)
            .fireImmune()
            .build("soulsand_valley_enderman"));

    public static final RegistryEntry<EntityType<SwampEnderman>> SWAMP_ENDERMAN = ENTITY_TYPES.register("swamp_enderman", () ->
        EntityType.Builder.of(SwampEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.8f)
            .build("swamp_enderman"));

    public static final RegistryEntry<EntityType<WarpedForestEnderman>> WARPED_FOREST_ENDERMAN = ENTITY_TYPES.register("warped_forest_enderman", () ->
        EntityType.Builder.of(WarpedForestEnderman::new, MobCategory.MONSTER)
            .sized(0.8f, 2.4f)
            .fireImmune()
            .build("warped_forest_enderman"));

    public static final RegistryEntry<EntityType<WindsweptHillsEnderman>> WINDSWEPT_HILLS_ENDERMAN = ENTITY_TYPES.register("windswept_hills_enderman", () ->
        EntityType.Builder.of(WindsweptHillsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 4.1f)
            .build("windswept_hills_enderman"));

    // Pets
    public static final RegistryEntry<EntityType<PetEnderman>> PET_ENDERMAN = ENTITY_TYPES.register("pet_enderman", () ->
        EntityType.Builder.<PetEnderman>of(PetEnderman::new, MobCategory.CREATURE)
            .sized(0.8f, 2.8f)
            .build("pet_enderman"));

    public static final RegistryEntry<EntityType<HammerheadPetEnderman>> HAMMERHEAD_PET_ENDERMAN = ENTITY_TYPES.register("hammerhead_pet_enderman", () ->
        EntityType.Builder.<HammerheadPetEnderman>of(HammerheadPetEnderman::new, MobCategory.CREATURE)
            .sized(1.0f, 2.6f)
            .build("hammerhead_pet_enderman"));

    public static final RegistryEntry<EntityType<AxolotlPetEnderman>> AXOLOTL_PET_ENDERMAN = ENTITY_TYPES.register("axolotl_pet_enderman", () ->
        EntityType.Builder.<AxolotlPetEnderman>of(AxolotlPetEnderman::new, MobCategory.CREATURE)
            .sized(0.8f, 2.7f)
            .build("axolotl_pet_enderman"));

    // Summons
    public static final RegistryEntry<EntityType<Scarab>> SCARAB = ENTITY_TYPES.register("scarab", () ->
        EntityType.Builder.of(Scarab::new, MobCategory.MONSTER)
            .sized(0.3f, 0.5f)
            .build("scarab"));

    public static final RegistryEntry<EntityType<Spirit>> SPIRIT = ENTITY_TYPES.register("spirit", () ->
        EntityType.Builder.of(Spirit::new, MobCategory.MONSTER)
            .sized(0.3f, 0.3f)
            .fireImmune()
            .build("spirit"));

    // Projectiles
    public static final RegistryEntry<EntityType<EnderBullet>> ENDER_BULLET = ENTITY_TYPES.register("ender_bullet", () ->
        EntityType.Builder.<EnderBullet>of(EnderBullet::new, MobCategory.MISC)
            .sized(0.3125f, 0.3125f)
            .clientTrackingRange(8)
            .build("ender_bullet"));

    public static final RegistryEntry<EntityType<ThrownCorruptedPearl>> CORRUPTED_PEARL = PEARLS.register("corrupted_pearl", () ->
        EntityType.Builder.<ThrownCorruptedPearl>of(ThrownCorruptedPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("corrupted_pearl"));

    public static final RegistryEntry<EntityType<ThrownSoulPearl>> SOUL_PEARL = PEARLS.register("soul_pearl", () ->
        EntityType.Builder.<ThrownSoulPearl>of(ThrownSoulPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("soul_pearl"));

    public static final RegistryEntry<EntityType<ThrownAncientPearl>> ANCIENT_PEARL = PEARLS.register("ancient_pearl", () ->
        EntityType.Builder.<ThrownAncientPearl>of(ThrownAncientPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("ancient_pearl"));

    public static final RegistryEntry<EntityType<ThrownBubblePearl>> BUBBLE_PEARL = PEARLS.register("bubble_pearl", () ->
        EntityType.Builder.<ThrownBubblePearl>of(ThrownBubblePearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("bubble_pearl"));

    public static final RegistryEntry<EntityType<ThrownSummonerPearl>> SUMMONER_PEARL = PEARLS.register("summoner_pearl", () ->
        EntityType.Builder.<ThrownSummonerPearl>of(ThrownSummonerPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("summoner_pearl"));

    public static final RegistryEntry<EntityType<ThrownIcyPearl>> ICY_PEARL = PEARLS.register("icy_pearl", () ->
        EntityType.Builder.<ThrownIcyPearl>of(ThrownIcyPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("icy_pearl"));

    public static final RegistryEntry<EntityType<ThrownCrimsonPearl>> CRIMSON_PEARL = PEARLS.register("crimson_pearl", () ->
        EntityType.Builder.<ThrownCrimsonPearl>of(ThrownCrimsonPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("crimson_pearl"));

    public static final RegistryEntry<EntityType<ThrownWarpedPearl>> WARPED_PEARL = PEARLS.register("warped_pearl", () ->
        EntityType.Builder.<ThrownWarpedPearl>of(ThrownWarpedPearl::new, MobCategory.MISC)
            .sized(0.25f, 0.25f)
            .clientTrackingRange(4)
            .updateInterval(10)
            .build("warped_pearl"));

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(BADLANDS_ENDERMAN, BadlandsEnderman::createAttributes);
        attributes.accept(CAVE_ENDERMAN, CaveEnderman::createAttributes);
        attributes.accept(CRIMSON_FOREST_ENDERMAN, CrimsonForestEnderman::createAttributes);
        attributes.accept(DARK_OAK_ENDERMAN, DarkOakEnderman::createAttributes);
        attributes.accept(DESERT_ENDERMAN, DesertEnderman::createAttributes);
        attributes.accept(END_ENDERMAN, EndEnderman::createAttributes);
        attributes.accept(END_ISLANDS_ENDERMAN, EndIslandsEnderman::createAttributes);
        attributes.accept(FLOWER_FIELDS_ENDERMAN, FlowerFieldsEnderman::createAttributes);
        attributes.accept(ICE_SPIKES_ENDERMAN, IceSpikesEnderman::createAttributes);
        attributes.accept(MUSHROOM_FIELDS_ENDERMAN, MushroomFieldsEnderman::createAttributes);
        attributes.accept(NETHER_WASTES_ENDERMAN, NetherWastesEnderman::createAttributes);
        attributes.accept(OCEAN_ENDERMAN, OceanEnderman::createAttributes);
        attributes.accept(SAVANNA_ENDERMAN, SavannaEnderman::createAttributes);
        attributes.accept(SNOWY_ENDERMAN, SnowyEnderman::createAttributes);
        attributes.accept(SOULSAND_VALLEY_ENDERMAN, SoulsandValleyEnderman::createAttributes);
        attributes.accept(SWAMP_ENDERMAN, SwampEnderman::createAttributes);
        attributes.accept(WARPED_FOREST_ENDERMAN, WarpedForestEnderman::createAttributes);
        attributes.accept(WINDSWEPT_HILLS_ENDERMAN, WindsweptHillsEnderman::createAttributes);

        // Pets
        attributes.accept(PET_ENDERMAN, PetEnderman::createAttributes);
        attributes.accept(HAMMERHEAD_PET_ENDERMAN, HammerheadPetEnderman::createAttributes);
        attributes.accept(AXOLOTL_PET_ENDERMAN, AxolotlPetEnderman::createAttributes);

        // Summons
        attributes.accept(SCARAB, Scarab::createAttributes);
        attributes.accept(SPIRIT, Spirit::createAttributes);
    }

    public static void registerSpawnPlacements() {
        SpawnPlacements.register(BADLANDS_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BadlandsEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(CAVE_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CaveEnderman::checkSpawnRules);
        SpawnPlacements.register(CRIMSON_FOREST_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrimsonForestEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(DARK_OAK_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DarkOakEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(DESERT_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DesertEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(END_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(END_ISLANDS_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndIslandsEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(FLOWER_FIELDS_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, FlowerFieldsEnderman::checkMobSpawnRules);
        SpawnPlacements.register(ICE_SPIKES_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IceSpikesEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(MUSHROOM_FIELDS_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MushroomFieldsEnderman::checkMobSpawnRules);
        SpawnPlacements.register(NETHER_WASTES_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, NetherWastesEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(OCEAN_ENDERMAN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, OceanEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(SAVANNA_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SavannaEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(SNOWY_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SnowyEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(SOULSAND_VALLEY_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SoulsandValleyEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(SWAMP_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SwampEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(WARPED_FOREST_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WarpedForestEnderman::checkMonsterSpawnRules);
        SpawnPlacements.register(WINDSWEPT_HILLS_ENDERMAN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WindsweptHillsEnderman::checkMonsterSpawnRules);

        // Summons
        SpawnPlacements.register(SCARAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(SPIRIT.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }
}