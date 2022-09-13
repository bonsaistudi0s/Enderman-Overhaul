package io.github.padlocks.EndermanOverhaul.common.entity;

import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import static io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul.resourceLocation;

public class EndermanTypes {
    private static final DustParticleEffect SAND_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb((int) Long.parseLong("D3C7A2", 16))), 1f);
    private static final DustParticleEffect STONE_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb((int) Long.parseLong("97999B", 16))), 1f);
    public static final EndermanType DEFAULT = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/default/default_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/default/default_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/default_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            .setUsesAngryAnimation(true)
            .setRunsWhenAngry(false)
            .build();

    public static final EndermanType BADLANDS = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/badlands/badlands_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/badlands/badlands_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/badlands_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            //.setParticleEffect(SAND_PARTICLE)
            .setUsesAngryAnimation(false)
            .build();

    public static final EndermanType CAVE = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/cave/cave_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/cave/cave_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/cave_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            .setProvokedWithEyeContact(false)
            //.setParticleEffect(STONE_PARTICLE)
            .setChanceToSpawnWithRiches(2)
            .addBlockPickupRestriction(BlockTags.STONE_ORE_REPLACEABLES)
            .addBlockPickupRestriction(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
            .build();

    public static final EndermanType FLOWER = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/flower_fields_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
            .addAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
            .setProvokedWithEyeContact(false)
            .setTeleportOnInjury(true)
            .setCanPickupBlocks(false)
            .build();
}
