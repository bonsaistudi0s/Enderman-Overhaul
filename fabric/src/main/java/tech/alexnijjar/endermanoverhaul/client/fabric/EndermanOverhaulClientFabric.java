package tech.alexnijjar.endermanoverhaul.client.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;
import tech.alexnijjar.endermanoverhaul.client.renderers.items.CorruptedShieldRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderers.items.HoodRenderer;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModBlocks;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

public class EndermanOverhaulClientFabric {

    public static void init() {
        EndermanOverhaulClient.init();
        EndermanOverhaulClient.onRegisterParticles(EndermanOverhaulClientFabric::registerParticles);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TINY_SKULL.get(), RenderType.cutout());
        HudRenderCallback.EVENT.register((graphics, partialTick) -> FlashOverlay.render(graphics));

        GeoArmorRenderer.registerArmorRenderer(new HoodRenderer(ModItems.BADLANDS_HOOD.get()), ModItems.BADLANDS_HOOD.get());
        GeoArmorRenderer.registerArmorRenderer(new HoodRenderer(ModItems.SAVANNAS_HOOD.get()), ModItems.SAVANNAS_HOOD.get());
        GeoArmorRenderer.registerArmorRenderer(new HoodRenderer(ModItems.SNOWY_HOOD.get()), ModItems.SNOWY_HOOD.get());
        GeoItemRenderer.registerItemRenderer(ModItems.CORRUPTED_SHIELD.get(), new CorruptedShieldRenderer());
    }

    private static void registerParticles(ParticleType<SimpleParticleType> particle, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType> provider) {
        ParticleFactoryRegistry.getInstance().register(particle, provider::create);
    }
}
