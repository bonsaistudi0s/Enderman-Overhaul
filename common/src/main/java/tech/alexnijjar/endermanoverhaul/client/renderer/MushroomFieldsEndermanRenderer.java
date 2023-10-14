package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.MushroomFieldsEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class MushroomFieldsEndermanRenderer extends BaseEndermanEntityRenderer<MushroomFieldsEnderman> {

    // Custom renderer without the glowing eyes
    public MushroomFieldsEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager,
            Registry.ENTITY_TYPE.getKey(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN.get()),
            getTexture(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN.get()),
            ANIMATION,
            null,
            true);
    }
}
