package tech.alexnijjar.endermanoverhaul.common.entities.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;

import java.util.EnumSet;
import java.util.function.Predicate;

public abstract class BaseEnderman extends EnderMan implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BaseEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            if (state.isMoving()) {
                state.getController().setAnimation(isCreepy() && canRunWhenAngry() ?
                    ConstantAnimations.RUN :
                    ConstantAnimations.WALK);
            } else {
                state.getController().setAnimation(ConstantAnimations.IDLE);
            }
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "creepy_controller", state -> {
            if (!canOpenMouth()) return PlayState.STOP;
            if (!isCreepy()) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ANGRY);
            return PlayState.CONTINUE;
        }));
    }

    public boolean canOpenMouth() {
        return true;
    }

    public boolean canRunWhenAngry() {
        return true;
    }

    public boolean isProvokedByEyeContact() {
        return true;
    }

    public boolean hasParticles() {
        return true;
    }

    @Nullable
    public ParticleOptions getCustomParticles() {
        return null;
    }

    public boolean canPickupBlocks() {
        return true;
    }

    public boolean isAlwaysHostile() {
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canTeleport() {
        return true;
    }

    public double getVisionRange() {
        return 256.0;
    }

    @Nullable
    public MobEffectInstance getHitEffect() {
        return null;
    }

    public TagKey<Block> getCarriableBlockTag() {
        return BlockTags.ENDERMAN_HOLDABLE;
    }

    @Nullable
    public MobEffectInstance getAreaEffect() {
        return null;
    }

    public int getAreaEffectRange() {
        return 0;
    }

    public boolean hasLargeCreepyHitbox() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        tickAreaEffect();
    }

    protected void tickAreaEffect() {
        if (level().isClientSide()) return;
        if (level().getGameTime() % 20 != 0) return;
        var areaEffect = getAreaEffect();
        if (areaEffect == null || getAreaEffectRange() == 0) return;
        LivingEntity target = getTarget();
        if (target == null) return;
        if (this.distanceToSqr(target) <= getAreaEffectRange() * getAreaEffectRange()) {
            target.addEffect(areaEffect);
        }
    }

    @Override
    public void aiStep() {
        ParticleOptions customParticleType = getCustomParticles();
        if (hasParticles() && this.level().isClientSide() && customParticleType != null) {
            for (int i = 0; i < 2; ++i) {
                this.level().addParticle(customParticleType,
                    this.getRandomX(0.5),
                    this.getRandomY() - 0.25,
                    this.getRandomZ(0.5),
                    (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(),
                    (this.random.nextDouble() - 0.5) * 2.0);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (super.doHurtTarget(target)) {
            var effect = getHitEffect();
            if (effect != null && target instanceof LivingEntity entity) {
                entity.addEffect(effect);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        if (!hasLargeCreepyHitbox() || !isCreepy()) return super.getDimensions(pose);
        return super.getDimensions(pose).scale(1, 1.15f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EndermanFreezeWhenLookedAt());
        if (isAlwaysHostile()) {
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        }
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new EndermanLeaveBlockGoal());
        this.goalSelector.addGoal(11, new EndermanTakeBlockGoal());
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    public boolean isLookingAtMe(Player player) {
        if (!this.isProvokedByEyeContact()) return false;
        ItemStack itemStack = player.getInventory().armor.get(3);
        if (itemStack.is(Blocks.CARVED_PUMPKIN.asItem())) return false;

        Vec3 vec3 = player.getViewVector(1.0F).normalize();
        Vec3 vec32 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec32.length();
        vec32 = vec32.normalize();
        double e = vec3.dot(vec32);
        return e > 1.0 - 0.025 / d && player.hasLineOfSight(this);
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
        return 2.55f + (dimensions.height - 2.9f);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        refreshDimensions();
    }

    @Override
    protected boolean teleport() {
        if (!canTeleport()) return false;
        return super.teleport();
    }

    public boolean teleportTowards(Entity target) {
        if (!this.canTeleport()) return false;
        Vec3 vec3 = new Vec3(this.getX() - target.getX(), this.getY(0.5) - target.getEyeY(), this.getZ() - target.getZ());
        vec3 = vec3.normalize();
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 16.0;
        double f = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 16.0;
        return this.teleport(e, f, g);
    }

    @SuppressWarnings("deprecation")
    public boolean teleport(double x, double y, double z) {
        if (!this.canTeleport()) return false;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(x, y, z);

        while (mutableBlockPos.getY() > this.level().getMinBuildHeight() && !this.level().getBlockState(mutableBlockPos).blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        BlockState blockState = this.level().getBlockState(mutableBlockPos);
        boolean bl = blockState.blocksMotion();
        boolean bl2 = blockState.getFluidState().is(FluidTags.WATER);
        if (bl && !bl2) {
            Vec3 vec3 = this.position();
            boolean bl3 = this.randomTeleport(x, y, z, true);
            if (bl3) {
                this.level().gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(this));
                if (!this.isSilent()) {
                    this.level().playSound(null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }

            return bl3;
        } else {
            return false;
        }
    }

    public class EndermanTakeBlockGoal extends Goal {
        public boolean canUse() {
            if (!canPickupBlocks()) return false;
            if (BaseEnderman.this.getCarriedBlock() != null) {
                return false;
            } else if (!BaseEnderman.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return false;
            } else {
                return BaseEnderman.this.getRandom().nextInt(reducedTickDelay(20)) == 0;
            }
        }

        public void tick() {
            RandomSource randomSource = BaseEnderman.this.getRandom();
            Level level = BaseEnderman.this.level();
            int i = Mth.floor(BaseEnderman.this.getX() - 2.0 + randomSource.nextDouble() * 4.0);
            int j = Mth.floor(BaseEnderman.this.getY() + randomSource.nextDouble() * 3.0);
            int k = Mth.floor(BaseEnderman.this.getZ() - 2.0 + randomSource.nextDouble() * 4.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            Vec3 vec3 = new Vec3((double) BaseEnderman.this.getBlockX() + 0.5, j + 0.5, BaseEnderman.this.getBlockZ() + 0.5);
            Vec3 vec32 = new Vec3(i + 0.5, j + 0.5, k + 0.5);
            BlockHitResult blockHitResult = level.clip(new ClipContext(vec3, vec32, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, BaseEnderman.this));
            boolean bl = blockHitResult.getBlockPos().equals(blockPos);
            if (blockState.is(getCarriableBlockTag()) && bl) {
                level.removeBlock(blockPos, false);
                level.gameEvent(GameEvent.BLOCK_DESTROY, blockPos, GameEvent.Context.of(BaseEnderman.this, blockState));
                BaseEnderman.this.setCarriedBlock(blockState.getBlock().defaultBlockState());
            }
        }
    }

    public class EndermanLeaveBlockGoal extends Goal {
        public boolean canUse() {
            if (BaseEnderman.this.getCarriedBlock() == null) {
                return false;
            } else if (!BaseEnderman.this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return false;
            } else {
                return BaseEnderman.this.getRandom().nextInt(reducedTickDelay(2000)) == 0;
            }
        }

        public void tick() {
            RandomSource randomSource = BaseEnderman.this.getRandom();
            Level level = BaseEnderman.this.level();
            int i = Mth.floor(BaseEnderman.this.getX() - 1.0 + randomSource.nextDouble() * 2.0);
            int j = Mth.floor(BaseEnderman.this.getY() + randomSource.nextDouble() * 2.0);
            int k = Mth.floor(BaseEnderman.this.getZ() - 1.0 + randomSource.nextDouble() * 2.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = level.getBlockState(blockPos2);
            BlockState blockState3 = BaseEnderman.this.getCarriedBlock();
            if (blockState3 != null) {
                blockState3 = Block.updateFromNeighbourShapes(blockState3, BaseEnderman.this.level(), blockPos);
                if (this.canPlaceBlock(level, blockPos, blockState3, blockState, blockState2, blockPos2)) {
                    level.setBlock(blockPos, blockState3, 3);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(BaseEnderman.this, blockState3));
                    BaseEnderman.this.setCarriedBlock(null);
                }
            }
        }

        private boolean canPlaceBlock(Level level, BlockPos destinationPos, BlockState carriedState, BlockState destinationState, BlockState belowDestinationState, BlockPos belowDestinationPos) {
            return destinationState.isAir() && !belowDestinationState.isAir() && !belowDestinationState.is(Blocks.BEDROCK) && belowDestinationState.isCollisionShapeFullBlock(level, belowDestinationPos) && carriedState.canSurvive(level, destinationPos) && level.getEntities(BaseEnderman.this, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(destinationPos))).isEmpty();
        }
    }

    public class EndermanFreezeWhenLookedAt extends Goal {
        @Nullable
        private LivingEntity target;

        public EndermanFreezeWhenLookedAt() {
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            this.target = BaseEnderman.this.getTarget();
            if (!(this.target instanceof Player)) {
                return false;
            } else {
                double d = this.target.distanceToSqr(BaseEnderman.this);
                return !(d > getVisionRange()) && BaseEnderman.this.isLookingAtMe((Player) this.target);
            }
        }

        public void start() {
            BaseEnderman.this.getNavigation().stop();
        }

        public void tick() {
            if (target == null) return;
            BaseEnderman.this.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    public class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
        private final Predicate<LivingEntity> isAngerInducing;

        public EndermanLookForPlayerGoal(@Nullable Predicate<LivingEntity> predicate) {
            super(BaseEnderman.this, Player.class, 10, false, false, predicate);
            this.isAngerInducing = (livingEntity) ->
                (BaseEnderman.this.isLookingAtMe((Player) livingEntity) ||
                    BaseEnderman.this.isAngryAt(livingEntity)) &&
                    !BaseEnderman.this.hasIndirectPassenger(livingEntity);
            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
        }

        public boolean canUse() {
            this.pendingTarget = BaseEnderman.this.level().getNearestPlayer(this.startAggroTargetConditions, BaseEnderman.this);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.adjustedTickDelay(5);
            this.teleportTime = 0;
            BaseEnderman.this.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.isAngerInducing.test(this.pendingTarget)) {
                    return false;
                } else {
                    BaseEnderman.this.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                if (this.target != null) {
                    if (BaseEnderman.this.hasIndirectPassenger(this.target)) {
                        return false;
                    }

                    if (this.continueAggroTargetConditions.test(BaseEnderman.this, this.target)) {
                        return true;
                    }
                }

                return super.canContinueToUse();
            }
        }

        public void tick() {
            if (BaseEnderman.this.getTarget() == null) {
                super.setTarget(null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !BaseEnderman.this.isPassenger()) {
                    if (BaseEnderman.this.isLookingAtMe((Player) this.target)) {
                        if (this.target.distanceToSqr(BaseEnderman.this) < 16.0) {
                            BaseEnderman.this.teleport();
                        }

                        this.teleportTime = 0;
                    } else if (this.target.distanceToSqr(BaseEnderman.this) > getVisionRange() && this.teleportTime++ >= this.adjustedTickDelay(30) && BaseEnderman.this.teleportTowards(this.target)) {
                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }
        }
    }
}
