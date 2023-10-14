package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ReplacedEndermanModel<T extends IAnimatable> extends BaseEndermanModel<T> {
    private Supplier<EnderMan> enderman;

    public ReplacedEndermanModel(ResourceLocation assetSubpath, boolean turnsHead, ResourceLocation texture, ResourceLocation animation) {
        super(assetSubpath, turnsHead, texture, animation);
    }

    public void setEnderman(Supplier<EnderMan> enderman) {
        this.enderman = enderman;
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationState) {
        List<Object> data = new ArrayList<>(animationState.getExtraData());
        data.add(enderman.get());

        AnimationEvent<?> event = new AnimationEvent<>(
            animatable,
            animationState.getLimbSwing(),
            animationState.getLimbSwingAmount(),
            animationState.getPartialTick(),
            animationState.isMoving(),
            data);
        super.setCustomAnimations(animatable, instanceId, event);
    }
}
