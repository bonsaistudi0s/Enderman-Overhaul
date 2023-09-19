package tech.alexnijjar.endermanoverhaul.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.common.entities.CaveEnderman;

public class CaveEndermanRenderer extends BaseEndermanEntityRenderer<CaveEnderman> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(EndermanOverhaul.MOD_ID, "cave/cave_enderman");
    public static final ResourceLocation GLOW = new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/cave/cave_enderman_glow.png");

    public CaveEndermanRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ResourceLocation(EndermanOverhaul.MOD_ID, "cave_enderman"), TEXTURE, GLOW);
    }
}
