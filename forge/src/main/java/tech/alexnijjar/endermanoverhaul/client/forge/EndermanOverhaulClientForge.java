package tech.alexnijjar.endermanoverhaul.client.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EndermanOverhaulClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(EndermanOverhaulClient::init);
    }

    @SubscribeEvent
    private static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        EndermanOverhaulClient.onRegisterParticles((type, provider) -> event.registerSpriteSet(type, provider::create));
    }
}
