package tech.alexnijjar.endermanoverhaul.client.renderer.replaced;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoReplacedEntityRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.ReplacedEnderman;

public class ReplacedEndermanRenderer extends GeoReplacedEntityRenderer<EnderMan, ReplacedEnderman> {
    public ReplacedEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ReplacedEndermanModel(), new ReplacedEnderman());
        addRenderLayer(new ReplacedEnderEyesLayer(this));
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
