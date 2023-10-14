package tech.alexnijjar.endermanoverhaul.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import software.bernie.geckolib3.util.RenderUtils;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.ReplacedEndermanCarriedBlockLayer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.ReplacedEndermanEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.ReplacedEndermanModel;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;
import tech.alexnijjar.endermanoverhaul.common.entities.ReplacedEnderman;

public class ReplacedEndermanRenderer extends GeoReplacedEntityRenderer<ReplacedEnderman> {
    public static final ResourceLocation MODEL = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/entity/default_enderman.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "default/default_enderman");
    public static final ResourceLocation GLOW = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman_glow.png");

    protected EnderMan currentEntity;

    public ReplacedEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedEndermanModel<>(
                new ResourceLocation(EndermanOverhaul.MOD_ID, "default_enderman"),
                true,
                TEXTURE,
                BaseEndermanEntityRenderer.ANIMATION),
            new ReplacedEnderman());
        ClientPlatformUtils.registerReplacedRenderer(ReplacedEnderman.class, this);

        ((ReplacedEndermanModel) getGeoModelProvider()).setEnderman(() -> getCurrentEntity());

        addLayer(new ReplacedEndermanEnderEyesLayer<>(this, GLOW));
        addLayer(new ReplacedEndermanCarriedBlockLayer<>(this, renderManager.getBlockRenderDispatcher()));
    }

    private EnderMan getCurrentEntity() {
        return currentEntity;
    }

    @Override
    public @NotNull Vec3 getRenderOffset(@NotNull Entity entity, float partialTicks) {
        this.currentEntity = (EnderMan) entity;
        if (!(entity instanceof EnderMan enderman)) return super.getRenderOffset(entity, partialTicks);
        if (enderman.isCreepy()) {
            Level level = entity.level;
            return new Vec3(level.random.nextGaussian() * 0.02, 0.0, level.random.nextGaussian() * 0.02);
        } else {
            return super.getRenderOffset(entity, partialTicks);
        }
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight,
                                  int packedOverlay, float red, float green, float blue, float alpha) {
        if (bone.isTrackingXform()) {
            Entity entity = this.currentEntity;
            Matrix4f poseState = poseStack.last().pose().copy();
            Matrix4f localMatrix = RenderUtils.invertAndMultiplyMatrices(poseState, this.dispatchedMat);

            bone.setModelSpaceXform(RenderUtils.invertAndMultiplyMatrices(poseState, this.renderEarlyMat));
            localMatrix.translate(new Vector3f(getRenderOffset(entity, 1)));
            bone.setLocalSpaceXform(localMatrix);

            Matrix4f worldState = localMatrix.copy();

            worldState.translate(new Vector3f(entity.position()));
            bone.setWorldSpaceXform(worldState);
        }

        poseStack.pushPose();
        RenderUtils.prepMatrixForBone(poseStack, bone);
        renderCubesOfBone(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        renderChildBones(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
