package io.github.padlocks.EndermanOverhaul.common.entity;

import com.mojang.math.Vector3f;
import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import static io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul.resourceLocation;

public class EndermanTypes {
    private static final DustParticleOptions SAND_PARTICLE = new DustParticleOptions(new Vector3f(Vec3.fromRGB24((int) Long.parseLong("D3C7A2", 16))), 1f);
    private static final DustParticleOptions STONE_PARTICLE = new DustParticleOptions(new Vector3f(Vec3.fromRGB24((int) Long.parseLong("97999B", 16))), 1f);
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
            .setParticleEffect(SAND_PARTICLE)
            .setUsesAngryAnimation(false)
            .build();

    public static final EndermanType CAVE = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/cave/cave_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/cave/cave_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/cave_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            .setProvokedWithEyeContact(false)
            .setParticleEffect(STONE_PARTICLE)
            .setChanceToSpawnWithRiches(2)
            .addBlockPickupRestriction(BlockTags.STONE_ORE_REPLACEABLES)
            .addBlockPickupRestriction(BlockTags.DEEPSLATE_ORE_REPLACEABLES)
            .build();

    public static final EndermanType FLOWER = new EndermanType.Builder()
            .setTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman.png"))
            .setGlowingTexture(resourceLocation("textures/entity/flower_fields/flower_fields_enderman_glow.png"))
            .setModel(resourceLocation("pinwheel/geometry/flower_fields_enderman.json"))
            .setAnimation(resourceLocation("pinwheel/animations/default_enderman.json"))
            .addAttribute(Attributes.MAX_HEALTH, 20.0)
            .addAttribute(Attributes.MOVEMENT_SPEED, 0.2)
            .setProvokedWithEyeContact(false)
            .setTeleportOnInjury(true)
            .setCanPickupBlocks(false)
            .build();
}
