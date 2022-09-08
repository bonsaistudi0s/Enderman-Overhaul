package io.github.padlocks.EndermanOverhaul.fabric;

import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.fabricmc.api.ModInitializer;

public class EndermanOverhaulFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        EndermanOverhaul.PLATFORM.setup();
    }
}
