package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

public class ThrownWarpedPearl extends ThrowableItemProjectile {
    public ThrownWarpedPearl(EntityType<? extends ThrownWarpedPearl> type, Level level) {
        super(type, level);
    }

    public ThrownWarpedPearl(Level level, LivingEntity shooter) {
        super(ModEntityTypes.WARPED_PEARL.get(), shooter, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.WARPED_PEARL.get();
    }

    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0f);
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        for (int i = 0; i < 32; ++i) {
            this.level().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), this.random.nextGaussian(), 0.0, this.random.nextGaussian());
        }

        if (this.level().isClientSide() || this.isRemoved()) return;
        if (!(getOwner() instanceof LivingEntity entity)) return;
        if (entity instanceof ServerPlayer serverPlayer) {
            if (this.random.nextFloat() < 0.05f && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                Endermite endermite = EntityType.ENDERMITE.create(this.level());
                if (endermite != null) {
                    endermite.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                    this.level().addFreshEntity(endermite);
                }
            }

            if (serverPlayer.connection.isAcceptingMessages() && serverPlayer.level() == this.level() && !serverPlayer.isSleeping()) {
                if (entity.isPassenger()) {
                    serverPlayer.dismountTo(this.getX(), this.getY(), this.getZ());
                } else {
                    entity.teleportTo(this.getX(), this.getY(), this.getZ());
                }

                entity.hurt(this.damageSources().fall(), 5.0f);
                entity.resetFallDistance();
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 2));
            }
        } else {
            entity.teleportTo(this.getX(), this.getY(), this.getZ());
            entity.resetFallDistance();
        }

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
