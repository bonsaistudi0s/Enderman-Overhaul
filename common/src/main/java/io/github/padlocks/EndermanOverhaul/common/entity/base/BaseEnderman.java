package io.github.padlocks.EndermanOverhaul.common.entity.base;

import gg.moonflower.pollen.api.entity.PollenEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BaseEnderman extends HostileEntity implements Angerable, AnimatedEntity, PollenEntity {
    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final EntityAttributeModifier SPEED_MODIFIER_ATTACKING;
    private static final int DELAY_BETWEEN_CREEPY_STARE_SOUND = 400;
    private static final int MIN_DEAGGRESSION_TIME = 600;
    private static final TrackedData<Optional<BlockState>> DATA_CARRY_STATE;
    private static final TrackedData<Boolean> DATA_CREEPY;
    private static final TrackedData<Boolean> DATA_STARED_AT;
    private final EndermanType type;
    private int lastStareSound = Integer.MIN_VALUE;
    private int targetChangeTime;
    private static final UniformIntProvider PERSISTENT_ANGER_TIME;
    private int remainingPersistentAngerTime;
    @Nullable
    private UUID persistentAngerTarget;
    public static final AnimationState WALK_ANIMATION = new AnimationState(20);
    public static final AnimationState RUN_ANIMATION = new AnimationState(20);
    public static final AnimationState IDLE_ANIMATION = new AnimationState(20);
    public static final AnimationState ANGRY_ANIMATION = new AnimationState(60);
    private AnimationState animationState;
    private int animationTick;

    public BaseEnderman(EntityType<? extends BaseEnderman> entityType, World level, EndermanType type) {
        super(entityType, level);
        this.type = type;
        this.stepHeight = 1.0F;
        this.animationState = AnimationState.EMPTY;
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);

        if (this.type.chanceToSpawnWithRiches() > 0) {
            if (random.nextInt(1, 101) <= this.type.chanceToSpawnWithRiches()) {
                ArrayList<BlockState> blocks = new ArrayList<>();
                blocks.add(Blocks.COAL_BLOCK.getDefaultState());
                blocks.add(Blocks.IRON_BLOCK.getDefaultState());
                blocks.add(Blocks.GOLD_BLOCK.getDefaultState());
                blocks.add(Blocks.EMERALD_BLOCK.getDefaultState());
                this.setCarriedBlock(blocks.get(random.nextInt(0, blocks.size())));
            }
        }
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EndermanFreezeWhenLookedAt(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.goalSelector.add(10, new EndermanLeaveBlockGoal(this));
        this.goalSelector.add(11, new EndermanTakeBlockGoal(this));
        this.targetSelector.add(1, new EndermanLookForPlayerGoal(this, this::shouldAngerAt));
        this.targetSelector.add(2, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(3, new ActiveTargetGoal(this, EndermiteEntity.class, true, false));
        this.targetSelector.add(4, new UniversalAngerGoal(this, false));
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

    public static Supplier<DefaultAttributeContainer.Builder> createAttributes() {
        return () -> HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30000001192092896)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }

    @Override
    public double getAttributeValue(@NotNull EntityAttribute attribute) {
        return this.getAttributeInstance(attribute).getValue();
    }

    public static EntityType.EntityFactory<BaseEnderman> of(EndermanType type) {
        return (entityType, level) -> new BaseEnderman(entityType, level, type);
    }

    public void setTarget(@Nullable LivingEntity livingEntity) {
        super.setTarget(livingEntity);
        EntityAttributeInstance attributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (livingEntity == null) {
            this.targetChangeTime = 0;
            this.dataTracker.set(DATA_CREEPY, false);
            this.dataTracker.set(DATA_STARED_AT, false);
            attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING);
        } else {
            this.targetChangeTime = this.age;
            this.dataTracker.set(DATA_CREEPY, true);
            if (!attributeInstance.hasModifier(SPEED_MODIFIER_ATTACKING)) {
                attributeInstance.addTemporaryModifier(SPEED_MODIFIER_ATTACKING);
            }
        }

    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_CARRY_STATE, Optional.empty());
        this.dataTracker.startTracking(DATA_CREEPY, false);
        this.dataTracker.startTracking(DATA_STARED_AT, false);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(PERSISTENT_ANGER_TIME.get(this.random));
    }

    public void setAngerTime(int i) {
        this.remainingPersistentAngerTime = i;
    }

    public int getAngerTime() {
        return this.remainingPersistentAngerTime;
    }

    public void setAngryAt(@Nullable UUID uUID) {
        this.persistentAngerTarget = uUID;
    }

    @Nullable
    public UUID getAngryAt() {
        return this.persistentAngerTarget;
    }

    public void playStareSound() {
        if (this.age >= this.lastStareSound + 400) {
            this.lastStareSound = this.age;
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), 2.5F, 1.0F, false);
            }
        }

    }

    public void onTrackedDataSet(TrackedData<?> entityDataAccessor) {
        if (DATA_CREEPY.equals(entityDataAccessor) && this.hasBeenStaredAt() && this.world.isClient) {
            this.playStareSound();
        }

        super.onTrackedDataSet(entityDataAccessor);
    }

    public void writeCustomDataToNbt(NbtCompound compoundTag) {
        super.writeCustomDataToNbt(compoundTag);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            compoundTag.put("carriedBlockState", NbtHelper.fromBlockState(blockState));
        }

        this.writeAngerToNbt(compoundTag);
    }

    public void readCustomDataFromNbt(NbtCompound compoundTag) {
        super.readCustomDataFromNbt(compoundTag);
        BlockState blockState = null;
        if (compoundTag.contains("carriedBlockState", 10)) {
            blockState = NbtHelper.toBlockState(compoundTag.getCompound("carriedBlockState"));
            if (blockState.isAir()) {
                blockState = null;
            }
        }

        this.setCarriedBlock(blockState);
        this.readAngerFromNbt(this.world, compoundTag);
    }

    boolean isLookingAtMe(PlayerEntity player) {
        ItemStack itemStack = (ItemStack)player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem())) {
            return false;
        } else {
            Vec3d vec3 = player.getRotationVec(1.0F).normalize();
            Vec3d vec32 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d = vec32.length();
            vec32 = vec32.normalize();
            double e = vec3.dotProduct(vec32);
            return e > 1.0 - 0.025 / d ? player.canSee(this) : false;
        }
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions entityDimensions) {
        return 2.55F;
    }

    public void tickMovement() {
        if (this.world.isClient) {
            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(this.type.particleEffect(), this.getParticleX(0.5), this.getRandomBodyY() - 0.25, this.getParticleZ(0.5), (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(), (this.random.nextDouble() - 0.5) * 2.0);
            }
        }

        this.jumping = false;
        if (!this.world.isClient) {
            this.tickAngerLogic((ServerWorld)this.world, true);
        }

        super.tickMovement();
    }

    public boolean hurtByWater() {
        return true;
    }

    protected void mobTick() {
        if (this.world.isDay() && this.age >= this.targetChangeTime + 600) {
            float f = this.getBrightnessAtEyes();
            if (f > 0.5F && this.world.isSkyVisible(this.getBlockPos()) && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
                this.setTarget((LivingEntity)null);
                this._teleport();
            }
        }

        super.mobTick();
    }

    protected boolean teleport() {
        if (!this.world.isClient() && this.isAlive() && this.type.canTeleport()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double e = this.getY() + (double)(this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            return this._teleport(d, e, f);
        } else {
            return false;
        }
    }

    boolean teleportTowards(Entity entity) {
        if (this.type.canTeleport()) return false;
        Vec3d vec3 = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3 = vec3.normalize();
        double d = 16.0;
        double e = this.getX() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.x * 16.0;
        double f = this.getY() + (double)(this.random.nextInt(16) - 8) - vec3.y * 16.0;
        double g = this.getZ() + (this.random.nextDouble() - 0.5) * 8.0 - vec3.z * 16.0;
        return this._teleport(e, f, g);
    }

    private boolean _teleport() {
        double x = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
        double y = this.getY() + (double)(this.random.nextInt(64) - 32);
        double z = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
        return this._teleport(x, y, z);
    }

    private boolean _teleport(double d, double e, double f) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(d, e, f);

        while(mutableBlockPos.getY() > this.world.getBottomY() && !this.world.getBlockState(mutableBlockPos).getMaterial().blocksMovement()) {
            mutableBlockPos.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutableBlockPos);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            boolean bl3 = this._teleport(d, e, f);
            if (bl3 && !this.isSilent()) {
                this.world.playSound((PlayerEntity)null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return bl3;
        } else {
            return false;
        }
    }

    protected SoundEvent getAmbientSound() {
        return this.isCreepy() ? SoundEvents.ENTITY_ENDERMAN_SCREAM : SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    protected void dropEquipment(DamageSource damageSource, int i, boolean bl) {
        super.dropEquipment(damageSource, i, bl);
        BlockState blockState = this.getCarriedBlock();
        if (blockState != null) {
            this.dropItem(blockState.getBlock());
        }

    }

    public void setCarriedBlock(@Nullable BlockState blockState) {
        if (canPickupSpecificBlock(blockState)) {
            this.dataTracker.set(DATA_CARRY_STATE, Optional.ofNullable(blockState));
        }
    }

    private boolean canPickupSpecificBlock(BlockState blockState) {
        if (blockState == null) return true;
        boolean isValidBlock = false;
        if (!this.type.restrictedToBlocks().isEmpty()) {
            for (TagKey<?> tag : this.type.restrictedToBlocks()) {
                if (blockState.isIn((TagKey) tag)) {
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
        return (BlockState)((Optional)this.dataTracker.get(DATA_CARRY_STATE)).orElse((Object)null);
    }

    public boolean damage(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (this.type.teleportOnInjury()) {
            if (!this.world.isClient()) {
                this._teleport();
            }
            return true;
        }
        else if (damageSource instanceof ProjectileDamageSource) {
            Entity entity = damageSource.getSource();
            boolean bl;
            if (entity instanceof PotionEntity) {
                bl = this.hurtWithCleanWater(damageSource, (PotionEntity)entity, f);
            } else {
                bl = false;
            }

            for(int i = 0; i < 64; ++i) {
                if (this._teleport()) {
                    return true;
                }
            }

            return bl;
        } else {
            boolean bl2 = super.damage(damageSource, f);
            if (!this.world.isClient() && !(damageSource.getAttacker() instanceof LivingEntity) && this.random.nextInt(10) != 0) {
                this._teleport();
            }

            return bl2;
        }
    }

    private boolean hurtWithCleanWater(DamageSource damageSource, PotionEntity thrownPotion, float f) {
        ItemStack itemStack = thrownPotion.getStack();
        Potion potion = PotionUtil.getPotion(itemStack);
        List<StatusEffectInstance> list = PotionUtil.getPotionEffects(itemStack);
        boolean bl = potion == Potions.WATER && list.isEmpty();
        return bl ? super.damage(damageSource, f) : false;
    }

    public boolean isCreepy() {
        return (Boolean)this.dataTracker.get(DATA_CREEPY);
    }

    public boolean hasBeenStaredAt() {
        return (Boolean)this.dataTracker.get(DATA_STARED_AT);
    }

    public void setBeingStaredAt() {
        this.dataTracker.set(DATA_STARED_AT, true);
    }

    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.getCarriedBlock() != null;
    }

    static {
        SPEED_MODIFIER_ATTACKING = new EntityAttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Attacking speed boost", 0.15000000596046448, Operation.ADDITION);
        DATA_CARRY_STATE = DataTracker.registerData(BaseEnderman.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
        DATA_CREEPY = DataTracker.registerData(BaseEnderman.class, TrackedDataHandlerRegistry.BOOLEAN);
        DATA_STARED_AT = DataTracker.registerData(BaseEnderman.class, TrackedDataHandlerRegistry.BOOLEAN);
        PERSISTENT_ANGER_TIME = TimeHelper.betweenSeconds(20, 39);
    }

    private static class EndermanFreezeWhenLookedAt extends Goal {
        private final BaseEnderman enderman;
        @Nullable
        private LivingEntity target;

        public EndermanFreezeWhenLookedAt(BaseEnderman enderMan) {
            this.enderman = enderMan;
            this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        }

        public boolean canStart() {
            this.target = this.enderman.getTarget();
            if (!(this.target instanceof PlayerEntity)) {
                return false;
            } else {
                double d = this.target.squaredDistanceTo(this.enderman);
                return d > 256.0 ? false : this.enderman.isLookingAtMe((PlayerEntity)this.target);
            }
        }

        public void start() {
            this.enderman.getNavigation().stop();
        }

        public void tick() {
            this.enderman.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        }
    }

    private static class EndermanLeaveBlockGoal extends Goal {
        private final BaseEnderman enderman;

        public EndermanLeaveBlockGoal(BaseEnderman enderMan) {
            this.enderman = enderMan;
        }

        public boolean canStart() {
            if (this.enderman.getCarriedBlock() == null) {
                return false;
            } else if (!this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(toGoalTicks(2000)) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            World level = this.enderman.world;
            int i = MathHelper.floor(this.enderman.getX() - 1.0 + random.nextDouble() * 2.0);
            int j = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 2.0);
            int k = MathHelper.floor(this.enderman.getZ() - 1.0 + random.nextDouble() * 2.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState2 = level.getBlockState(blockPos2);
            BlockState blockState3 = this.enderman.getCarriedBlock();
            if (blockState3 != null) {
                blockState3 = Block.postProcessState(blockState3, this.enderman.world, blockPos);
                if (this.canPlaceBlock(level, blockPos, blockState3, blockState, blockState2, blockPos2)) {
                    level.setBlockState(blockPos, blockState3, 3);
                    level.emitGameEvent(this.enderman, GameEvent.BLOCK_PLACE, blockPos);
                    this.enderman.setCarriedBlock((BlockState)null);
                }

            }
        }

        private boolean canPlaceBlock(World level, BlockPos blockPos, BlockState blockState, BlockState blockState2, BlockState blockState3, BlockPos blockPos2) {
            return blockState2.isAir() && !blockState3.isAir() && !blockState3.isOf(Blocks.BEDROCK) && blockState3.isFullCube(level, blockPos2) && blockState.canPlaceAt(level, blockPos) && level.getOtherEntities(this.enderman, Box.from(Vec3d.of(blockPos))).isEmpty();
        }
    }

    private static class EndermanTakeBlockGoal extends Goal {
        private final BaseEnderman enderman;

        public EndermanTakeBlockGoal(BaseEnderman enderMan) {
            this.enderman = enderMan;
        }

        public boolean canStart() {
            if (this.enderman.getCarriedBlock() != null) {
                return false;
            } else if (!this.enderman.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                return false;
            } else {
                return this.enderman.getRandom().nextInt(toGoalTicks(20)) == 0;
            }
        }

        public void tick() {
            Random random = this.enderman.getRandom();
            World level = this.enderman.world;
            int i = MathHelper.floor(this.enderman.getX() - 2.0 + random.nextDouble() * 4.0);
            int j = MathHelper.floor(this.enderman.getY() + random.nextDouble() * 3.0);
            int k = MathHelper.floor(this.enderman.getZ() - 2.0 + random.nextDouble() * 4.0);
            BlockPos blockPos = new BlockPos(i, j, k);
            BlockState blockState = level.getBlockState(blockPos);
            Vec3d vec3 = new Vec3d((double)this.enderman.getBlockX() + 0.5, (double)j + 0.5, (double)this.enderman.getBlockZ() + 0.5);
            Vec3d vec32 = new Vec3d((double)i + 0.5, (double)j + 0.5, (double)k + 0.5);
            BlockHitResult blockHitResult = level.raycast(new RaycastContext(vec3, vec32, net.minecraft.world.RaycastContext.ShapeType.OUTLINE, FluidHandling.NONE, this.enderman));
            boolean bl = blockHitResult.getBlockPos().equals(blockPos);
            if (blockState.isIn(BlockTags.ENDERMAN_HOLDABLE) && bl) {
                level.removeBlock(blockPos, false);
                level.emitGameEvent(this.enderman, GameEvent.BLOCK_DESTROY, blockPos);
                this.enderman.setCarriedBlock(blockState.getBlock().getDefaultState());
            }

        }
    }

    static class EndermanLookForPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
        private final BaseEnderman enderman;
        @Nullable
        private PlayerEntity pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetPredicate startAggroTargetConditions;
        private final TargetPredicate continueAggroTargetConditions = TargetPredicate.createAttackable().ignoreVisibility();

        public EndermanLookForPlayerGoal(BaseEnderman enderMan, @Nullable Predicate<LivingEntity> predicate) {
            super(enderMan, PlayerEntity.class, 10, false, false, predicate);
            this.enderman = enderMan;
            this.startAggroTargetConditions = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate((livingEntity) -> {
                return enderMan.isLookingAtMe((PlayerEntity)livingEntity) && enderMan.getEndermanType().provokedWithEyeContact();
            });
        }

        public boolean canStart() {
            this.pendingTarget = this.enderman.world.getClosestPlayer(this.startAggroTargetConditions, this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.getTickCount(5);
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean shouldContinue() {
            if (this.pendingTarget != null) {
                if (!this.enderman.isLookingAtMe(this.pendingTarget)) {
                    return false;
                } else {
                    this.enderman.lookAtEntity(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.targetEntity != null && this.continueAggroTargetConditions.test(this.enderman, this.targetEntity) ? true : super.shouldContinue();
            }
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTargetEntity((LivingEntity)null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.targetEntity = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.enderman.hasVehicle()) {
                    if (this.enderman.isLookingAtMe((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.enderman) < 16.0) {
                            this.enderman.teleport();
                        }

                        this.teleportTime = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.enderman) > 256.0 && this.teleportTime++ >= this.getTickCount(30) && this.enderman.teleportTowards(this.targetEntity)) {
                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }

        }
    }
}