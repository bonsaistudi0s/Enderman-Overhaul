package tech.alexnijjar.endermanoverhaul.client.fabric;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;

public class EndermanOverhaulClientFabric {

    public static void init() {
        EndermanOverhaulClient.init();
        EndermanOverhaulClient.onRegisterParticles(EndermanOverhaulClientFabric::registerParticles);
    }

    private static void registerParticles(ParticleType<SimpleParticleType> particle, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType> provider) {
        ParticleFactoryRegistry.getInstance().register(particle, provider::create);
    }
}
