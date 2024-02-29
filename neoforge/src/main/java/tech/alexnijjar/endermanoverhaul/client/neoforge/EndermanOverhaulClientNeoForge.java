package tech.alexnijjar.endermanoverhaul.client.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.common.NeoForge;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EndermanOverhaulClientNeoForge {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(EndermanOverhaulClientNeoForge::onRegisterClientHud);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(EndermanOverhaulClient::init);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        EndermanOverhaulClient.onRegisterParticles((type, provider) -> event.registerSpriteSet(type, provider::create));
    }

    public static void onRegisterClientHud(RenderGuiEvent.Post event) {
        FlashOverlay.render(event.getGuiGraphics());
    }
}
