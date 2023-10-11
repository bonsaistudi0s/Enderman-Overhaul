package tech.alexnijjar.endermanoverhaul.common.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModEntityTypeTags {
    public static final TagKey<EntityType<?>> CANT_BE_TELEPORTED = tag("cant_be_teleported");
    public static final TagKey<EntityType<?>> ENDER_PEARLS = tag("ender_pearls");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(EndermanOverhaul.MOD_ID, name));
    }
}
