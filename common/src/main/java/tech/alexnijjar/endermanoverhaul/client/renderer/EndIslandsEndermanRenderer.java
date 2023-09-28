package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.CustomEnderEyesLayer;
import tech.alexnijjar.endermanoverhaul.common.entities.EndIslandsEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class EndIslandsEndermanRenderer extends BaseEndermanEntityRenderer<EndIslandsEnderman> {

    public EndIslandsEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<EndIslandsEnderman>(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntityTypes.END_ISLANDS_ENDERMAN.get()), true)
            .withAltTexture(getTexture(ModEntityTypes.END_ISLANDS_ENDERMAN.get()))
            .withAltAnimations(END_ISLANDS_ANIMATION));

        addRenderLayer(new CustomEnderEyesLayer<>(this, new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/end_islands/end_islands_enderman_glow.png")));
        addRenderLayer(new CustomEnderEyesLayer<>(this, new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/end_islands/end_islands_enderman_glow_2.png")));
        addRenderLayer(new CustomEnderEyesLayer<>(this, new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/end_islands/end_islands_enderman_glow_3.png")));
    }

    @Override
    public @NotNull Vec3 getRenderOffset(EndIslandsEnderman entity, float partialTicks) {
        return entity.isPossessing() ? Vec3.ZERO : super.getRenderOffset(entity, partialTicks);
    }
}
