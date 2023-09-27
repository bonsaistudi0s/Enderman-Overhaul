package tech.alexnijjar.endermanoverhaul.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShielditem;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

public class CorruptedShieldRenderer extends GeoItemRenderer<CorruptedShielditem> {
    public CorruptedShieldRenderer() {
        super(new DefaultedItemGeoModel<>(BuiltInRegistries.ITEM.getKey(ModItems.CORRUPTED_SHIELD.get())));
    }

    @Override
    public void actuallyRender(PoseStack poseStack, CorruptedShielditem animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.translate(0.0, -0.5, 0.0);
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
