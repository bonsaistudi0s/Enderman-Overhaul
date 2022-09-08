package io.github.padlocks.EndermanOverhaul.common.entity.base;

import gg.moonflower.pollen.api.entity.PollenEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimatedEntity;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationEffectHandler;
import gg.moonflower.pollen.pinwheel.api.common.animation.AnimationState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class PassiveEnderman extends Monster implements NeutralMob, AnimatedEntity, PollenEntity {

    private static final EntityDataAccessor<Optional<BlockState>> DATA_CARRY_STATE = SynchedEntityData.defineId(EnderMan.class, EntityDataSerializers.BLOCK_STATE);
    private final EndermanType type;

    public static final AnimationState WALK_ANIMATION = new AnimationState(20);
    public static final AnimationState RUN_ANIMATION = new AnimationState(20);

    public static final AnimationState IDLE_ANIMATION = new AnimationState(20);

    private AnimationState animationState;
    private int animationTick;

    public PassiveEnderman(EntityType<? extends Monster> entityType, Level level, EndermanType type) {
        super(entityType, level);
        this.type = type;
        this.maxUpStep = 1.0F;
        this.animationState = AnimationState.EMPTY;
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uUID) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
    public static Supplier<AttributeSupplier.Builder> createAttributes() {
        return () -> Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.20000001192092896)
                .add(Attributes.ATTACK_DAMAGE, 0.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CARRY_STATE, Optional.empty());
    }

    @Override
    public double getAttributeValue(@NotNull Attribute attribute) {
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
