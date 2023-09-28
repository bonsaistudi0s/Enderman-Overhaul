package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

import java.util.List;

public class ThrownIcyPearl extends ThrowableItemProjectile {
    public ThrownIcyPearl(EntityType<? extends ThrownIcyPearl> type, Level level) {
        super(type, level);
    }

    public ThrownIcyPearl(Level level, LivingEntity shooter) {
        super(ModEntityTypes.ICY_PEARL.get(), shooter, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.ICY_PEARL.get();
    }

    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 4.0f);
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide() || this.isRemoved()) return;
        for (int i = 0; i < 32; ++i) {
            ModUtils.sendParticles((ServerLevel) level(), ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
        }

        Entity entity = this.getOwner();
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.connection.isAcceptingMessages() && serverPlayer.level() == this.level() && !serverPlayer.isSleeping()) {
                List<Entity> nearbyEntities = level().getEntities(this, getBoundingBox().inflate(4.0), e -> e instanceof LivingEntity);
                for (var nearby : nearbyEntities) {
                    if (nearby == serverPlayer) continue;
                    if (!(nearby instanceof LivingEntity target)) continue;
                    target.setTicksFrozen(target.getTicksRequiredToFreeze() * 5);
                    target.hurt(this.damageSources().freeze(), 8.0f);
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 2));
                    target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 1));
                }

                BlockPos.betweenClosedStream(
                        Mth.floor(getX() - 1),
                        Mth.floor(getY() - 1),
                        Mth.floor(getZ() - 1),
                        Mth.floor(getX() + 1),
                        Mth.floor(getY() + 1),
                        Mth.floor(getZ() + 1))
                    .filter(pos -> level().getFluidState(pos).is(FluidTags.WATER))
                    .forEach(pos -> level().setBlockAndUpdate(pos, Blocks.FROSTED_ICE.defaultBlockState()));

                if (serverPlayer.connection.isAcceptingMessages() && serverPlayer.level() == this.level() && !serverPlayer.isSleeping()) {
                    if (entity.isPassenger()) {
                        serverPlayer.dismountTo(this.getX(), this.getY(), this.getZ());
                    } else {
                        entity.teleportTo(this.getX(), this.getY(), this.getZ());
                    }

                    entity.hurt(this.damageSources().fall(), 5.0f);
                    entity.resetFallDistance();
                }
            }
        } else if (entity != null) {
            entity.teleportTo(this.getX(), this.getY(), this.getZ());
            entity.resetFallDistance();
        }

        level().playSound(null, getX(), getY(), getZ(), SoundEvents.ENDER_EYE_DEATH, getSoundSource(), 1.0f, 1.0f);
        this.discard();
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
        }
        if (this.tickCount >= 500) this.discard();
    }

    @Nullable
    public Entity changeDimension(@NotNull ServerLevel destination) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level().dimension() != destination.dimension()) {
            this.setOwner(null);
        }

        return super.changeDimension(destination);
    }
}
