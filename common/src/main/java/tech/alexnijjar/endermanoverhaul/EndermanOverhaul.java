package tech.alexnijjar.endermanoverhaul;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import tech.alexnijjar.endermanoverhaul.common.registry.*;
import tech.alexnijjar.endermanoverhaul.config.EndermanOverhaulConfig;

public class EndermanOverhaul {

    public static final String MOD_ID = "endermanoverhaul";
    public static final Configurator CONFIGURATOR = new Configurator();

    public static void init() {
        CONFIGURATOR.registerConfig(EndermanOverhaulConfig.class);
        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModEntityTypes.ENTITY_TYPES.init();
        ModParticleTypes.PARTICLE_TYPES.init();
        ModSoundEvents.SOUND_EVENTS.init();
    }

    public static void postInit() {
        ModEntityTypes.registerSpawnPlacements();
    }
}
