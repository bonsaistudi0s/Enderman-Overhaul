package io.github.padlocks.EndermanOverhaul.client.render.entity;

import io.github.padlocks.EndermanOverhaul.client.model.entity.DefaultEndermanModel;
import io.github.padlocks.EndermanOverhaul.common.entity.DefaultEnderman;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

@SuppressWarnings({"unchecked", "rawtypes"})
@Environment(EnvType.CLIENT)
public class DefaultEndermanRenderer extends GeoReplacedEntityRenderer<DefaultEnderman> {
    public DefaultEndermanRenderer(EntityRendererFactory.Context context) {
        super(context, new DefaultEndermanModel(), new DefaultEnderman());
        EndermanOverhaul.registerReplacedEntity(DefaultEnderman.class, this);
    }
}
