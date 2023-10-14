package tech.alexnijjar.endermanoverhaul.common.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantAnimations;

import java.util.List;

public class ReplacedEnderman implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;

            if (!state.isMoving()) {
                state.getController().setAnimation(enderman.isCreepy() ?
                    ConstantAnimations.RUN :
                    ConstantAnimations.WALK);
            } else {
                state.getController().setAnimation(ConstantAnimations.IDLE);
            }
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "creepy_controller", 0, state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (!enderman.isCreepy()) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.HOLDING);
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "hold_controller", 0, state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (enderman.getCarriedBlock() == null) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.HOLDING);
            return PlayState.CONTINUE;
        }));

        data.addAnimationController(new AnimationController<>(this, "attack_controller", 0, state -> {
            EnderMan enderman = getEndermanFromState(state);
            if (enderman == null) return PlayState.STOP;
            if (enderman.getAttackAnim(state.getPartialTick()) == 0) return PlayState.STOP;
            state.getController().setAnimation(ConstantAnimations.HOLDING);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    public EnderMan getEndermanFromState(AnimationEvent<ReplacedEnderman> state) {
        List<LivingEntity> list = state.getExtraDataOfType(LivingEntity.class);
        if (list.isEmpty()) return null;
        Entity entity = list.get(0);
        if (!(entity instanceof EnderMan enderman)) return null;
        return enderman;
    }
}
