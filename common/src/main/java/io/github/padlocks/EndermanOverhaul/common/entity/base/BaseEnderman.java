//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package io.github.padlocks.EndermanOverhaul.common.entity.base;

import gg.moonflower.pollen.api.entity.PollenEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BaseEnderman extends Monster implements NeutralMob, AnimatedEntity, PollenEntity {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier SPEED_MODIFIER_ATTACKING;
    private static final int DELAY_BETWEEN_CREEPY_STARE_SOUND = 400;
    private static final int MIN_DEAGGRESSION_TIME = 600;
    private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE;
    private static final EntityDataAccessor<Boolean> DATA_CREEPY;
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT;
    private final EndermanType type;
    private int lastStareSound = Integer.MIN_VALUE;
    private int targetChangeTime;
    private static final UniformInt PERSISTENT_ANGER_TIME;
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    public static final AnimationState WALK_ANIMATION = new AnimationState(20);
    public static final AnimationState RUN_ANIMATION = new AnimationState(20);
    public static final AnimationState IDLE_ANIMATION = new AnimationState(20);
    public static final AnimationState ANGRY_ANIMATION = new AnimationState(60);
    private AnimationState animationState;
    private int animationTick;

    public BaseEnderman(EntityType<? extends BaseEnderman> entityType, Level level, EndermanType type) {
        super(entityType, level);
        this.type = type;
        this.maxUpStep = 1.0F;
        this.animationState = AnimationState.EMPTY;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);

        if (this.type.chanceToSpawnWithRiches() > 0) {
            if (random.nextInt(1, 101) <= this.type.chanceToSpawnWithRiches()) {
                ArrayList<BlockState> blocks = new ArrayList<>();
                blocks.add(Blocks.COAL_BLOCK.defaultBlockState());
                blocks.add(Blocks.IRON_BLOCK.defaultBlockState());
                blocks.add(Blocks.GOLD_BLOCK.defaultBlockState());
                blocks.add(Blocks.EMERALD_BLOCK.defaultBlockState());
                this.setCarriedBlock(blocks.get(random.nextInt(0, blocks.size())));
            }
        }
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EndermanFreezeWhenLookedAt(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new EndermanLeaveBlockGoal(this));
        this.goalSelector.addGoal(11, new EndermanTakeBlockGoal(this));
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal(this, false));
    }

    public EndermanType getEndermanType() {
        return this.type;
    }

    @Override
    public void tick() {
        super.tick();
        this.animationTick();
    }

    @Override
    public int getAnimationTick() {
        return 0;
    }

    @Override
    public int getAnimationTransitionTick() {
        return 0;
    }

    @Override
    public int getAnimationTransitionLength() {
        return 0;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = animationTick;
    }

    @Override
    public void setAnimationTransitionTick(int transitionTick) {

    }

    @Override
    public void setAnimationTransitionLength(int transitionLength) {

    }

    @Override
    public AnimationState getAnimationState() {
        return this.animationState;
    }

    @Override
    public AnimationState getTransitionAnimationState() {
        return null;
    }

    @Override
    public void setAnimationState(AnimationState state) {
        this.onAnimationStop(this.animationState);
        this.animationState = state;
        this.setAnimationTick(0);
    }

    @Override
    public void setTransitionAnimationState(AnimationState state) {

    }

    @Override
    public @Nullable AnimationEffectHandler getAnimationEffects() {
        return null;
    }

    @Override
    public AnimationState[] getAnimationStates() {
        AnimationState[] states = new AnimationState[] {
                WALK_ANIMATION,
                RUN_ANIMATION,
                IDLE_ANIMATION
        };
        if (this.type.usesAngryAnimation()) {
            states = new AnimationState[] {
                    WALK_ANIMATION,
                    RUN_ANIMATION,
                    IDLE_ANIMATION,
                    ANGRY_ANIMATION
            };
        }
        return states;
    }

    public static Supplier<AttributeSupplier.Builder> createAttributes() {
        return () -> Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896)
                .add(Attributes.ATTACK_DAMAGE, 7.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    public double getAttributeValue(@NotNull Attribute attribute) {
        return this.getAttribute(attribute).getValue();
    }

    public static EntityType.EntityFactory<BaseEnderman> of(EndermanType type) {
        return (entityType, level) -> new BaseEnderman(entityType, level, type);
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        super.setTarget(livingEntity);
        AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (livingEntity == null) {
            this.targetChangeTime = 0;
            this.entityData.set(DATA_CREEPY, false);
            this.entityData.set(DATA_STARED_AT, false);
            attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.targetChangeTime = this.tickCount;
            this.entityData.set(DATA_CREEPY, true);
            if (!attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeInstance.addTransientModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CARRY_STATE, Optional.empty());
        this.entityData.define(DATA_CREEPY, false);
        this.entityData.define(DATA_STARED_AT, false);
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    public void setRemainingPersistentAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    public int getRemainingPersistentAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setPersistentAngerTarget(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void playStareSound() {
        if (this.tickCount >= this.lastStareSound + 400) {
            this.lastStareSound = this.tickCount;
            if (!this.isSilent()) {
                this.level.playLocalSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENDERMAN_STARE, this.getSoundSource(), 2.5F, 1.0F, false);
            }
        }

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        if (DATA_CREEPY.equals(entityDataAccessor) && this.hasBeenStaredAt() && this.level.isClientSide) {
            this.playStareSound();
        }

        super.onSyncedDataUpdated(entityDataAccessor);
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            compoundTag.put("carriedBlockState", NbtUtils.writeBlockState(blockState));
        }

        this.addPersistentAngerSaveData(compoundTag);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        BlockState blockState = null;
        if (compoundTag.contains("carriedBlockState", 10)) {
            blockState = NbtUtils.readBlockState(compoundTag.getCompound("carriedBlockState"));
            if (blockState.isAir()) {
                blockState = null;
            }
        }

        this.setCarriedBlock(blockState);
        this.readPersistentAngerSaveData(this.level, compoundTag);
    }

    boolean isLookingAtMe(Player player) {
        ItemStack itemStack = (ItemStack)player.getInventory().armor.get(3);
        if (itemStack.is(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        } else {
            Vec3 vec3 = player.getViewVector(1.0F).normalize();
            Vec3 vec32 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d = vec32.length();
            vec32 = vec32.normalize();
            double e = vec3.dot(vec32);
            return e > 1.0 - 0.025 / d ? player.hasLineOfSight(this) : false;
        }
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 2.55F;
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            for(int i = 0; i < 2; ++i) {
                this.level.addParticle(this.type.particleEffect(), this.getRandomX(0.5), this.getRandomY() - 0.25, this.getRandomZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }

        this.jumping = false;
        if (!this.level.isClientSide) {
            this.updatePersistentAnger((ServerLevel)this.level, true);
        }

        super.aiStep();
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    protected void customServerAiStep() {
        if (this.level.isDay() && this.tickCount >= this.targetChangeTime + 600) {
            float f = this.getBrightness();
            if (f > 0.5F && this.level.canSeeSky(this.blockPosition()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setTarget((LivingEntity)null);
                this.teleport();
            }
        }

        super.customServerAiStep();
    }

    protected boolean teleport() {
        if (!this.level.isClientSide() && this.isAlive() && this.type.canTeleport()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double e = this.getY() + (double)(this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            return this.teleport(d, e, f);
        } else {
            return false;
        }
    }

    boolean teleportTowards(Entity entity) {
        if (this.type.canTeleport()) return false;
        Vec3 vec3 = new Vec3(this.getX() - entity.getX(), this.getY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3 = vec3.normalize();
        double d = 16.0;
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 16.0;
        return this.teleport(e, f, g);
    }

    private boolean teleport(double d, double e, double f) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(d, e, f);

        while(mutableBlockPos.getY() > this.level.getMinBuildHeight() && !this.level.getBlockState(mutableBlockPos).getMaterial().blocksMotion()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        BlockState blockState = this.level.getBlockState(mutableBlockPos);
        boolean bl = blockState.getMaterial().blocksMotion();
        boolean bl2 = blockState.getFluidState().is(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = this.randomTeleport(d, e, f, true);
            if (bl3 && !this.isSilent()) {
                this.level.playSound((Player)null, this.xo, this.yo, this.zo, SoundEvents.ENDERMAN_TELEPORT, this.getSoundSource(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return bl3;
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isCreepy() ? SoundEvents.ENDERMAN_SCREAM : SoundEvents.ENDERMAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENDERMAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }

    protected void dropCustomDeathLoot(DamageSource damageSource, int i, boolean bl) {
        super.dropCustomDeathLoot(damageSource, i, bl);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            this.spawnAtLocation(blockState.getBlock());
        }

    }

    public void setCarriedBlock(@Nullable BlockState blockState) {
        if (canPickupSpecificBlock(blockState)) {
            this.entityData.set(DATA_CARRY_STATE, Optional.ofNullable(blockState));
        }
    }

    private boolean canPickupSpecificBlock(BlockState blockState) {
        if (blockState == null) return true;
        boolean isValidBlock = false;
        if (!this.type.restrictedToBlocks().isEmpty()) {
            for (TagKey<?> tag : this.type.restrictedToBlocks()) {
                if (blockState.is((TagKey) tag)) {
                    isValidBlock = true;
                    break;
                }
            }
        } else {
            isValidBlock = true;
        }
        return this.type.canPickupBlocks() && isValidBlock;
    }

    @Nullable
    public BlockState getCarriedBlock() {
        return (BlockState)((Optional)this.entityData.get(DATA_CARRY_STATE)).orElse((Object)null);
    }

    public boolean hurt(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (this.type.teleportOnInjury()) {
            if (!this.level.isClientSide()) {
                double x = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
                double y = this.getY() + (double)(this.random.nextInt(64) - 32);
                double z = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
                this.teleport(x, y, z);
            }
            return true;
        }
        else if (damageSource instanceof IndirectEntityDamageSource) {
            Entity entity = damageSource.getDirectEntity();
            boolean bl;
            if (entity instanceof ThrownPotion) {
                bl = this.hurtWithCleanWater(damageSource, (ThrownPotion)entity, f);
            } else {
                bl = false;
            }

            for(int i = 0; i < 64; ++i) {
                if (this.teleport()) {
                    return true;
                }
            }

            return bl;
        } else {
            boolean bl2 = super.hurt(damageSource, f);
            if (!this.level.isClientSide() && !(damageSource.getEntity() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
                this.teleport();
            }

            return bl2;
        }
    }

    private boolean hurtWithCleanWater(DamageSource damageSource, ThrownPotion thrownPotion, float f) {
        ItemStack itemStack = thrownPotion.getItem();
        Potion potion = PotionUtils.getPotion(itemStack);
        List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
        boolean bl = potion == Potions.WATER && list.isEmpty();
        return bl ? super.hurt(damageSource, f) : false;
    }

    public boolean isCreepy() {
        return (Boolean)this.entityData.get(DATA_CREEPY);
    }

    public boolean hasBeenStaredAt() {
        return (Boolean)this.entityData.get(DATA_STARED_AT);
    }

    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, true);
    }

    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.getCarriedBlock() != null;
    }

    static {
        SPEED_MODIFIER_ATTACKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15000000596046448, Operation.ADDITION);
        DATA_CARRY_STATE = SynchedEntityData.defineId(BaseEnderman.class, EntityDataSerializers.BLOCK_STATE);
        DATA_CREEPY = SynchedEntityData.defineId(BaseEnderman.class, EntityDataSerializers.BOOLEAN);
        DATA_STARED_AT = SynchedEntityData.defineId(BaseEnderman.class, EntityDataSerializers.BOOLEAN);
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    }

    private static class EndermanFreezeWhenLookedAt extends Goal {
        private final BaseEnderman enderman;
        @Nullable
        private LivingEntity target;

        public EndermanFreezeWhenLookedAt(BaseEnderman enderMan) {
            this.enderman = enderMan;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof Player)) {
                return false;
            } else {
                double d = this.target.distanceToSqr(this.enderman);
                return d > 256.0 ? false : this.enderman.isLookingAtMe((Player)this.target);
            }
        }

        public void start() {
            this.enderman.getNavigation().stop();
        }

        public void tick() {
            this.enderman.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    private static class EndermanLeaveBlockGoal extends Goal {
        private final BaseEnderman enderman;

        public EndermanLeaveBlockGoal(BaseEnderman enderMan) {
            this.enderman = enderMan;
        }

        public boolean canUse() {
            if (this.enderman.getCarriedBlock() == null) {
                return false;
            } else if (!this.enderman.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(reducedTickDelay(2000)) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            Level level = this.enderman.level;
            int i = Mth.floor(this.enderman.getX() - 1.0 + random.nextDouble() * 2.0);
            int j = Mth.floor(this.enderman.getY() + random.nextDouble() * 2.0);
            int k = Mth.floor(this.enderman.getZ() - 1.0 + random.nextDouble() * 2.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.below();
            BlockState blockState2 = level.getBlockState(blockPos2);
            BlockState blockState3 = this.enderman.getCarriedBlock();
            if (blockState3 != null) {
                blockState3 = Block.updateFromNeighbourShapes(blockState3, this.enderman.level, blockPos);
                if (this.canPlaceBlock(level, blockPos, blockState3, blockState, blockState2, blockPos2)) {
                    level.setBlock(blockPos, blockState3, 3);
                    level.gameEvent(this.enderman, GameEvent.BLOCK_PLACE, blockPos);
                    this.enderman.setCarriedBlock((BlockState)null);
                }

            }
        }

        private boolean canPlaceBlock(Level level, BlockPos blockPos, BlockState blockState, BlockState blockState2, BlockState blockState3, BlockPos blockPos2) {
            return blockState2.isAir() && !blockState3.isAir() && !blockState3.is(Blocks.BEDROCK) && blockState3.isCollisionShapeFullBlock(level, blockPos2) && blockState.canSurvive(level, blockPos) && level.getEntities(this.enderman, AABB.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(blockPos))).isEmpty();
        }
    }

    private static class EndermanTakeBlockGoal extends Goal {
        private final BaseEnderman enderman;

        public EndermanTakeBlockGoal(BaseEnderman enderMan) {
            this.enderman = enderMan;
        }

        public boolean canUse() {
            if (this.enderman.getCarriedBlock() != null) {
                return false;
            } else if (!this.enderman.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(reducedTickDelay(20)) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            Level level = this.enderman.level;
            int i = Mth.floor(this.enderman.getX() - 2.0 + random.nextDouble() * 4.0);
            int j = Mth.floor(this.enderman.getY() + random.nextDouble() * 3.0);
            int k = Mth.floor(this.enderman.getZ() - 2.0 + random.nextDouble() * 4.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            Vec3 vec3 = new Vec3((double)this.enderman.getBlockX() + 0.5, (double)j + 0.5, (double)this.enderman.getBlockZ() + 0.5);
            Vec3 vec32 = new Vec3((double)i + 0.5, (double)j + 0.5, (double)k + 0.5);
            BlockHitResult blockHitResult = level.clip(new ClipContext(vec3, vec32, net.minecraft.world.level.ClipContext.Block.OUTLINE, Fluid.NONE, this.enderman));
            boolean bl = blockHitResult.getBlockPos().equals(blockPos);
            if (blockState.is(BlockTags.ENDERMAN_HOLDABLE) && bl) {
                level.removeBlock(blockPos, false);
                level.gameEvent(this.enderman, GameEvent.BLOCK_DESTROY, blockPos);
                this.enderman.setCarriedBlock(blockState.getBlock().defaultBlockState());
            }

        }
    }

    static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final BaseEnderman enderman;
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();

        public EndermanLookForPlayerGoal(BaseEnderman enderMan, @Nullable Predicate<LivingEntity> predicate) {
            super(enderMan, Player.class, 10, false, false, predicate);
            this.enderman = enderMan;
            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector((livingEntity) -> {
                return enderMan.isLookingAtMe((Player)livingEntity) && enderMan.getEndermanType().provokedWithEyeContact();
            });
        }

        public boolean canUse() {
            this.pendingTarget = this.enderman.level.getNearestPlayer(this.startAggroTargetConditions, this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.adjustedTickDelay(5);
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
                    return false;
                } else {
                    this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.target != null && this.continueAggroTargetConditions.test(this.enderman, this.target) ? true : super.canContinueToUse();
            }
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTarget((LivingEntity)null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !this.enderman.isPassenger()) {
                    if (this.enderman.isLookingAtMe((Player)this.target)) {
                        if (this.target.distanceToSqr(this.enderman) < 16.0) {
                            this.enderman.teleport();
                        }

                        this.teleportTime = 0;
                    } else if (this.target.distanceToSqr(this.enderman) > 256.0 && this.teleportTime++ >= this.adjustedTickDelay(30) && this.enderman.teleportTowards(this.target)) {
                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }

        }
    }
}