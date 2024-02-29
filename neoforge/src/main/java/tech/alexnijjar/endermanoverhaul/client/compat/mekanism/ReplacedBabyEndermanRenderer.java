package tech.alexnijjar.endermanoverhaul.client.compat.mekanism;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mekanism.additions.common.entity.baby.EntityBabyEnderman;
import mekanism.additions.common.registries.AdditionsEntityTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.ReplacedEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanModel;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomCarriedBlockLayer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;

public class ReplacedBabyEndermanRenderer extends GeoReplacedEntityRenderer<EntityBabyEnderman, ReplacedBabyEnderman> {
    public ReplacedBabyEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BaseEndermanModel<>(
                new ResourceLocation(EndermanOverhaul.MOD_ID, "default_enderman"),
                true,
                ReplacedEndermanRenderer.TEXTURE,
                BaseEndermanEntityRenderer.ANIMATION),
            new ReplacedBabyEnderman());

        addRenderLayer(new CustomEnderEyesLayer<>(this, ReplacedEndermanRenderer.GLOW));
        addRenderLayer(new CustomCarriedBlockLayer<>(this, renderManager.getBlockRenderDispatcher(), () -> this.currentEntity, true));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, ReplacedBabyEnderman animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(0.5F, 0.5F, 0.5F);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }

    @Override
    public void renderRecursively(PoseStack poseStack, ReplacedBabyEnderman animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        boolean isHead = "head".equals(bone.getName());
        if (isHead) {
            poseStack.pushPose();
            poseStack.translate(0.0, -1.25, 0.0);
            poseStack.scale(1.5F, 1.5F, 1.5F);
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        if (isHead) {
            poseStack.popPose();
        }
    }

    @Override
    public @NotNull Vec3 getRenderOffset(EntityBabyEnderman entity, float partialTicks) {
        if (entity.isCreepy()) {
            Level level = entity.level();
            return new Vec3(level.random.nextGaussian() * 0.02, 0.0, level.random.nextGaussian() * 0.02);
        } else {
            return super.getRenderOffset(entity, partialTicks);
        }
    }

    public static void register() {
        EntityRenderers.register(AdditionsEntityTypes.BABY_ENDERMAN.get(), ReplacedBabyEndermanRenderer::new);
    }

    @Override
    public RenderType getRenderType(ReplacedBabyEnderman animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutout(texture);
    }
}
