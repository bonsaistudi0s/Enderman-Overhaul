package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.EntityAttributeRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.entity.EndermanTypes;
import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.common.entity.base.PassiveEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class ModEntities {
    public static final PollinatedRegistry<EntityType<?>> ENTITIES = PollinatedRegistry.create(Registry.ENTITY_TYPE, EndermanOverhaul.MOD_ID);
//    public static final Supplier<EntityType<BaseEnderman>> DEFAULT_ENDERMAN = ENTITIES.register("default_enderman",
//            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.DEFAULT), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
//                    .maxTrackingRange(8)
//                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> BADLANDS_ENDERMAN = ENTITIES.register("badlands_enderman",
            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.BADLANDS), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> CAVE_ENDERMAN = ENTITIES.register("cave_enderman",
            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.CAVE), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> DESERT_ENDERMAN = ENTITIES.register("desert_enderman",
            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.DESERT), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> SAVANNA_ENDERMAN = ENTITIES.register("savanna_enderman",
            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.SAVANNA), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));
    public static final Supplier<EntityType<BaseEnderman>> SNOWY_ENDERMAN = ENTITIES.register("snowy_enderman",
            () -> EntityType.Builder.create(BaseEnderman.of(EndermanTypes.SNOWY), SpawnGroup.MONSTER).setDimensions(0.6F, 3.0F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));

    public static final Supplier<EntityType<BaseEnderman>> FLOWER_ENDERMAN = ENTITIES.register("flower_fields_enderman",
            () -> EntityType.Builder.create(PassiveEnderman.of(EndermanTypes.FLOWER), SpawnGroup.MONSTER).setDimensions(0.5F, 1.5F)
                    .maxTrackingRange(8)
                    .build(EndermanOverhaul.MOD_ID));

    private static <T extends Entity> Supplier<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(id));
    }

    public static void registerEntityAttributes() {
        //EntityAttributeRegistry.register(DEFAULT_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(BADLANDS_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(CAVE_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(DESERT_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(SAVANNA_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(SNOWY_ENDERMAN, BaseEnderman.createAttributes());
        EntityAttributeRegistry.register(FLOWER_ENDERMAN, PassiveEnderman.createAttributes());
    }
}
