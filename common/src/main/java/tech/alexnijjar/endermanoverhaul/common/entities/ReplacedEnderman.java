package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
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

public class ReplacedEnderman implements GeoReplacedEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public EntityType<?> getReplacingEntityType() {
        return EntityType.ENDERMAN;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;

            if (state.isMoving()) {
                state.getController().setAnimation(enderman.isCreepy() ?
                    ConstantAnimations.RUN :
                    ConstantAnimations.WALK);
            } else {
                state.getController().setAnimation(ConstantAnimations.IDLE);
            }
            return PlayState.CONTINUE;
        }));

        controllerRegistrar.add(new AnimationController<>(this, "creepy_controller", state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (!enderman.isCreepy()) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.ANGRY);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Nullable
    private EnderMan getEndermanFromState(AnimationState<ReplacedEnderman> state) {
        Entity entity = state.getData(DataTickets.ENTITY);
        if (!(entity instanceof EnderMan enderman)) return null;
        return enderman;
    }
}
