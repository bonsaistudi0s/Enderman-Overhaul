package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.tags.ModBlockTags;

public class CaveEnderman extends BaseEnderman {
    public CaveEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.15)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 32.0);
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
        return ParticleTypes.ASH;
    }

    @Override
    public boolean isAlwaysHostile() {
        return true;
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
            if (this.isAlive() && level().canSeeSky(this.blockPosition())) {
                this.hurt(damageSources().onFire(), 1.0f);
            }
        }
        super.tick();
    }
}
