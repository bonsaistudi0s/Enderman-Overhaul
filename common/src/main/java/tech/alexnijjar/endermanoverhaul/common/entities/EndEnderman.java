package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.networking.NetworkHandler;
import tech.alexnijjar.endermanoverhaul.networking.messages.ClientboundFlashScreenPacket;

public class EndEnderman extends BaseEnderman {
    private static final EntityDataAccessor<Integer> DATA_BITING_TICKS = SynchedEntityData.defineId(EndEnderman.class, EntityDataSerializers.INT);

    public EndEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 8;
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.38)
            .add(Attributes.ATTACK_DAMAGE, 10.0)
            .add(Attributes.FOLLOW_RANGE, 24)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    public static boolean checkMonsterSpawnRules(@NotNull EntityType<? extends Monster> type, ServerLevelAccessor level, @NotNull MobSpawnType spawnType, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!EndermanOverhaulConfig.spawnEndEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        return BaseEnderman.checkMonsterSpawnRules(type, level, spawnType, pos, random);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        super.registerControllers(controllerRegistrar);
        controllerRegistrar.add(new AnimationController<>(this, "bite_controller", state -> {
            if (entityData.get(DATA_BITING_TICKS) <= 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.BITE);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public void tick() {
        if (entityData.get(DATA_BITING_TICKS) > 0) {
            entityData.set(DATA_BITING_TICKS, entityData.get(DATA_BITING_TICKS) - 1);
        }
        super.tick();
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_BITING_TICKS, 0);
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    @Override
    public boolean canRunWhenAngry() {
        return false;
    }

    @Override
    public boolean isProvokedByEyeContact() {
        return false;
    }

    @Override
    public boolean isAlwaysHostile() {
        return true;
    }

    @Override
    public boolean canTeleport() {
        return false;
    }

    @Override
    public double getVisionRange() {
        return 24.0;
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        if (super.doHurtTarget(target)) {
            this.playSound(SoundEvents.PHANTOM_BITE, 10.0f, 0.95f + this.random.nextFloat() * 0.1f);
            entityData.set(DATA_BITING_TICKS, 7);
            if (target instanceof LivingEntity entity && random.nextBoolean()) {
                teleportPlayer(entity);
            }
            return true;
        } else {
            return false;
        }
    }

    public void teleportPlayer(LivingEntity target) {
        if (level().isClientSide) return;

        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();

        for (int i = 0; i < 16; ++i) {
            double g = target.getX() + (random.nextDouble() - 0.5) * 16.0;
            double h = Mth.clamp(target.getY() + (double) (random.nextInt(16) - 8), level().getMinBuildHeight(), level().getMinBuildHeight() + ((ServerLevel) level()).getLogicalHeight() - 1);
            double j = target.getZ() + (random.nextDouble() - 0.5) * 16.0;
            if (target.isPassenger()) {
                target.stopRiding();
            }

            Vec3 position = target.position();
            if (target.randomTeleport(g, h, j, true)) {
                level().gameEvent(GameEvent.TELEPORT, position, GameEvent.Context.of(target));
                SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                level().playSound(null, x, y, z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (target instanceof Player player) {
                    NetworkHandler.CHANNEL.sendToPlayer(new ClientboundFlashScreenPacket(), player);
                }
                target.playSound(soundEvent, 1.0F, 1.0F);
                break;
            }
        }
    }
}
