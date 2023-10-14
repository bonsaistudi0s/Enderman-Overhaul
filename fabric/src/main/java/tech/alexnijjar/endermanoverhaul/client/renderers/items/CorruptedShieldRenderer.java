package tech.alexnijjar.endermanoverhaul.client.renderers.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShieldItem;

public class CorruptedShieldRenderer extends GeoItemRenderer<CorruptedShieldItem> {
    public static final ResourceLocation ASSET_SUBPATH = new ResourceLocation(EndermanOverhaul.MOD_ID, "geo/item/corrupted_shield.geo.json");
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/item/corrupted_shield.png");

    public CorruptedShieldRenderer() {
        super(new CorruptedShieldModel());
    }

    @Override
    public void render(ItemStack stack, ItemTransforms.TransformType transformType, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.0, -0.5, 0.0);
        super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
        poseStack.popPose();
    }

    private static class CorruptedShieldModel extends AnimatedGeoModel<CorruptedShieldItem> {
        @Override
        public ResourceLocation getAnimationResource(CorruptedShieldItem entity) {
            return TEXTURE;
        }

        @Override
        public ResourceLocation getModelResource(CorruptedShieldItem entity) {
            return ASSET_SUBPATH;
        }

        @Override
        public ResourceLocation getTextureResource(CorruptedShieldItem entity) {
            return TEXTURE;
        }
    }
}
