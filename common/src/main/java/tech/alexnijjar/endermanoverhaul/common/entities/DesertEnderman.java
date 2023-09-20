package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

public class DesertEnderman extends BaseEnderman {

    public DesertEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    @Override
    public boolean canRunWhenAngry() {
        return false;
    }

    @Override
    public @Nullable ParticleOptions getCustomParticles() {
        return ModParticleTypes.SAND.get();
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        // TODO Spawns 3 scarabs on death
    }
}
