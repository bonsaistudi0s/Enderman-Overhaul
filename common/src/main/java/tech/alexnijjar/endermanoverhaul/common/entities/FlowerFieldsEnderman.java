package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.PassiveEnderman;

public class FlowerFieldsEnderman extends PassiveEnderman {

    public FlowerFieldsEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 1.0)
            .add(Attributes.FOLLOW_RANGE, 64);
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
    public boolean canPickupBlocks() {
        return false;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        boolean hurt = super.hurt(source, amount);

        if (source.getEntity() == null) return hurt;
        for (int i = 0; i < 64; ++i) {
            if (this.teleport()) return true;
        }
        return hurt;
    }
}
