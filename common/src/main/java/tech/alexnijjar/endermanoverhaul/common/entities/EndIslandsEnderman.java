package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.BasePetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.EnderBullet;
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;

public class EndIslandsEnderman extends BaseEnderman {
    private static final EntityDataAccessor<Boolean> DATA_POSSESSING = SynchedEntityData.defineId(EndIslandsEnderman.class, EntityDataSerializers.BOOLEAN);

    public EndIslandsEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 42;
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 80.0)
            .add(Attributes.MOVEMENT_SPEED, 0.21)
            .add(Attributes.ATTACK_DAMAGE, 10.0)
            .add(Attributes.FOLLOW_RANGE, 32);
    }

    public static boolean checkMonsterSpawnRules(@NotNull EntityType<? extends Monster> type, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!EndermanOverhaulConfig.spawnEndIslandsEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        if (random.nextInt(5) != 0) return false;
        return BaseEnderman.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(new AnimationController<>(this, "possessing_controller", 5, state -> {
            if (!entityData.get(DATA_POSSESSING)) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.POSSESS);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new EndIslandsEndermanSummonProjectileGoal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_POSSESSING, false);
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    @Override
    public boolean playRunAnimWhenAngry() {
        return false;
    }

    @Override
    public boolean canShake() {
        return false;
    }

    @Override
    public boolean isProvokedByEyeContact() {
        return false;
    }

    @Override
    public boolean isAlwaysHostile() {
        return true;
    }

    @Override
    public boolean canTeleport() {
        return false;
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
    public boolean speedUpWhenAngry() {
        return false;
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        if (getTarget() != null) {
            spawnProjectiles(getTarget());
        }
    }

    public void spawnProjectiles(Entity target) {
        for (int i = 0; i < 3; i++) {
            EnderBullet bullet = new EnderBullet(level(), this, target, getDirection().getAxis());
            bullet.setPos(
                getX() + (random.nextDouble() - 0.5) * 2.0,
                getY() + 1 + (random.nextDouble() - 0.5) * 2.0,
                getZ() + (random.nextDouble() - 0.5) * 2.0
            );
            level().addFreshEntity(bullet);
        }
    }

    public boolean isPossessing() {
        return entityData.get(DATA_POSSESSING);
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.TALL_ENDERMAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSoundEvents.TALL_ENDERMAN_DEATH.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.TALL_ENDERMAN_DEATH.get();
    }

    @Override
    public SoundEvent getStareSound() {
        return ModSoundEvents.TALL_ENDERMAN_STARE.get();
    }

    public class EndIslandsEndermanSummonProjectileGoal extends Goal {
        protected int summonTicks;
        protected int nextAttackTickCount = 400;

        @Override
        public boolean canUse() {
            LivingEntity target = getTarget();
            if (target != null && target.isAlive()) {
                if (isPossessing()) {
                    if (tickCount + 500 >= this.nextAttackTickCount) {
                        entityData.set(DATA_POSSESSING, false);
                        return true;
                    }
                    return false;
                } else {
                    return tickCount >= this.nextAttackTickCount;
                }
            } else {
                return false;
            }
        }

        @Override
        public boolean canContinueToUse() {
            LivingEntity target = getTarget();
            return target != null && target.isAlive() && this.summonTicks > 0;
        }

        @Override
        public void start() {
            entityData.set(DATA_POSSESSING, true);
            playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0f, 1.0f);
            this.nextAttackTickCount = tickCount + 400;
            this.summonTicks = 28;
        }

        @Override
        public void stop() {
            super.stop();
            LivingEntity target = getTarget();
            if (target == null) return;
            entityData.set(DATA_POSSESSING, false);
            level().getEntities(EndIslandsEnderman.this, getBoundingBox().inflate(15.0)).stream()
                .filter(entity -> entity instanceof EnderMan)
                .filter(entity -> !(entity instanceof BasePetEnderman))
                .forEach(entity -> ((EnderMan) entity).setTarget(target));

            playSound(SoundEvents.SHULKER_SHOOT, 1.0f, 1.0f);
            spawnProjectiles(target);
        }

        @Override
        public void tick() {
            --this.summonTicks;
            navigation.stop();
        }
    }
}
