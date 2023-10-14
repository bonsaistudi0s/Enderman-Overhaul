package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

import java.util.function.Supplier;

public class CustomCarriedBlockLayer<T extends Entity & IAnimatable> extends GeoLayerRenderer<T> {
    private final BlockRenderDispatcher blockRenderer;
    private final Supplier<EnderMan> enderman;

    public CustomCarriedBlockLayer(IGeoRenderer<T> entityRendererIn, BlockRenderDispatcher blockRenderDispatcher, Supplier<EnderMan> enderman) {
        super(entityRendererIn);
        this.blockRenderer = blockRenderDispatcher;
        this.enderman = enderman;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, T animatable, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        BlockState state = enderman.get().getCarriedBlock();
        if (state == null) return;

        try (var pose = new CloseablePoseStack(poseStack)) {
            float lerped = Mth.rotLerp(partialTick, enderman.get().yBodyRotO, enderman.get().yBodyRot);
            pose.mulPose(Vector3f.YP.rotationDegrees(lerped));
            pose.translate(0, enderman.get().getType().getDimensions().height - 2.9, 0);

            var leftArm = getEntityModel().getModel(getEntityModel().getModelResource(animatable)).getBone("left_arm").orElse(null);
            if (leftArm == null) return;
            poseStack.mulPoseMatrix(leftArm.getLocalSpaceXform());
            pose.translate(0.32, -2.35, 0);

            if (enderman.get() instanceof BaseEnderman base) {
                var offset = base.getHeldBlockOffset();
                pose.translate(offset.x, offset.y, offset.z);
            }

            pose.translate(0, 0.6875f, -0.75f);
            pose.mulPose(Vector3f.XP.rotationDegrees(20f));
            pose.mulPose(Vector3f.YP.rotationDegrees(45f));

            pose.translate(-1.35, 0.4, 1.35);
            pose.scale(0.5f, 0.5f, 0.5f);
            pose.mulPose(Vector3f.YP.rotationDegrees(90));

            this.blockRenderer.renderSingleBlock(state, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
