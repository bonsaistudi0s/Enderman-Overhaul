package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BaseEndermanModel<T extends GeoAnimatable> extends DefaultedEntityGeoModel<T> {
    private final boolean turnsHead;

    public BaseEndermanModel(ResourceLocation assetSubpath, boolean turnsHead, ResourceLocation texture, ResourceLocation animation) {
        super(assetSubpath, turnsHead);
        this.withAltTexture(texture);
        this.withAltAnimations(animation);
        this.turnsHead = turnsHead;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        if (!turnsHead) return;

        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head == null) return;
        if (head.getChildBones().isEmpty()) return;
        CoreGeoBone headRotation = head.getChildBones().get(0);
        if (headRotation == null) return;

        EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
        head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
    }
}
