package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;

public class SwampEnderman extends BaseEnderman {

    public SwampEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 8;
        setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, 0.216)
            .add(Attributes.ATTACK_DAMAGE, 7.0)
            .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public static boolean checkMonsterSpawnRules(@NotNull EntityType<? extends Monster> type, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!EndermanOverhaulConfig.spawnSwampEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        return BaseEnderman.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, state -> {
            state.getController().setAnimation(state.getLimbSwingAmount() > 0.01 || state.getLimbSwingAmount() < -0.01 ?
                ConstantAnimations.WALK :
                ConstantAnimations.IDLE);
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "creepy_controller", 5, state -> {
            if (!canOpenMouth()) return PlayState.STOP;
            if (!isCreepy()) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ANGRY);
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "hold_controller", 5, state -> {
            if (!canPickupBlocks()) return PlayState.STOP;
            if (getCarriedBlock() == null) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.HOLDING);
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "attack_controller", 5, state -> {
            if (!playArmSwingAnimWhenAttacking()) return PlayState.STOP;
            if (getAttackAnim(state.getPartialTick()) == 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ATTACK);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public boolean playRunAnimWhenAngry() {
        return false;
    }

    @Override
    public boolean hasParticles() {
        return false;
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public double getVisionRange() {
        return 32.0;
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    @Override
    public @Nullable MobEffectInstance getHitEffect() {
        return new MobEffectInstance(new MobEffectInstance(MobEffects.POISON, 100, 2));
    }

    @Override
    public boolean canFloat() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.PLANT_ENDERMAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSoundEvents.PLANT_ENDERMAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.PLANT_ENDERMAN_HURT.get();
    }
}
