package tech.alexnijjar.endermanoverhaul;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import tech.alexnijjar.endermanoverhaul.common.registry.ModBlocks;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.config.EndermanOverhaulConfig;

public class EndermanOverhaul {

    public static final String MOD_ID = "endermanoverhaul";
    public static final Configurator CONFIGURATOR = new Configurator();

    public static void init() {
        CONFIGURATOR.registerConfig(EndermanOverhaulConfig.class);
        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModEntityTypes.ENTITY_TYPES.init();
    }

    public static void postInit() {
        ModEntityTypes.registerSpawnPlacements();
    }
}
