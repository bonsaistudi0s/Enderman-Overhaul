package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.EndIslandsEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

import java.util.List;
import java.util.UUID;

public class EnderBullet extends Projectile implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private static final double SPEED = 0.25;
    @Nullable
    private Entity finalTarget;
    @Nullable
    private Direction currentMoveDirection;
    private int flightSteps;
    private double targetDeltaX;
    private double targetDeltaY;
    private double targetDeltaZ;
    @Nullable
    private UUID targetId;

    public EnderBullet(EntityType<? extends EnderBullet> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    public EnderBullet(Level level, LivingEntity livingEntity, @Nullable Entity entity, Direction.Axis axis) {
        this(ModEntityTypes.ENDER_BULLET.get(), level);
        this.setOwner(livingEntity);
        BlockPos blockPos = livingEntity.blockPosition();
        double d = blockPos.getX() + 0.5;
        double e = blockPos.getY() + 0.5;
        double f = blockPos.getZ() + 0.5;
        this.moveTo(d, e, f, this.getYRot(), this.getXRot());
        this.finalTarget = entity;
        this.currentMoveDirection = Direction.UP;
        this.selectNextMoveDirection(axis);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            state.getController().setAnimation(ConstantAnimations.SPIN);
            return PlayState.CONTINUE;
        }));
    }

    public @NotNull SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.finalTarget != null) {
            compound.putUUID("Target", this.finalTarget.getUUID());
        }

        if (this.currentMoveDirection != null) {
            compound.putInt("Dir", this.currentMoveDirection.get3DDataValue());
        }

        compound.putInt("Steps", this.flightSteps);
        compound.putDouble("TXD", this.targetDeltaX);
        compound.putDouble("TYD", this.targetDeltaY);
        compound.putDouble("TZD", this.targetDeltaZ);
    }

    protected void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.flightSteps = compound.getInt("Steps");
        this.targetDeltaX = compound.getDouble("TXD");
        this.targetDeltaY = compound.getDouble("TYD");
        this.targetDeltaZ = compound.getDouble("TZD");
        if (compound.contains("Dir", 99)) {
            this.currentMoveDirection = Direction.from3DDataValue(compound.getInt("Dir"));
        }

        if (compound.hasUUID("Target")) {
            this.targetId = compound.getUUID("Target");
        }

    }

    protected void defineSynchedData() {
    }

    private void setMoveDirection(@Nullable Direction direction) {
        this.currentMoveDirection = direction;
    }

    private void selectNextMoveDirection(@Nullable Direction.Axis axis) {
        double d = 0.5;
        BlockPos blockPos;
        if (this.finalTarget == null) {
            blockPos = this.blockPosition().below();
        } else {
            d = this.finalTarget.getBbHeight() * 0.5;
            blockPos = BlockPos.containing(this.finalTarget.getX(), this.finalTarget.getY() + d, this.finalTarget.getZ());
        }

        double e = blockPos.getX() + 0.5;
        double f = blockPos.getY() + d;
        double g = blockPos.getZ() + 0.5;
        Direction direction = null;
        if (!blockPos.closerToCenterThan(this.position(), 2.0)) {
            BlockPos blockPos2 = this.blockPosition();
            List<Direction> list = Lists.newArrayList();
            if (axis != Direction.Axis.X) {
                if (blockPos2.getX() < blockPos.getX() && this.level().isEmptyBlock(blockPos2.east())) {
                    list.add(Direction.EAST);
                } else if (blockPos2.getX() > blockPos.getX() && this.level().isEmptyBlock(blockPos2.west())) {
                    list.add(Direction.WEST);
                }
            }

            if (axis != Direction.Axis.Y) {
                if (blockPos2.getY() < blockPos.getY() && this.level().isEmptyBlock(blockPos2.above())) {
                    list.add(Direction.UP);
                } else if (blockPos2.getY() > blockPos.getY() && this.level().isEmptyBlock(blockPos2.below())) {
                    list.add(Direction.DOWN);
                }
            }

            if (axis != Direction.Axis.Z) {
                if (blockPos2.getZ() < blockPos.getZ() && this.level().isEmptyBlock(blockPos2.south())) {
                    list.add(Direction.SOUTH);
                } else if (blockPos2.getZ() > blockPos.getZ() && this.level().isEmptyBlock(blockPos2.north())) {
                    list.add(Direction.NORTH);
                }
            }

            direction = Direction.getRandom(this.random);
            if (list.isEmpty()) {
                for (int i = 5; !this.level().isEmptyBlock(blockPos2.relative(direction)) && i > 0; --i) {
                    direction = Direction.getRandom(this.random);
                }
            } else {
                direction = list.get(this.random.nextInt(list.size()));
            }

            e = this.getX() + direction.getStepX();
            f = this.getY() + direction.getStepY();
            g = this.getZ() + direction.getStepZ();
        }

        this.setMoveDirection(direction);
        double h = e - this.getX();
        double j = f - this.getY();
        double k = g - this.getZ();
        double l = Math.sqrt(h * h + j * j + k * k);
        if (l == 0.0) {
            this.targetDeltaX = 0.0;
            this.targetDeltaY = 0.0;
            this.targetDeltaZ = 0.0;
        } else {
            this.targetDeltaX = h / l * SPEED;
            this.targetDeltaY = j / l * SPEED;
            this.targetDeltaZ = k / l * SPEED;
        }

        this.hasImpulse = true;
        this.flightSteps = 10 + this.random.nextInt(5) * 10;
    }

    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL) {
            this.discard();
        }

    }

    public void tick() {
        super.tick();
        Vec3 vec3;
        if (!this.level().isClientSide) {
            if (this.finalTarget == null && this.targetId != null) {
                this.finalTarget = ((ServerLevel) this.level()).getEntity(this.targetId);
                if (this.finalTarget == null) {
                    this.targetId = null;
                }
            }

            if (this.finalTarget == null || !this.finalTarget.isAlive() || this.finalTarget instanceof Player && this.finalTarget.isSpectator()) {
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
                }
            } else {
                this.targetDeltaX = Mth.clamp(this.targetDeltaX * 1.025, -1.0, 1.0);
                this.targetDeltaY = Mth.clamp(this.targetDeltaY * 1.025, -1.0, 1.0);
                this.targetDeltaZ = Mth.clamp(this.targetDeltaZ * 1.025, -1.0, 1.0);
                vec3 = this.getDeltaMovement();
                this.setDeltaMovement(vec3.add((this.targetDeltaX - vec3.x) * 0.6, (this.targetDeltaY - vec3.y) * 0.6, (this.targetDeltaZ - vec3.z) * 0.6));
            }

            HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onHit(hitResult);
            }
        }

        this.checkInsideBlocks();
        vec3 = this.getDeltaMovement();
        this.setPos(this.getX() + vec3.x, this.getY() + vec3.y, this.getZ() + vec3.z);
        ProjectileUtil.rotateTowardsMovement(this, 10);
        if (this.level().isClientSide) {
            for (int i = 0; i < 5; i++) {
                this.level().addParticle(ParticleTypes.PORTAL,
                    this.getRandomX(0.5),
                    this.getRandomY() - 0.25,
                    this.getRandomZ(0.5),
                    (this.random.nextDouble() - 0.5) * 2.0, -this.random.nextDouble(),
                    (this.random.nextDouble() - 0.5) * 2.0);
            }
        } else if (this.finalTarget != null && !this.finalTarget.isRemoved()) {
            if (this.flightSteps > 0) {
                --this.flightSteps;
                if (this.flightSteps == 0) {
                    this.selectNextMoveDirection(this.currentMoveDirection == null ? null : this.currentMoveDirection.getAxis());
                }
            }

            if (this.currentMoveDirection != null) {
                BlockPos blockPos = this.blockPosition();
                Direction.Axis axis = this.currentMoveDirection.getAxis();
                if (this.level().loadedAndEntityCanStandOn(blockPos.relative(this.currentMoveDirection), this)) {
                    this.selectNextMoveDirection(axis);
                } else {
                    BlockPos blockPos2 = this.finalTarget.blockPosition();
                    if (axis == Direction.Axis.X && blockPos.getX() == blockPos2.getX() || axis == Direction.Axis.Z && blockPos.getZ() == blockPos2.getZ() || axis == Direction.Axis.Y && blockPos.getY() == blockPos2.getY()) {
                        this.selectNextMoveDirection(axis);
                    }
                }
            }
        }

    }

    protected boolean canHitEntity(@NotNull Entity target) {
        return super.canHitEntity(target) && !target.noPhysics;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return distance < 16384.0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (result.getEntity() instanceof EndIslandsEnderman) return;
        super.onHitEntity(result);
        if (!(result.getEntity() instanceof LivingEntity target)) return;
        LivingEntity livingEntity = getOwner() instanceof LivingEntity e ? e : null;
        if (target.hurt(this.damageSources().mobProjectile(this, livingEntity), 15.0f)) {
            if (livingEntity != null) {
                this.doEnchantDamageEffects(livingEntity, target);
            }
            ModUtils.teleportTarget(level(), target, 32);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
        this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.0F);
    }

    private void destroy() {
        this.discard();
        this.level().gameEvent(GameEvent.ENTITY_DAMAGE, this.position(), GameEvent.Context.of(this));
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);
        this.destroy();
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (!this.level().isClientSide) {
            this.playSound(SoundEvents.SHULKER_BULLET_HURT, 1.0F, 1.0F);
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 15, 0.2, 0.2, 0.2, 0.0);
            this.destroy();
        }

        return true;
    }

    @Override
    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d = packet.getXa();
        double e = packet.getYa();
        double f = packet.getZa();
        this.setDeltaMovement(d, e, f);
    }
}
