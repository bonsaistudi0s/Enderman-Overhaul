package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

import java.util.function.Supplier;

public class CustomCarriedBlockLayer<T extends GeoAnimatable> extends GeoRenderLayer<T> {
    private final BlockRenderDispatcher blockRenderer;
    private final Supplier<EnderMan> enderman;
    private final boolean baby;

    public CustomCarriedBlockLayer(GeoRenderer<T> entityRendererIn, BlockRenderDispatcher blockRenderDispatcher, Supplier<EnderMan> enderman, boolean baby) {
        super(entityRendererIn);
        this.blockRenderer = blockRenderDispatcher;
        this.enderman = enderman;
        this.baby = baby;
    }

    @Override
    public void render(PoseStack poseStack, T animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        BlockState state = enderman.get().getCarriedBlock();
        if (state == null) return;

        try (var pose = new CloseablePoseStack(poseStack)) {
            float lerped = Mth.rotLerp(partialTick, enderman.get().yBodyRotO, enderman.get().yBodyRot);
            pose.mulPose(Axis.YP.rotationDegrees(-lerped));
            pose.translate(0, enderman.get().getType().getDimensions().height - 2.9, 0);

            var leftArm = getGeoModel().getBone("left_arm").orElse(null);
            if (leftArm == null) return;
            poseStack.mulPoseMatrix(leftArm.getModelRotationMatrix());
            pose.mulPose(Axis.XP.rotationDegrees(-28.6479f));
            pose.translate(0.25, -2.2, 0);

            if (enderman.get() instanceof BaseEnderman base) {
                var offset = base.getHeldBlockOffset();
                pose.translate(offset.x, offset.y, offset.z);
            }

            if (baby) {
                pose.translate(0, -0.3, -0.3f);
            }

            pose.translate(0, 0.6875f, -0.75f);
            pose.mulPose(Axis.XP.rotationDegrees(20f));
            pose.mulPose(Axis.YP.rotationDegrees(45f));

            pose.translate(-1.35, 0.4, 1.35);
            pose.scale(0.5f, 0.5f, 0.5f);
            pose.mulPose(Axis.YP.rotationDegrees(90));

            this.blockRenderer.renderSingleBlock(state, poseStack, bufferSource, packedLight, OverlayTexture.NO_OVERLAY);
        }
    }
}
