package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModBlockTags;

public class CaveEnderman extends BaseEnderman {

    public CaveEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 24.0);
    }

    @SuppressWarnings("deprecation")
    public static boolean checkSpawnRules(EntityType<CaveEnderman> enderman, ServerLevelAccessor serverLevel, MobSpawnType mobSpawnType, BlockPos pos, RandomSource random) {
        if (!EndermanOverhaulConfig.spawnCaveEnderman || !EndermanOverhaulConfig.allowSpawning) return false;
        return pos.getY() < serverLevel.getSeaLevel() &&
            !serverLevel.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) &&
            Monster.checkMonsterSpawnRules(enderman, serverLevel, mobSpawnType, pos, random);
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
        if (level().getGameTime() % 20 == 0) {
            if (this.isAlive() && level().canSeeSky(this.blockPosition()) && level().isDay()) {
                this.hurt(damageSources().onFire(), 1.0f);
            }
        }
    }
}
