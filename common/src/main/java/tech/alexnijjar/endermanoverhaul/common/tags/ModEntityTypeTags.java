package tech.alexnijjar.endermanoverhaul.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> IGNORE_SOUL_PEARL = tag("ignore_soul_pearl");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(EndermanOverhaul.MOD_ID, name));
    }
}
