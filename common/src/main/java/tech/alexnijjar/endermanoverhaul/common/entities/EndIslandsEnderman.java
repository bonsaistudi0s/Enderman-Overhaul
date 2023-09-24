package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;

public class EndIslandsEnderman extends BaseEnderman {
    private static final EntityDataAccessor<Integer> DATA_POSSESSING_TICKS = SynchedEntityData.defineId(EndIslandsEnderman.class, EntityDataSerializers.INT);

    public EndIslandsEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 42;
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 80.0)
            .add(Attributes.MOVEMENT_SPEED, 0.35)
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
        controllerRegistrar.add(new AnimationController<>(this, "possessing_controller", state -> {
            if (entityData.get(DATA_POSSESSING_TICKS) <= 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.POSSESS);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public void tick() {
        if (isPossessing()) {
            entityData.set(DATA_POSSESSING_TICKS, entityData.get(DATA_POSSESSING_TICKS) - 1);
            this.getNavigation().stop();
        }
        super.tick();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_POSSESSING_TICKS, 0);
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
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (level().getGameTime() % 200 == 0 && random.nextBoolean()) {
            possess(getTarget());
        }
    }

    // anger all enderman in the nearby area
    private void possess(LivingEntity target) {
        if (target == null) return;
        entityData.set(DATA_POSSESSING_TICKS, 64);
        playSound(SoundEvents.EVOKER_CAST_SPELL, 1.0f, 1.0f);
        level().getEntities(this, getBoundingBox().inflate(15.0)).stream()
            .filter(entity -> entity instanceof EnderMan)
            .forEach(entity -> ((EnderMan) entity).setTarget(target));
    }

    public boolean isPossessing() {
        return entityData.get(DATA_POSSESSING_TICKS) > 0;
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
}
