package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.summons.Spirit;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

public class SoulsandValleyEnderman extends BaseEnderman {
    private static final EntityDataAccessor<Integer> DATA_BITING_TICKS = SynchedEntityData.defineId(SoulsandValleyEnderman.class, EntityDataSerializers.INT);

    public SoulsandValleyEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 8;
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
            .add(Attributes.ATTACK_DAMAGE, 10.0)
            .add(Attributes.FOLLOW_RANGE, 24)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(new AnimationController<>(this, "bite_controller", state -> {
            if (entityData.get(DATA_BITING_TICKS) <= 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.BITE);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public void tick() {
        if (entityData.get(DATA_BITING_TICKS) > 0) {
            entityData.set(DATA_BITING_TICKS, entityData.get(DATA_BITING_TICKS) - 1);
        }
        super.tick();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BITING_TICKS, 0);
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
    public boolean isProvokedByEyeContact() {
        return false;
    }

    @Override
    public @Nullable ParticleOptions getCustomParticles() {
        return ModParticleTypes.SOUL_FIRE_FLAME.get();
    }

    @Override
    public boolean isAlwaysHostile() {
        return true;
    }

    @Override
    public double getVisionRange() {
        return 32.0;
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (super.doHurtTarget(target)) {
            this.playSound(SoundEvents.PHANTOM_BITE, 10.0f, 0.95f + this.random.nextFloat() * 0.1f);
            entityData.set(DATA_BITING_TICKS, 7);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        for (int i = 0; i < 3; i++) {
            Spirit spirit = ModEntityTypes.SPIRIT.get().create(level());
            if (spirit == null) return;
            spirit.setPos(getX(), getY() + 2, getZ());
            spirit.addDeltaMovement(new Vec3(0, 0.5, 0));
            level().addFreshEntity(spirit);
        }

        for (int i = 0; i < 10; ++i) {
            this.level().addParticle(ParticleTypes.SOUL,
                this.getRandomX(0.5),
                this.getRandomY() - 1.25,
                this.getRandomZ(0.5),
                (this.random.nextDouble() - 0.5) * 0.5, -this.random.nextDouble(),
                (this.random.nextDouble() - 0.5) * 0.5);
        }
    }
}
