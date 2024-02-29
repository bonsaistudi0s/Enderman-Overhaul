package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModSoundEvents;
import tech.alexnijjar.endermanoverhaul.common.tags.ModBlockTags;

public class CaveEnderman extends BaseEnderman {

    public CaveEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.2187)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @SuppressWarnings("deprecation")
    public static boolean checkSpawnRules(EntityType<CaveEnderman> enderman, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        if (!EndermanOverhaulConfig.spawnCaveEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        if (serverLevel.getBiome(pos).is(Biomes.DEEP_DARK)) return false;
        return pos.getY() < serverLevel.getSeaLevel() &&
            !serverLevel.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) &&
            Monster.checkMonsterSpawnRules(enderman, serverLevel, mobSpawnType, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RestrictSunGoal(this));
        this.goalSelector.addGoal(1, new FleeSunGoal(this, 1.0));
        this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(3, new EndermanFreezeWhenLookedAt());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new EndermanLeaveBlockGoal());
        this.goalSelector.addGoal(11, new EndermanTakeBlockGoal());
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Endermite.class, true, false));
        this.targetSelector.addGoal(4, new ResetUniversalAngerTargetGoal<>(this, false));
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    @Override
    public boolean isProvokedByEyeContact() {
        return false;
    }

    @Override
    public ParticleOptions getCustomParticles() {
        return ModParticleTypes.DUST.get();
    }

    @Override
    public boolean isAlwaysHostile() {
        return true;
    }

    @Override
    public double getVisionRange() {
        return 24.0;
    }

    @Override
    public TagKey<Block> getCarriableBlockTag() {
        return ModBlockTags.CAVE_ENDERMAN_HOLDEABLE;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) return;
        if (getTickCount() % 20 == 0 && isAlive()) {
            if (level().canSeeSky(this.blockPosition()) && level().isDay()) {
                this.hurt(damageSources().onFire(), 1.0f);
                teleportUnderBlock();
            } else if (level().getBrightness(LightLayer.BLOCK, blockPosition()) > 6) {
                for (int i = 0; i < 64; i++) {
                    if (teleport()) {
                        setTarget(null);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.CAVE_ENDERMAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
        return ModSoundEvents.CAVE_ENDERMAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.CAVE_ENDERMAN_HURT.get();
    }

    private void teleportUnderBlock() {
        int range = 5;
        BlockPos.betweenClosedStream(
                Mth.floor(getX() - range),
                Mth.floor(getY() - 5),
                Mth.floor(getZ() - range),
                Mth.floor(getX() + range),
                Mth.floor(getY()),
                Mth.floor(getZ() + range))
            .filter(pos -> level().getBlockState(pos).isAir() && level().getBlockState(pos.above()).isAir() && !level().canSeeSky(pos))
            .findAny()
            .ifPresent(pos -> {
                level().gameEvent(GameEvent.TELEPORT, position(), GameEvent.Context.of(this));
                teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 1.0f);
            });
        navigation.stop();
    }
}
