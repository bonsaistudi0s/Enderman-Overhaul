package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class BaseEndermanModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final boolean turnsHead;
    private final ResourceLocation assetSubpath;
    private final ResourceLocation texture;
    private final ResourceLocation animation;

    public BaseEndermanModel(ResourceLocation assetSubpath, boolean turnsHead, ResourceLocation texture, ResourceLocation animation) {
        this.texture = buildFormattedTexturePath(texture);
        this.assetSubpath = buildFormattedModelPath(assetSubpath);
        this.animation = buildFormattedAnimationPath(animation);
        this.turnsHead = turnsHead;
    }

    public ResourceLocation buildFormattedModelPath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "geo/entity/" + basePath.getPath() + ".geo.json");
    }

    public ResourceLocation buildFormattedAnimationPath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "animations/entity/" + basePath.getPath() + ".animation.json");
    }

    public ResourceLocation buildFormattedTexturePath(ResourceLocation basePath) {
        return new ResourceLocation(basePath.getNamespace(), "textures/entity/" + basePath.getPath() + ".png");
    }

    @Override
    public void setCustomAnimations(T animatable, int instanceId, AnimationEvent animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (!turnsHead) return;

        GeoBone head = (GeoBone) getAnimationProcessor().getBone("head");
        if (head == null) return;
        if (head.childBones.isEmpty()) return;
        IBone headRotation = head.childBones.get(0);
        if (headRotation == null) return;

        EntityModelData entityData = (EntityModelData) animationState.getExtraDataOfType(EntityModelData.class).get(0);
        headRotation.setRotationX(entityData.headPitch * Mth.DEG_TO_RAD);
        headRotation.setRotationY(entityData.netHeadYaw * Mth.DEG_TO_RAD);
    }

    @Override
    public ResourceLocation getModelResource(T object) {
        return assetSubpath;
    }

    @Override
    public ResourceLocation getTextureResource(T object) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }
}
