package tech.alexnijjar.endermanoverhaul.client.forge;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;
import tech.alexnijjar.endermanoverhaul.common.config.EndermanOverhaulConfig;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EndermanOverhaulClientForge {

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(EndermanOverhaulClientForge::onRegisterClientHud);

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
                ResourcefulConfig config = EndermanOverhaul.CONFIGURATOR.getConfig(EndermanOverhaulConfig.class);
                if (config == null) return null;
                return new ConfigScreen(null, config);
            })
        );
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
