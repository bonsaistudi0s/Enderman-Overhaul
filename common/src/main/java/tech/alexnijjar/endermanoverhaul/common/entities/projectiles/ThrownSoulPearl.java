package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

public class ThrownSoulPearl extends ThrowableItemProjectile {
    @Nullable
    private Entity boundEntity;

    public ThrownSoulPearl(EntityType<? extends ThrownSoulPearl> type, Level level) {
        super(type, level);
    }

    public ThrownSoulPearl(Level level, LivingEntity shooter, @NotNull Entity boundEntity) {
        super(ModEntityTypes.CORRUPTED_PEARL.get(), shooter, level);
        this.boundEntity = boundEntity;
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
        result.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0f);
    }

    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level().isClientSide() || this.isRemoved()) return;
        for (int i = 0; i < 32; ++i) {
            ModUtils.sendParticles((ServerLevel) level(), ModParticleTypes.SOUL_FIRE_FLAME.get(), this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
        }

        if (boundEntity != null) {
            if (boundEntity.getType().is(ModEntityTypeTags.CANT_BE_TELEPORTED)) return;
            if (this.random.nextFloat() < 0.05f && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                Endermite endermite = EntityType.ENDERMITE.create(this.level());
                if (endermite != null) {
                    endermite.moveTo(boundEntity.getX(), boundEntity.getY(), boundEntity.getZ(), boundEntity.getYRot(), boundEntity.getXRot());
                    this.level().addFreshEntity(endermite);
                }
            }

            boundEntity.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
            boundEntity.hurt(this.damageSources().fall(), 5.0f);
            boundEntity.resetFallDistance();
            boundEntity.teleportTo(this.getX(), this.getY(), this.getZ());
        }

        this.discard();
    }
}
