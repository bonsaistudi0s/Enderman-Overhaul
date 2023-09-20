package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

public class SavannaEnderman extends BaseEnderman {
    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 7.0)
            .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public SavannaEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canOpenMouth() {
        return false;
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return super.getDimensions(pose);
    }
}
