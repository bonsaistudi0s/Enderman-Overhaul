package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.base.BaseThrownPearl;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;
import tech.alexnijjar.endermanoverhaul.common.utils.ModUtils;

public class ThrownBubblePearl extends BaseThrownPearl {
    public ThrownBubblePearl(EntityType<? extends ThrownBubblePearl> type, Level level) {
        super(type, level);
    }

    public ThrownBubblePearl(Level level, LivingEntity shooter) {
        super(ModEntityTypes.BUBBLE_PEARL.get(), shooter, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.BUBBLE_PEARL.get();
    }

    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0f);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide() || this.isRemoved()) return;
        for (int i = 0; i < 32; ++i) {
            ModUtils.sendParticles((ServerLevel) level(), ModParticleTypes.BUBBLE.get(), this.getX(), (this.getY() - 1) + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
        }

        Entity entity = this.getOwner();
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.connection.isAcceptingMessages() && serverPlayer.level() == this.level() && !serverPlayer.isSleeping()) {
                if (entity.isPassenger()) {
                    serverPlayer.dismountTo(this.getX(), this.getY(), this.getZ());
                } else {
                    entity.teleportTo(this.getX(), this.getY(), this.getZ());
                }

                entity.resetFallDistance();
                entity.setAirSupply(entity.getMaxAirSupply());
            }
        } else if (entity != null) {
            entity.teleportTo(this.getX(), this.getY(), this.getZ());
            entity.resetFallDistance();
        }

        level().playSound(null, getX(), getY(), getZ(), ModSoundEvents.BUBBLE_PEARL_HIT.get(), getSoundSource(), 1.0f, random.nextFloat() * 0.4f + 0.8f);
        this.discard();
    }

    @Override
    public void tick() {
        Vec3 velocity = getDeltaMovement();
        super.tick();
        if (isInWater()) {
            setDeltaMovement(velocity);
        }
        setDeltaMovement(getDeltaMovement().multiply(1.01, 1.01, 1.01));
        if (this.tickCount >= 420) {
            if (!level().isClientSide()) {
                for (int i = 0; i < 32; ++i) {
                    ModUtils.sendParticles((ServerLevel) level(), ModParticleTypes.BUBBLE.get(), this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
                }
            }
            level().playSound(null, getX(), getY(), getZ(), ModSoundEvents.BUBBLE_PEARL_HIT.get(), getSoundSource(), 1.0f, random.nextFloat() * 0.4f + 0.8f);
            this.discard();
        }
    }
}
