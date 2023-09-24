package tech.alexnijjar.endermanoverhaul.client.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModBlocks;

public class EndermanOverhaulClientFabric {

    public static void init() {
        EndermanOverhaulClient.init();
        EndermanOverhaulClient.onRegisterParticles(EndermanOverhaulClientFabric::registerParticles);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINY_SKULL.get(), RenderType.cutout());
        HudRenderCallback.EVENT.register((graphics, partialTick) -> FlashOverlay.render(graphics));
    }

    private static void registerParticles(ParticleType<SimpleParticleType> particle, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType> provider) {
        ParticleFactoryRegistry.getInstance().register(particle, provider::create);
    }
}
