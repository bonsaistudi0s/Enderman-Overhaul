package tech.alexnijjar.endermanoverhaul.client.compat.mekanism;

import mekanism.additions.common.entity.baby.EntityBabyEnderman;
import mekanism.additions.common.registries.AdditionsEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;

public class ReplacedBabyEnderman implements GeoReplacedEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public EntityType<?> getReplacingEntityType() {
        return AdditionsEntityTypes.BABY_ENDERMAN.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, 0, state -> {
            EntityBabyEnderman enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;

            if (state.isMoving()) {
                state.getController().setAnimation(ConstantAnimations.RUN);
                state.setControllerSpeed(3);
            } else {
                state.getController().setAnimation(ConstantAnimations.IDLE);
                state.setControllerSpeed(1);
            }
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "creepy_controller", 5, state -> {
            EntityBabyEnderman enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (!enderman.isCreepy()) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ANGRY);
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "hold_controller", 5, state -> {
            EntityBabyEnderman enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (enderman.getCarriedBlock() == null) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.HOLDING);
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "attack_controller", 5, state -> {
            EntityBabyEnderman enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (enderman.getAttackAnim(state.getPartialTick()) == 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ATTACK);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    private EntityBabyEnderman getEndermanFromState(AnimationState<ReplacedBabyEnderman> state) {
        Entity entity = state.getData(DataTickets.ENTITY);
        if (!(entity instanceof EntityBabyEnderman enderman)) return null;
        return enderman;
    }
}
