package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.CaveEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.NetherWastesEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.SavannaEnderman;

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

    public static void registerAttributes(BiConsumer<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> attributes) {
        attributes.accept(CAVE_ENDERMAN, CaveEnderman::createAttributes);
        attributes.accept(SAVANNA_ENDERMAN, SavannaEnderman::createAttributes);
        attributes.accept(NETHER_WASTES_ENDERMAN, NetherWastesEnderman::createAttributes);
    }

    public static void registerSpawnPlacements() {
    }

    public static <T extends Mob> void registerSpawnPlacement(EntityType<T> entityType, SpawnPlacements.Type decoratorType, Heightmap.Types heightMapType, SpawnPlacements.SpawnPredicate<T> decoratorPredicate) {
        SpawnPlacements.register(entityType, decoratorType, heightMapType, decoratorPredicate);
    }
}