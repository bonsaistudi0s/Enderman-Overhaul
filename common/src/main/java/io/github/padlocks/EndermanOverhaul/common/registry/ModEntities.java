package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.EntityAttributeRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.entity.EndermanTypes;
import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.common.entity.base.PassiveEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class ModEntities {
    public static final PollinatedRegistry<EntityType<?>> ENTITIES = PollinatedRegistry.create(Registry.ENTITY_TYPE, EndermanOverhaul.MOD_ID);
    public static final Supplier<EntityType<BaseEnderman>> DEFAULT_ENDERMAN = ENTITIES.register("default_enderman",
            () -> EntityType.Builder.of(BaseEnderman.of(EndermanTypes.DEFAULT), MobCategory.MONSTER).sized(0.6F, 3.0F)
                    .clientTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> BADLANDS_ENDERMAN = ENTITIES.register("badlands_enderman",
            () -> EntityType.Builder.of(BaseEnderman.of(EndermanTypes.BADLANDS), MobCategory.MONSTER).sized(0.6F, 3.0F)
                    .clientTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> CAVE_ENDERMAN = ENTITIES.register("cave_enderman",
            () -> EntityType.Builder.of(BaseEnderman.of(EndermanTypes.CAVE), MobCategory.MONSTER).sized(0.6F, 3.0F)
                    .clientTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));

    public static final Supplier<EntityType<PassiveEnderman>> FLOWER_ENDERMAN = ENTITIES.register("flower_fields_enderman",
            () -> EntityType.Builder.of(PassiveEnderman.of(EndermanTypes.FLOWER), MobCategory.MONSTER).sized(0.5F, 1.5F)
                    .clientTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(id));
    }

    public static void registerEntityAttributes() {
        EntityAttributeRegistry.register(DEFAULT_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(BADLANDS_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(CAVE_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(FLOWER_ENDERMAN, PassiveEnderman.createAttributes());
    }
}
