package io.github.padlocks.EndermanOverhaul.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.pollen.pinwheel.api.client.animation.AnimatedEntityRenderer;
import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CaveEndermanRenderer extends AnimatedEntityRenderer<BaseEnderman> {
    public static final ResourceLocation ENDERMAN_LOCATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "cave_enderman");
    private static final ResourceLocation[] WALK_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.setup"), new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.walk")};
    private static final ResourceLocation[] RUN_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.setup"), new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.run")};
    private static final ResourceLocation[] ANGRY_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.setup"), new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.angry")};
    private static final ResourceLocation[] IDLE_ANIMATION = new ResourceLocation[]{new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.setup"), new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman.idle")};

    private boolean isMoving = true;

    public CaveEndermanRenderer(EntityRendererProvider.Context context) {
        super(context, new ResourceLocation(EndermanOverhaul.MOD_ID, "cave_enderman"), 1.0F);
    }

    @Override
    public ResourceLocation[] getAnimations(BaseEnderman entity) {
        if (isMoving)
            return WALK_ANIMATION;
        else if (entity.isAngry())
            return ANGRY_ANIMATION;
        return super.getAnimations(entity);
    }

    @Override
    public ResourceLocation getTextureTableLocation(BaseEnderman entity) {
        return ENDERMAN_LOCATION;
    }

    @Override
    public void render(BaseEnderman entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
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
