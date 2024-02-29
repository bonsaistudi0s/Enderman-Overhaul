package tech.alexnijjar.endermanoverhaul.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.neoforge.EndermanOverhaulClientNeoForge;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

@Mod(EndermanOverhaul.MOD_ID)
public class EndermanOverhaulNeoForge {

    public EndermanOverhaulNeoForge(IEventBus bus) {
        EndermanOverhaul.init();
        bus.addListener(EndermanOverhaulNeoForge::onAttributes);
//        bus.addListener(EndermanOverhaulNeoForge::commonSetup);
        if (FMLEnvironment.dist.isClient()) {
            EndermanOverhaulClientNeoForge.init();
        }
    }

    public static void onAttributes(EntityAttributeCreationEvent event) {
        ModEntityTypes.registerAttributes((entityType, attribute) -> event.put(entityType.get(), attribute.get().build()));
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        EndermanOverhaul.postInit();
    }
}
