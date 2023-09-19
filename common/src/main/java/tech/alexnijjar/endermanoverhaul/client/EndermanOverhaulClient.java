package tech.alexnijjar.endermanoverhaul.client;

import net.minecraft.world.entity.EntityType;
import tech.alexnijjar.endermanoverhaul.client.renderer.replaced.ReplacedEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;

public class EndermanOverhaulClient {

    public static void init() {
        registerEntityRenderers();
    }

    private static void registerEntityRenderers() {
        ClientPlatformUtils.registerRenderer(() -> EntityType.ENDERMAN, ReplacedEndermanRenderer::new);
    }
}
