package io.github.padlocks.EndermanOverhaul.common.entity.base;

import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import gg.moonflower.pollen.api.entity.PollenEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class PassiveEnderman extends HostileEntity implements Angerable, AnimatedEntity, PollenEntity {

    private static final TrackedData<Optional<BlockState>> DATA_CARRY_STATE = DataTracker.registerData(EndermanEntity.class, TrackedDataHandlerRegistry.OPTIONAL_BLOCK_STATE);
    private final EndermanType type;

    public static final AnimationState WALK_ANIMATION = new AnimationState(20);
    public static final AnimationState RUN_ANIMATION = new AnimationState(20);

    public static final AnimationState IDLE_ANIMATION = new AnimationState(20);

    private AnimationState animationState;
    private int animationTick;

    public PassiveEnderman(EntityType<? extends HostileEntity> entityType, World level, EndermanType type) {
        super(entityType, level);
        this.type = type;
        this.stepHeight = 1.0F;
        this.animationState = AnimationState.EMPTY;
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return null;
    }

    @Override
    public void setAngryAt(@Nullable UUID uUID) {

    }

    @Override
    public void chooseRandomAngerTime() {

    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
    }
    public static Supplier<DefaultAttributeContainer.Builder> createAttributes() {
        return () -> HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000001192092896)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 64.0);
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_CARRY_STATE, Optional.empty());
    }

    @Override
    public double getAttributeValue(@NotNull EntityAttribute attribute) {
        double val = super.getAttributeValue(attribute);
        //return attribute.equals(Attributes.ATTACK_DAMAGE) ? val * 10 : val;
        return val;
    }

    public static EntityType.EntityFactory<PassiveEnderman> of(EndermanType type) {
        return (entityType, level) -> new PassiveEnderman(entityType, level, type);
    }

    @Override
    public void tick() {
        super.tick();
        this.animationTick();
    }

    @Override
    public int getAnimationTick() {
        return 0;
    }

    @Override
    public int getAnimationTransitionTick() {
        return 0;
    }

    @Override
    public int getAnimationTransitionLength() {
        return 0;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = animationTick;
    }

    @Override
    public void setAnimationTransitionTick(int transitionTick) {

    }

    @Override
    public void setAnimationTransitionLength(int transitionLength) {

    }

    @Override
    public AnimationState getAnimationState() {
        return this.animationState;
    }

    @Override
    public AnimationState getTransitionAnimationState() {
        return null;
    }

    @Override
    public void setAnimationState(AnimationState state) {
        this.onAnimationStop(this.animationState);
        this.animationState = state;
        this.setAnimationTick(0);
    }

    @Override
    public void setTransitionAnimationState(AnimationState state) {

    }

    @Override
    public @Nullable AnimationEffectHandler getAnimationEffects() {
        return null;
    }

    @Override
    public AnimationState[] getAnimationStates() {
        return new AnimationState[] {
                WALK_ANIMATION,
                RUN_ANIMATION,
                IDLE_ANIMATION
        };
    }
}
