package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

public class OceanEnderman extends BaseEnderman {
    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;

    public OceanEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 6;
        setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
        this.moveControl = new OceanEndermanMoveControl();
        this.waterNavigation = new WaterBoundPathNavigation(this, level);
        this.groundNavigation = new GroundPathNavigation(this, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.MOVEMENT_SPEED, 0.13)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    public static boolean checkMonsterSpawnRules(@NotNull EntityType<? extends Monster> type, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!EndermanOverhaulConfig.spawnOceanEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        return BaseEnderman.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @SuppressWarnings({"deprecation", "unused"})
    public static boolean checkSpawnRules(EntityType<OceanEnderman> enderman, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        if (!EndermanOverhaulConfig.spawnOceanEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        if (!serverLevel.getFluidState(pos.below()).is(FluidTags.WATER)) return false;
        boolean bl = serverLevel.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(serverLevel, pos, random) && (mobSpawnType == MobSpawnType.SPAWNER || serverLevel.getFluidState(pos).is(FluidTags.WATER));
        return random.nextInt(40) == 0 && pos.getY() < serverLevel.getSeaLevel() - 5 && bl;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 0, state -> {
            if (isInWater()) {
                state.getController().setAnimation(ConstantAnimations.SWIM);
                return PlayState.CONTINUE;
            }
            state.getController().setAnimation(state.getLimbSwingAmount() > 0.1 || state.getLimbSwingAmount() < -0.1 ?
                ConstantAnimations.WALK :
                ConstantAnimations.IDLE);
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "attack_controller", 5, state -> {
            if (!playArmSwingAnimWhenAttacking()) return PlayState.STOP;
            if (getAttackAnim(state.getPartialTick()) == 0) return PlayState.STOP;
            if (getTarget() == null) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ATTACK);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new EndermanFreezeWhenLookedAt());
        this.goalSelector.addGoal(1, new OceanEndermanSwimGoal());
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
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
    public boolean playRunAnimWhenAngry() {
        return false;
    }

    @Override
    public @Nullable ParticleOptions getCustomParticles() {
        return ModParticleTypes.BUBBLE.get();
    }

    @Override
    public int getParticleCount() {
        return 1;
    }

    @Override
    public int getParticleRate() {
        return 4;
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean canTeleport() {
        return isCreepy() || getAirSupply() <= 20;
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
        return !this.isSwimming();
    }

    @Override
    public void baseTick() {
        int airSupply = this.getAirSupply();
        super.baseTick();
        this.handleAirSupply(airSupply);
    }

    @Override
    public boolean speedUpWhenAngry() {
        return false;
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
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isControlledByLocalInstance() && this.isInWater()) {
            this.moveRelative(0.01f, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(travelVector);
        }
        super.travel(travelVector);
    }

    @Override
    public void updateSwimming() {
        if (!this.level().isClientSide) {
            if (this.isEffectiveAi() && level().getBlockState(blockPosition().above().above()).is(Blocks.WATER)) {
                this.navigation = this.waterNavigation;
                this.setSwimming(true);
            } else {
                this.navigation = this.groundNavigation;
                this.setSwimming(false);
            }
        }
        super.updateSwimming();
    }

    protected void handleAirSupply(int airSupply) {
        if (this.isAlive() && !this.isInWaterOrBubble()) {
            this.setAirSupply(airSupply - 1);
            if (this.getAirSupply() <= 20 && level().getGameTime() % 20 == 0) {
                if (!teleportToWater()) {
                    for (int i = 0; i < 64; i++) {
                        if (this.teleport()) break;
                    }
                }
            }
            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(this.damageSources().drown(), 2.0f);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    private boolean teleportToWater() {
        RandomSource randomSource = getRandom();
        BlockPos blockPos = blockPosition();

        for (int i = 0; i < 100; ++i) {
            BlockPos blockPos2 = blockPos.offset(randomSource.nextInt(30) - 10, 2 - randomSource.nextInt(8), randomSource.nextInt(30) - 10);
            if (level().getBlockState(blockPos2).is(Blocks.WATER)) {
                Vec3 pos = Vec3.atBottomCenterOf(blockPos2);
                if (pos == null) return false;
                level().gameEvent(GameEvent.TELEPORT, position(), GameEvent.Context.of(this));
                teleportTo(pos.x() + 0.5, pos.y(), pos.z() + 0.5);
                playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
                return true;
            }
        }

        return false;
    }

    private class OceanEndermanMoveControl extends MoveControl {

        OceanEndermanMoveControl() {
            super(OceanEnderman.this);
        }

        @Override
        public void tick() {
            if (!isInWater()) {
                super.tick();
                return;
            }

            LivingEntity livingEntity = getTarget();
            if (livingEntity != null && livingEntity.getY() > getY()) {
                setDeltaMovement(getDeltaMovement().add(0.0, 0.002, 0.0));
            }

            if (this.operation != MoveControl.Operation.MOVE_TO || getNavigation().isDone()) {
                setSpeed(0.0F);
                return;
            }

            double d = this.wantedX - getX();
            double e = this.wantedY - getY();
            double f = this.wantedZ - getZ();
            double g = Math.sqrt(d * d + e * e + f * f);
            e /= g;
            float h = (float) (Mth.atan2(f, d) * 57.2957763671875) - 90.0F;
            setYRot(this.rotlerp(getYRot(), h, 90.0F));
            yBodyRot = getYRot();
            float i = (float) (this.speedModifier * getAttributeValue(Attributes.MOVEMENT_SPEED));
            float j = Mth.lerp(0.125F, getSpeed(), i);
            setSpeed(j);
            j *= 15;
            setDeltaMovement(getDeltaMovement().add(j * d * 0.005, j * e * 0.025, j * f * 0.005));

            if (isCreepy()) {
                addDeltaMovement(new Vec3(getDeltaMovement().x * 0.18, 0, getDeltaMovement().z * 0.18));
            }
        }
    }

    private class OceanEndermanSwimGoal extends RandomSwimmingGoal {

        public OceanEndermanSwimGoal() {
            super(OceanEnderman.this, 1.0, 40);
        }

        public boolean canUse() {
            return super.canUse() && !isCreepy();
        }
    }
}
