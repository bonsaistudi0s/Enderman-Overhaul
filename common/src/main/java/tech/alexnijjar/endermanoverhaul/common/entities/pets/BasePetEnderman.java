package tech.alexnijjar.endermanoverhaul.common.entities.pets;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

import java.util.*;

public abstract class BasePetEnderman extends BaseEnderman implements IAnimatable, OwnableEntity {
    protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNERUUID_ID = SynchedEntityData.defineId(BasePetEnderman.class, EntityDataSerializers.OPTIONAL_UUID);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private EndermanHurtByTargetGoal hurtByTargetGoal;

    public BasePetEnderman(EntityType<? extends BaseEnderman> entityType, Level level) {
        super(entityType, level);
    }

    public BasePetEnderman(EntityType<? extends BaseEnderman> entityType, Level level, Player owner) {
        super(entityType, level);
        this.setOwnerUUID(owner.getUUID());
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OWNERUUID_ID, Optional.empty());
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, state -> {
            if (state.isMoving()) {
                state.getController().setAnimation(isCreepy() ?
                    ConstantAnimations.RUN :
                    ConstantAnimations.WALK);
            } else {
                state.getController().setAnimation(ConstantAnimations.IDLE);
            }
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "attack_controller", 5, state -> {
            if (!playArmSwingAnimWhenAttacking()) return PlayState.STOP;
            if (getAttackAnim(state.getPartialTick()) == 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ATTACK);
            return PlayState.CONTINUE;
        }));
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new EndermanFollowOwnerGoal(1.0, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new EndermanOwnerHurtByTargetGoal());
        this.targetSelector.addGoal(2, new EndermanOwnerHurtTargetGoal());
        this.targetSelector.addGoal(3, hurtByTargetGoal = new EndermanHurtByTargetGoal());
    }

    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getOwnerUUID() != null) {
            compound.putUUID("Owner", this.getOwnerUUID());
        }
    }

    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        UUID uuid;
        if (compound.hasUUID("Owner")) {
            uuid = compound.getUUID("Owner");
        } else {
            String string = compound.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(Objects.requireNonNull(this.getServer()), string);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        try {
            UUID uUID = this.getOwnerUUID();
            return uUID == null ? null : this.level.getPlayerByUUID(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_OWNERUUID_ID).orElse(null);
    }

    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(uuid));
    }

    public boolean canBeLeashed(@NotNull Player player) {
        return !this.isLeashed();
    }

    @Override
    public void tick() {
        super.tick();
        if (!level.isClientSide() && getTarget() instanceof BasePetEnderman e && Objects.equals(e.getOwnerUUID(), getOwnerUUID())) {
            setTarget(null);
            setLastHurtByMob(null);
        }

        if (!level.isClientSide() && getTarget() instanceof Player p && p.getUUID().equals(getOwnerUUID())) {
            setTarget(null);
            setLastHurtByMob(null);
        }
    }

    @Override
    public @Nullable ParticleOptions getCustomParticles() {
        return ModParticleTypes.FRIENDERMAN.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide() && this.isAlive() && this.tickCount % 2000 == 0) {
            this.heal(1.0f);
        }
    }

    public boolean wantsToAttack(LivingEntity target) {
        if (target instanceof Creeper) return false;
        if (target instanceof Ghast) return false;
        if (target instanceof BasePetEnderman otherPet) {
            return !Objects.equals(otherPet.getOwnerUUID(), getOwnerUUID());
        }
        if (target instanceof Player player) {
            return !player.getUUID().equals(getOwnerUUID());
        }
        return true;
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        if (!this.level.isClientSide && this.level.getGameRules().getBoolean(GameRules.RULE_SHOWDEATHMESSAGES) && this.getOwner() instanceof ServerPlayer) {
            this.getOwner().sendSystemMessage(this.getCombatTracker().getDeathMessage());
        }

        super.die(damageSource);
    }

    @Override
    public boolean isProvokedByEyeContact() {
        return false;
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    public class EndermanOwnerHurtByTargetGoal extends TargetGoal {
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public EndermanOwnerHurtByTargetGoal() {
            super(BasePetEnderman.this, false);
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            if (!(getOwner() instanceof LivingEntity livingEntity)) return false;
            this.ownerLastHurtBy = livingEntity.getLastHurtByMob();
            int i = livingEntity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && wantsToAttack(this.ownerLastHurtBy);
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            if (!(getOwner() instanceof LivingEntity livingEntity)) return;
            this.timestamp = livingEntity.getLastHurtByMobTimestamp();

            super.start();
        }
    }

    public class EndermanOwnerHurtTargetGoal extends TargetGoal {
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public EndermanOwnerHurtTargetGoal() {
            super(BasePetEnderman.this, false);
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            if (!(getOwner() instanceof LivingEntity livingEntity)) return false;
            this.ownerLastHurt = livingEntity.getLastHurtMob();
            int i = livingEntity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) && wantsToAttack(this.ownerLastHurt);
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurt);
            if (!(getOwner() instanceof LivingEntity livingEntity)) return;
            this.timestamp = livingEntity.getLastHurtMobTimestamp();

            super.start();
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (!player.getUUID().equals(getOwnerUUID())) return InteractionResult.PASS;
        if (!level.isClientSide()) {
            CompoundTag entityTag = new CompoundTag();
            CompoundTag petTag = new CompoundTag();
            this.saveWithoutId(petTag);
            ItemStack pearl = ModItems.ANCIENT_PEARL.get().getDefaultInstance();
            entityTag.put("PetEntity", petTag);
            entityTag.putString("PetType", Registry.ENTITY_TYPE.getKey(getType()).getPath());
            pearl.setTag(entityTag);
            BehaviorUtils.throwItem(this, pearl, position());
            playSound(SoundEvents.ENDERMAN_TELEPORT);
            this.discard();
            return InteractionResult.SUCCESS;
        }

        for (int i = 0; i < getParticleCount(); i++) {
            this.level.addParticle(ParticleTypes.PORTAL,
                this.getRandomX(0.5),
                this.getRandomY() - 0.25,
                this.getRandomZ(0.5),
                (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(),
                (this.random.nextDouble() - 0.5) * 2.0);
        }
        return InteractionResult.SUCCESS;
    }

    public class EndermanFollowOwnerGoal extends Goal {
        private LivingEntity owner;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;
        private final boolean canFly;

        public EndermanFollowOwnerGoal(double d, float f, float g, boolean bl) {
            this.speedModifier = d;
            this.navigation = getNavigation();
            this.startDistance = f;
            this.stopDistance = g;
            this.canFly = bl;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(getNavigation() instanceof GroundPathNavigation) && !(getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canUse() {
            if (!(getOwner() instanceof LivingEntity livingEntity)) return false;
            if (livingEntity.isSpectator()) {
                return false;
            } else if (this.unableToMove()) {
                return false;
            } else if (distanceToSqr(livingEntity) < (double) (this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = livingEntity;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else if (this.unableToMove()) {
                return false;
            } else {
                return !(distanceToSqr(this.owner) <= (double) (this.stopDistance * this.stopDistance));
            }
        }

        private boolean unableToMove() {
            return isPassenger() || isLeashed();
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = getPathfindingMalus(BlockPathTypes.WATER);
            setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        public void tick() {
            getLookControl().setLookAt(this.owner, 10.0F, (float) getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (distanceToSqr(this.owner) >= Math.pow(20, 2)) {
                    this.teleportToOwner();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }
            }
        }

        private void teleportToOwner() {
            BlockPos blockPos = this.owner.blockPosition();

            for (int i = 0; i < 10; i++) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean teleported = this.maybeTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
                if (teleported) return;
            }

        }

        private boolean maybeTeleportTo(int x, int y, int z) {
            if (Math.abs((double) x - this.owner.getX()) < 2.0 && Math.abs((double) z - this.owner.getZ()) < 2.0) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
                return false;
            } else {
                moveTo((double) x + 0.5, y, (double) z + 0.5, getYRot(), getXRot());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pos) {
            BlockPathTypes blockPathTypes = WalkNodeEvaluator.getBlockPathTypeStatic(level, pos.mutable());
            if (blockPathTypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockState blockState = level.getBlockState(pos.below());
                if (!this.canFly && blockState.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockPos = pos.subtract(blockPosition());
                    return level.noCollision(BasePetEnderman.this, getBoundingBox().move(blockPos));
                }
            }
        }

        private int randomIntInclusive(int min, int max) {
            return getRandom().nextInt(max - min + 1) + min;
        }
    }

    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    public class EndermanHurtByTargetGoal extends TargetGoal {
        private static final TargetingConditions HURT_BY_TARGETING = TargetingConditions.forCombat().ignoreLineOfSight().ignoreInvisibilityTesting();
        private int timestamp;
        private final Class<?>[] toIgnoreDamage;

        public EndermanHurtByTargetGoal(Class<?>... classes) {
            super(BasePetEnderman.this, true);
            this.toIgnoreDamage = classes;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            int i = getLastHurtByMobTimestamp();
            LivingEntity livingEntity = getLastHurtByMob();
            if (i != this.timestamp && livingEntity != null) {
                if (livingEntity.getType() == EntityType.PLAYER && level.getGameRules().getBoolean(GameRules.RULE_UNIVERSAL_ANGER)) {
                    return false;
                } else {
                    if (!wantsToAttack(livingEntity)) return false;

                    for (Class<?> clazz : this.toIgnoreDamage) {
                        if (clazz.isAssignableFrom(livingEntity.getClass())) {
                            return false;
                        }
                    }

                    return this.canAttack(livingEntity, HURT_BY_TARGETING);
                }
            } else {
                return false;
            }
        }

        public void start() {
            setTarget(getLastHurtByMob());
            this.targetMob = getTarget();
            this.timestamp = getLastHurtByMobTimestamp();
            this.unseenMemoryTicks = 300;
            alertOthers();
            super.start();
        }

        public void notifyStart() {
            setTarget(getLastHurtByMob());
            this.targetMob = getTarget();
            this.timestamp = getLastHurtByMobTimestamp();
            this.unseenMemoryTicks = 300;
            super.start();
        }

        public void alertOthers() {
            UUID owner = getOwnerUUID();
            if (owner == null) return;
            LivingEntity targetMob = getTarget();
            if (targetMob instanceof Player p && p.getUUID().equals(owner)) return;
            if (targetMob == null) return;

            AABB aabb = AABB.unitCubeFromLowerCorner(position()).inflate(30f);
            List<BasePetEnderman> pets = level.getEntitiesOfClass(BasePetEnderman.class, aabb, EntitySelector.NO_SPECTATORS);
            pets.remove(BasePetEnderman.this);

            List<BasePetEnderman> ownedPets = pets.stream().filter(pet -> owner.equals(pet.getOwnerUUID())).toList();
            if (ownedPets.size() <= 1) return;

            List<LivingEntity> targets = new ArrayList<>();
            if (targetMob instanceof BasePetEnderman target) {
                UUID targetOwner = target.getOwnerUUID();
                if (targetOwner == null) return;
                targets.addAll(pets.stream().filter(pet -> targetOwner.equals(pet.getOwnerUUID())).toList());
                Player player = level.getPlayerByUUID(targetOwner);
                if (player != null && player.distanceTo(BasePetEnderman.this) < 30.0f) {
                    targets.add(player);
                }
                if (targets.isEmpty()) return;
            } else if (targetMob instanceof Player player) {
                targets.add(player);
                targets.addAll(pets.stream().filter(pet -> player.getUUID().equals(pet.getOwnerUUID())).toList());
            } else {
                AABB aabb2 = AABB.unitCubeFromLowerCorner(position()).inflate(20f);
                targets.addAll(level.getEntitiesOfClass(targetMob.getClass(), aabb2, EntitySelector.NO_SPECTATORS));
            }

            for (var pet : ownedPets) {
                LivingEntity closestTarget = targets.stream().min(Comparator.comparingDouble(pet::distanceToSqr)).orElse(null);
                pet.setLastHurtByMob(closestTarget);
                pet.hurtByTargetGoal.notifyStart();
            }
        }
    }
}
