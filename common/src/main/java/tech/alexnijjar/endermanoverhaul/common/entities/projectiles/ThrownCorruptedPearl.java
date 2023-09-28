package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
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
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

public class ThrownCorruptedPearl extends ThrowableItemProjectile {
    public ThrownCorruptedPearl(EntityType<? extends ThrownCorruptedPearl> type, Level level) {
        super(type, level);
    }

    public ThrownCorruptedPearl(Level level, LivingEntity shooter) {
        super(ModEntityTypes.CORRUPTED_PEARL.get(), shooter, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.CORRUPTED_PEARL.get();
    }

    @Override
    public void tick() {
        Entity entity = this.getOwner();
        if (entity instanceof Player && !entity.isAlive()) {
            this.discard();
        } else {
            super.tick();
        }
    }

    @Nullable
    @Override
    public Entity changeDimension(@NotNull ServerLevel destination) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level().dimension() != destination.dimension()) {
            this.setOwner(null);
        }

        return super.changeDimension(destination);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);

        if (this.level().isClientSide() || this.isRemoved()) return;
        for (int i = 0; i < 32; ++i) {
            ModUtils.sendParticles((ServerLevel) level(), ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
        }

        if (!(result.getEntity() instanceof LivingEntity target)) {
            this.discard();
            return;
        }

        if (this.random.nextFloat() < 0.05f && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
            Endermite endermite = EntityType.ENDERMITE.create(this.level());
            if (endermite != null) {
                endermite.moveTo(target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
                this.level().addFreshEntity(endermite);
            }
        }

        ModUtils.teleportTarget(this.level(), target, 80);
        target.resetFallDistance();
        target.hurt(this.damageSources().fall(), 5.0f);
        this.discard();
    }

    protected void onHit(@NotNull HitResult result) {
        if (result.getType() == HitResult.Type.BLOCK) {
            this.discard();
        } else {
            super.onHit(result);
        }
    }
}
