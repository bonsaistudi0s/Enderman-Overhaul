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
    private static final DustParticleEffect SNOW_PARTICLE = new DustParticleEffect(new Vec3f(Vec3d.unpackRgb((int) Long.parseLong("FFFFFF", 16))), 1f);
    public static final EndermanType DEFAULT = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/default/default_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/default/default_enderman_glow.png"))
            .setModel(resourceLocation("geo/default_enderman.geo.json"))
            .setUsesAngryAnimation(true)
            .setRunsWhenAngry(false)
            .build();

    public static final EndermanType BADLANDS = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/badlands/badlands_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/badlands/badlands_enderman_glow.png"))
            .setModel(resourceLocation("geo/badlands_enderman.geo.json"))
            .setParticleEffect(SAND_PARTICLE)
            .setUsesAngryAnimation(false)
            .build();

    public static final EndermanType CAVE = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/cave/cave_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/cave/cave_enderman_glow.png"))
            .setModel(resourceLocation("geo/cave_enderman.geo.json"))
            .setProvokedWithEyeContact(false)
            .setParticleEffect(STONE_PARTICLE)
            .setChanceToSpawnWithRiches(100)
            .addBlockPickupRestriction(BlockTags.STONE_ORE_REPLACEABLES)
            .addBlockPickupRestriction(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
            .build();

    public static final EndermanType DESERT = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/desert/desert_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/desert/desert_enderman_glow.png"))
            .setModel(resourceLocation("geo/desert_enderman.geo.json"))
            .setIdleAnimation("animation.desert_enderman.idle")
            .setWalkAnimation("animation.desert_enderman.walk")
            .setProvokedWithEyeContact(true)
            .setUsesAngryAnimation(false)
            .setRunsWhenAngry(false)
            .setCanPickupBlocks(false)
            .setParticleEffect(SAND_PARTICLE)
            .build();

    public static final EndermanType SAVANNA = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/savanna/savanna_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/savanna/savanna_enderman_glow.png"))
            .setModel(resourceLocation("geo/savanna_enderman.geo.json"))
            .setProvokedWithEyeContact(true)
            .setUsesAngryAnimation(false)
            .setRunsWhenAngry(true)
            .setCanPickupBlocks(true)
            .build();

    public static final EndermanType SNOWY = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/snowy/snowy_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/snowy/snowy_enderman_glow.png"))
            .setModel(resourceLocation("geo/snowy_enderman.geo.json"))
            .setIdleAnimation("animation.snowy_enderman.idle")
            .setProvokedWithEyeContact(true)
            .setUsesAngryAnimation(false)
            .setRunsWhenAngry(true)
            .setParticleEffect(SNOW_PARTICLE)
            .build();

    public static final EndermanType FLOWER = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman_glow.png"))
            .setModel(resourceLocation("geo/flower_fields_enderman.geo.json"))
            .setWalkAnimation("animation.flower_fields_enderman.walk")
            .addAttribute(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
            .addAttribute(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
            .setProvokedWithEyeContact(false)
            .setTeleportOnInjury(true)
            .setCanPickupBlocks(false)
            .build();
}
