package io.github.padlocks.EndermanOverhaul.core;

import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.client.EntityRendererRegistry;
import io.github.padlocks.EndermanOverhaul.client.model.entity.EndermanModel;
import io.github.padlocks.EndermanOverhaul.client.render.entity.*;
import io.github.padlocks.EndermanOverhaul.common.entity.EndermanTypes;
import io.github.padlocks.EndermanOverhaul.common.entity.base.BaseEnderman;
import io.github.padlocks.EndermanOverhaul.common.entity.base.EndermanType;
import io.github.padlocks.EndermanOverhaul.common.registry.ModEntities;
import io.github.padlocks.EndermanOverhaul.common.registry.ModItems;
import io.github.padlocks.EndermanOverhaul.common.world.gen.ModEntitySpawn;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.function.Supplier;
import java.util.logging.Logger;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

public class EndermanOverhaul {
    public static final String MOD_ID = "endermanoverhaul";
    public static final Logger logger = Logger.getLogger("EndermanOverhaul");

    public static final Platform PLATFORM = Platform.builder(MOD_ID)
            .clientInit(() -> EndermanOverhaul::onClientInit)
            .commonInit(EndermanOverhaul::onCommonInit)
            .build();

    private static void onClientInit() {
        EntityRendererRegistry.register(() -> EntityType.ENDERMAN, ReplacedEndermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.BADLANDS_ENDERMAN, createRenderer(EndermanTypes.BADLANDS));
        EntityRendererRegistry.register(ModEntities.CAVE_ENDERMAN, createRenderer(EndermanTypes.CAVE));
        EntityRendererRegistry.register(ModEntities.DESERT_ENDERMAN, createRenderer(EndermanTypes.DESERT));
        EntityRendererRegistry.register(ModEntities.SAVANNA_ENDERMAN, createRenderer(EndermanTypes.SAVANNA));
        EntityRendererRegistry.register(ModEntities.SNOWY_ENDERMAN, createRenderer(EndermanTypes.SNOWY));
        EntityRendererRegistry.register(ModEntities.FLOWER_ENDERMAN, createRenderer(EndermanTypes.FLOWER));

        HoodArmorRenderer BADLANDS_HOOD_RENDERER = new HoodArmorRenderer(ModItems.BADLANDS_HOOD_MODEL, ModItems.BADLANDS_HOOD);
        GeoArmorRenderer.registerArmorRenderer(BADLANDS_HOOD_RENDERER, ModItems.BADLANDS_HOOD);

        HoodArmorRenderer SAVANNA_HOOD_RENDERER = new HoodArmorRenderer(ModItems.SAVANNA_HOOD_MODEL, ModItems.SAVANNA_HOOD);
        GeoArmorRenderer.registerArmorRenderer(SAVANNA_HOOD_RENDERER, ModItems.SAVANNA_HOOD);

        HoodArmorRenderer SNOWY_HOOD_RENDERER = new HoodArmorRenderer(ModItems.SNOWY_HOOD_MODEL, ModItems.SNOWY_HOOD);
        GeoArmorRenderer.registerArmorRenderer(SNOWY_HOOD_RENDERER, ModItems.SNOWY_HOOD);
    }

    private static void onCommonInit() {
        ModItems.ITEMS.register(PLATFORM);
        ModEntities.ENTITIES.register(PLATFORM);
        ModEntities.registerEntityAttributes();
        ModEntitySpawn.addEntitySpawn();
        GeckoLib.initialize();
    }

    private static <E extends BaseEnderman> EntityRendererFactory<E> createRenderer(EndermanType type) {
        return manager -> new EndermanRenderer<>(manager, new EndermanModel<>(type));
    }

    public static <E extends Entity> void registerRenderer(Supplier<EntityType<E>> entity, EntityRendererFactory<E> renderer) {
        registerRenderer(entity.get(), renderer);
    }
    public static <E extends Entity> void registerRenderer(EntityType<E> entity, EntityRendererFactory<E> renderer) {
        //throw new AssertionError();
    }

    public static void registerReplacedEntity(Class<? extends IAnimatable> clazz, GeoReplacedEntityRenderer renderer) {
        //throw new AssertionError();
    }

    public static Identifier resourceLocation(String string) {
        return new Identifier(MOD_ID, string);
    }
}
