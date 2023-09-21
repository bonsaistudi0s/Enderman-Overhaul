package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

public class OceanEnderman extends BaseEnderman {

    public OceanEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 6;
        setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.moveControl = new OceanEndermanMoveControl();
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static boolean checkSpawnRules(EntityType<OceanEnderman> enderman, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        if (!serverLevel.getFluidState(pos.below()).is(FluidTags.WATER)) return false;
        boolean bl = serverLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(serverLevel, pos, random) && (mobSpawnType == MobSpawnType.SPAWNER || serverLevel.getFluidState(pos).is(FluidTags.WATER));
        return random.nextInt(40) == 0 && pos.getY() < serverLevel.getSeaLevel() - 5 && bl;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            state.getController().setAnimation(ConstantAnimations.SWIM);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EndermanFreezeWhenLookedAt());
        this.goalSelector.addGoal(2, new OceanEndermanSwimGoal());
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
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
        return ModParticleTypes.BUBBLE.get();
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean canTeleport() {
        return false;
    }

    @Override
    public double getVisionRange() {
        return 64.0;
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void baseTick() {
        int airSupply = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(airSupply);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return level.isUnobstructed(this);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level) {
        return new WaterBoundPathNavigation(this, level);
    }

    @Override
    public void aiStep() {
        if (!this.isInWater() && this.onGround() && this.verticalCollision) {
            this.setDeltaMovement(this.getDeltaMovement().add((this.random.nextFloat() * 2.0f - 1.0f) * 0.05f, 0.4000000059604645, (this.random.nextFloat() * 2.0f - 1.0f) * 0.05f));
            this.setOnGround(false);
            this.hasImpulse = true;
            this.playSound(SoundEvents.TADPOLE_FLOP, this.getSoundVolume(), this.getVoicePitch());
        }

        super.aiStep();
    }

    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0f);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    private class OceanEndermanMoveControl extends MoveControl {

        OceanEndermanMoveControl() {
            super(OceanEnderman.this);
        }

        public void tick() {
            if (OceanEnderman.this.isEyeInFluid(FluidTags.WATER)) {
                OceanEnderman.this.setDeltaMovement(OceanEnderman.this.getDeltaMovement().add(0.0, 0.005, 0.0));
            }

            if (this.operation == MoveControl.Operation.MOVE_TO && !OceanEnderman.this.getNavigation().isDone()) {
                float f = (float) (this.speedModifier * OceanEnderman.this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                if (OceanEnderman.this.isCreepy()) f *= 3;
                OceanEnderman.this.setSpeed(Mth.lerp(0.125f, OceanEnderman.this.getSpeed(), f));
                double d = this.wantedX - OceanEnderman.this.getX();
                double e = this.wantedY - OceanEnderman.this.getY();
                double g = this.wantedZ - OceanEnderman.this.getZ();
                if (e != 0.0) {
                    double h = Math.sqrt(d * d + e * e + g * g);
                    OceanEnderman.this.setDeltaMovement(OceanEnderman.this.getDeltaMovement().add(0.0, (double) OceanEnderman.this.getSpeed() * (e / h) * 0.1, 0.0));
                }

                if (d != 0.0 || g != 0.0) {
                    float i = (float) (Mth.atan2(g, d) * 57.3) - 90.0f;
                    OceanEnderman.this.setYRot(this.rotlerp(OceanEnderman.this.getYRot(), i, 90.0f));
                    OceanEnderman.this.yBodyRot = OceanEnderman.this.getYRot();
                }

            } else {
                OceanEnderman.this.setSpeed(0.0F);
            }
        }
    }

    private class OceanEndermanSwimGoal extends RandomSwimmingGoal {

        public OceanEndermanSwimGoal() {
            super(OceanEnderman.this, 1.0, 40);
        }

        public boolean canUse() {
            return super.canUse() && !OceanEnderman.this.isCreepy();
        }
    }
}
