package tech.alexnijjar.endermanoverhaul.forge;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.forge.EndermanOverhaulClientForge;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

@Mod(EndermanOverhaul.MOD_ID)
public class EndermanOverhaulForge {

    public EndermanOverhaulForge() {
        EndermanOverhaul.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(EndermanOverhaulForge::onAttributes);
        bus.addListener(EndermanOverhaulForge::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            EndermanOverhaulClientForge.init();
        }
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        EndermanOverhaul.postInit();
    }
}
