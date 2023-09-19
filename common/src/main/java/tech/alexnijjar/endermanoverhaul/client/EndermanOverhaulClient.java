package tech.alexnijjar.endermanoverhaul.client;

import net.minecraft.world.entity.EntityType;
import tech.alexnijjar.endermanoverhaul.client.renderer.CaveEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.ReplacedEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class EndermanOverhaulClient {
    public static void init() {
        registerEntityRenderers();
    }

    private static void registerEntityRenderers() {
        ClientPlatformUtils.registerRenderer(() -> EntityType.ENDERMAN, ReplacedEndermanRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.CAVE_ENDERMAN, CaveEndermanRenderer::new);
    }
}
