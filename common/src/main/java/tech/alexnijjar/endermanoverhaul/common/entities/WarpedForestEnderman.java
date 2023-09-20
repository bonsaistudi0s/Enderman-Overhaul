package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

public class WarpedForestEnderman extends BaseEnderman {
    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    public WarpedForestEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
        xpReward = 8;
    }

    @Override
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean hasParticles() {
        return false;
    }

    @Override
    public double getVisionRange() {
        return 32.0;
    }

    // TODO: implement dynamic hitbox when creepy
}
