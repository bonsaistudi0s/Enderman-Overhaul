package io.github.padlocks.EndermanOverhaul.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import io.github.padlocks.EndermanOverhaul.common.entity.base.PassiveEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class FlowerEndermanRenderer extends AnimatedEntityRenderer<PassiveEnderman> {
    public static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields_enderman");
    private static final ResourceLocation[] WALK_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields.walk")};
    private static final ResourceLocation[] RUN_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields.run")};
    private static final ResourceLocation[] IDLE_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields.idle")};

    private boolean isMoving = true;

    public FlowerEndermanRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields_enderman"), 1.0F);
    }

    @Override
    public ResourceLocation[] getAnimations(PassiveEnderman entity) {
        if (isMoving)
            return WALK_ANIMATION;
        else if (entity.isNoAnimationPlaying())
            return IDLE_ANIMATION;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(PassiveEnderman entity) {
        return ENDERMAN_LOCATION;
    }

    @Override
    public void render(PassiveEnderman entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();

        float limbSwingAmount = 0.0F;
        if (entity.isAlive()) {
            limbSwingAmount = Mth.lerp(partialTicks, entity.animationSpeedOld, entity.animationSpeed);

            if (limbSwingAmount > 1.0F) {
                limbSwingAmount = 1.0F;
            }
        }
        this.isMoving = !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}
