package io.github.padlocks.EndermanOverhaul.forge;

import dev.architectury.platform.forge.EventBuses;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(EndermanOverhaul.MOD_ID)
@Mod.EventBusSubscriber(modid = EndermanOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EndermanOverhaulForge {
    public EndermanOverhaulForge() {
        EndermanOverhaul.PLATFORM.setup();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(EndermanOverhaul.MOD_ID, modBus);
    }
}
