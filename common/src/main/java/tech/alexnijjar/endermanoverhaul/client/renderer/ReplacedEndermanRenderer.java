package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomCarriedBlockLayer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.ReplacedEnderman;

public class ReplacedEndermanRenderer extends GeoReplacedEntityRenderer<EnderMan, ReplacedEnderman> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "default/default_enderman");
    public static final ResourceLocation GLOW = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/default/default_enderman_glow.png");

    public ReplacedEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<ReplacedEnderman>(new ResourceLocation(EndermanOverhaul.MOD_ID, "default_enderman"), true)
                .withAltTexture(TEXTURE)
                .withAltAnimations(BaseEndermanEntityRenderer.ANIMATION),
            new ReplacedEnderman());

        addRenderLayer(new CustomEnderEyesLayer<>(this, GLOW));
        addRenderLayer(new CustomCarriedBlockLayer<>(this, renderManager.getBlockRenderDispatcher(), () -> this.currentEntity));
    }

    public @NotNull Vec3 getRenderOffset(EnderMan entity, float partialTicks) {
        if (entity.isCreepy()) {
            Level level = entity.level();
            return new Vec3(level.random.nextGaussian() * 0.02, 0.0, level.random.nextGaussian() * 0.02);
        } else {
            return super.getRenderOffset(entity, partialTicks);
        }
    }
}
