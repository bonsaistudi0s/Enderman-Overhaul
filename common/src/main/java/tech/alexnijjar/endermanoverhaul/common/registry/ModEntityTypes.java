package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.*;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModEntityTypes {
    public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, EndermanOverhaul.MOD_ID);

    public static final RegistryEntry<EntityType<CaveEnderman>> CAVE_ENDERMAN = ENTITY_TYPES.register("cave_enderman", () ->
        EntityType.Builder.of(CaveEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.6f)
            .build("cave_enderman"));

    public static final RegistryEntry<EntityType<SavannaEnderman>> SAVANNA_ENDERMAN = ENTITY_TYPES.register("savanna_enderman", () ->
        EntityType.Builder.of(SavannaEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.8f)
            .build("savanna_enderman"));

    public static final RegistryEntry<EntityType<NetherWastesEnderman>> NETHER_WASTES_ENDERMAN = ENTITY_TYPES.register("nether_wastes_enderman", () ->
        EntityType.Builder.of(NetherWastesEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.9f)
            .fireImmune()
            .build("nether_wastes_enderman"));

    public static final RegistryEntry<EntityType<CrimsonForestEnderman>> CRIMSON_FOREST_ENDERMAN = ENTITY_TYPES.register("crimson_forest_enderman", () ->
        EntityType.Builder.of(CrimsonForestEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.1f)
            .fireImmune()
            .build("crimson_forest_enderman"));

    public static final RegistryEntry<EntityType<SwampEnderman>> SWAMP_ENDERMAN = ENTITY_TYPES.register("swamp_enderman", () ->
        EntityType.Builder.of(SwampEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.8f)
            .build("swamp_enderman"));

    public static final RegistryEntry<EntityType<IceSpikesEnderman>> ICE_SPIKES_ENDERMAN = ENTITY_TYPES.register("ice_spikes_enderman", () ->
        EntityType.Builder.of(IceSpikesEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.9f)
            .build("ice_spikes_enderman"));

    public static final RegistryEntry<EntityType<DarkOakEnderman>> DARK_OAK_ENDERMAN = ENTITY_TYPES.register("dark_oak_enderman", () ->
        EntityType.Builder.of(DarkOakEnderman::new, MobCategory.MONSTER)
            .sized(0.7f, 3.5f)
            .build("dark_oak_enderman"));

    public static final RegistryEntry<EntityType<WindsweptHillsEnderman>> WINDSWEPT_HILLS_ENDERMAN = ENTITY_TYPES.register("windswept_hills_enderman", () ->
        EntityType.Builder.of(WindsweptHillsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 4.1f)
            .build("windswept_hills_enderman"));

    public static final RegistryEntry<EntityType<BadlandsEnderman>> BADLANDS_ENDERMAN = ENTITY_TYPES.register("badlands_enderman", () ->
        EntityType.Builder.of(BadlandsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.0f)
            .build("badlands_enderman"));

    public static final RegistryEntry<EntityType<SnowyEnderman>> SNOWY_ENDERMAN = ENTITY_TYPES.register("snowy_enderman", () ->
        EntityType.Builder.of(SnowyEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.8f)
            .build("snowy_enderman"));

    public static final RegistryEntry<EntityType<DesertEnderman>> DESERT_ENDERMAN = ENTITY_TYPES.register("desert_enderman", () ->
        EntityType.Builder.of(DesertEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 3.1f)
            .build("desert_enderman"));

    public static final RegistryEntry<EntityType<SoulsandValleyEnderman>> SOULSAND_VALLEY_ENDERMAN = ENTITY_TYPES.register("soulsand_valley_enderman", () ->
        EntityType.Builder.of(SoulsandValleyEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.5f)
            .fireImmune()
            .build("soulsand_valley_enderman"));

    public static final RegistryEntry<EntityType<EndEnderman>> END_ENDERMAN = ENTITY_TYPES.register("end_enderman", () ->
        EntityType.Builder.of(EndEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 2.5f)
            .fireImmune()
            .build("end_enderman"));

    public static final RegistryEntry<EntityType<FlowerFieldsEnderman>> FLOWER_FIELDS_ENDERMAN = ENTITY_TYPES.register("flower_fields_enderman", () ->
        EntityType.Builder.of(FlowerFieldsEnderman::new, MobCategory.CREATURE)
            .sized(0.5f, 1.5f)
            .build("flower_fields_enderman"));

    public static final RegistryEntry<EntityType<MushroomFieldsEnderman>> MUSHROOM_FIELDS_ENDERMAN = ENTITY_TYPES.register("mushroom_fields_enderman", () ->
        EntityType.Builder.of(MushroomFieldsEnderman::new, MobCategory.CREATURE)
            .sized(0.6f, 2.6f)
            .build("mushroom_fields_enderman"));

    public static final RegistryEntry<EntityType<EndIslandsEnderman>> END_ISLANDS_ENDERMAN = ENTITY_TYPES.register("end_islands_enderman", () ->
        EntityType.Builder.of(EndIslandsEnderman::new, MobCategory.MONSTER)
            .sized(0.6f, 4.4f)
            .fireImmune()
            .build("end_islands_enderman"));

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(CAVE_ENDERMAN, CaveEnderman::createAttributes);
        attributes.accept(SAVANNA_ENDERMAN, SavannaEnderman::createAttributes);
        attributes.accept(NETHER_WASTES_ENDERMAN, NetherWastesEnderman::createAttributes);
        attributes.accept(CRIMSON_FOREST_ENDERMAN, CrimsonForestEnderman::createAttributes);
        attributes.accept(SWAMP_ENDERMAN, SwampEnderman::createAttributes);
        attributes.accept(ICE_SPIKES_ENDERMAN, IceSpikesEnderman::createAttributes);
        attributes.accept(DARK_OAK_ENDERMAN, DarkOakEnderman::createAttributes);
        attributes.accept(WINDSWEPT_HILLS_ENDERMAN, WindsweptHillsEnderman::createAttributes);
        attributes.accept(BADLANDS_ENDERMAN, BadlandsEnderman::createAttributes);
        attributes.accept(SNOWY_ENDERMAN, SnowyEnderman::createAttributes);
        attributes.accept(DESERT_ENDERMAN, DesertEnderman::createAttributes);
        attributes.accept(SOULSAND_VALLEY_ENDERMAN, SoulsandValleyEnderman::createAttributes);
        attributes.accept(END_ENDERMAN, EndEnderman::createAttributes);
        attributes.accept(FLOWER_FIELDS_ENDERMAN, FlowerFieldsEnderman::createAttributes);
        attributes.accept(MUSHROOM_FIELDS_ENDERMAN, MushroomFieldsEnderman::createAttributes);
        attributes.accept(END_ISLANDS_ENDERMAN, EndIslandsEnderman::createAttributes);
    }

    public static void registerSpawnPlacements() {
//        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
    }
}