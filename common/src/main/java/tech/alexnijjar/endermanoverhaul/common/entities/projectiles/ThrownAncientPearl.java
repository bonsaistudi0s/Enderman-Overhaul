package tech.alexnijjar.endermanoverhaul.common.entities.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.AxolotlPetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.BasePetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.HammerheadPetEnderman;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.PetEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;

public class ThrownAncientPearl extends ThrowableItemProjectile {
    @Nullable
    private Entity existingPet;

    public ThrownAncientPearl(EntityType<? extends ThrownAncientPearl> type, Level level) {
        super(type, level);
    }

    public ThrownAncientPearl(Level level, LivingEntity shooter) {
        this(level, shooter, null);
    }

    public ThrownAncientPearl(Level level, LivingEntity shooter, @Nullable Entity existingPet) {
        super(ModEntityTypes.ANCIENT_PEARL.get(), shooter, level);
        this.existingPet = existingPet;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModItems.ANCIENT_PEARL.get();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        result.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0f);
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level.isClientSide() || this.isRemoved()) return;
        for (int i = 0; i < 32; ++i) {
            ModUtils.sendParticles((ServerLevel) level, ParticleTypes.PORTAL, this.getX(), (this.getY() - 1) + this.random.nextDouble() * 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, -1.3);
        }

        if (!(getOwner() instanceof LivingEntity entity)) return;
        if (entity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.connection.getConnection().isConnected()) {
                Entity pet = existingPet != null ? existingPet : createPet(serverPlayer);
                if (result instanceof EntityHitResult entityHitResult) {
                    if (pet instanceof Mob mob && entityHitResult.getEntity() instanceof Mob hit && !(hit instanceof TamableAnimal)) {
                        mob.setTarget(hit);
                    }
                }
                if (pet == null) return;
                pet.setPos(this.getX(), this.getY(), this.getZ());
                level.addFreshEntity(pet);
            }
        }

        level.playSound(null, getX(), getY(), getZ(), ModSoundEvents.ANCIENT_PEARL_HIT.get(), getSoundSource(), 1.0f, random.nextFloat() * 0.4f + 0.8f);
        this.discard();
    }

    private Entity createPet(ServerPlayer player) {
        return createPet(level, player, this.random.nextInt(3));
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
    public Entity changeDimension(@NotNull ServerLevel destination) {
        Entity entity = this.getOwner();
        if (entity != null && entity.level.dimension() != destination.dimension()) {
            this.setOwner(null);
        }

        return super.changeDimension(destination);
    }

    public static BasePetEnderman createPet(Level level, Player player, int id) {
        return switch (id) {
            case 1 -> new HammerheadPetEnderman(level, player);
            case 2 -> new AxolotlPetEnderman(level, player);
            default -> new PetEnderman(level, player);
        };
    }
}
