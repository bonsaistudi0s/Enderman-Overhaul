package tech.alexnijjar.endermanoverhaul.common.entities.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class PassiveEnderman extends BaseEnderman {

    public PassiveEnderman(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(10, new EndermanLeaveBlockGoal());
        this.goalSelector.addGoal(11, new EndermanTakeBlockGoal());
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this::isAngryAt));
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return null;
    }

    @Override
    public boolean isAngryAt(@NotNull LivingEntity entity) {
        return false;
    }

    @Override
    public boolean isCreepy() {
        return false;
    }
}
