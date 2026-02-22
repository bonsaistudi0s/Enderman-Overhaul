package tech.alexnijjar.endermanoverhaul.client;

import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfoUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.compat.mekanism.ReplacedBabyEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.config.EndermanOverhaulClientConfig;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;
import tech.alexnijjar.endermanoverhaul.client.particles.EndermanParticle;
import tech.alexnijjar.endermanoverhaul.client.renderer.EndIslandsEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.EnderBulletRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.MushroomFieldsEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.ReplacedEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.summons.ScarabRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.summons.SpiritRenderer;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

@EventBusSubscriber(value = Dist.CLIENT, modid = EndermanOverhaul.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EndermanOverhaulClient {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EndermanOverhaul.CONFIGURATOR.register(EndermanOverhaulClientConfig.class);
        ItemProperties.register(ModItems.CORRUPTED_SHIELD.get(), ResourceLocation.withDefaultNamespace("blocking"), (stack, level, entity, i) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
        event.enqueueWork(() -> {
            if (EndermanOverhaulClientConfig.replaceMekanismBabyEnderman && ModInfoUtils.isModLoaded("mekanismadditions")) {
                ReplacedBabyEndermanRenderer.register();
            }
        });
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.DUST.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.7f, 0.7f, 0.7f));
        event.registerSpriteSet(ModParticleTypes.SNOW.get(), EndermanParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.SAND.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.86f, 0.83f, 0.63f));
        event.registerSpriteSet(ModParticleTypes.SOUL_FIRE_FLAME.get(), EndermanParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.BUBBLE.get(), EndermanParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FRIENDERMAN.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.07f, 0.93f, 0.84f));
    }

    @SubscribeEvent
    private static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        if (EndermanOverhaulClientConfig.replaceDefaultEnderman) {
            event.registerEntityRenderer(EntityType.ENDERMAN, ReplacedEndermanRenderer::new);
        }
        event.registerEntityRenderer(ModEntityTypes.BADLANDS_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.BADLANDS_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.CAVE_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.CAVE_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_FOREST_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.CRIMSON_FOREST_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.DARK_OAK_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.DARK_OAK_ENDERMAN.get(), BaseEndermanEntityRenderer.DARK_OAK_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.DESERT_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.DESERT_ENDERMAN.get(), BaseEndermanEntityRenderer.DESERT_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.END_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.END_ENDERMAN.get(), BaseEndermanEntityRenderer.END_ANIMATION, false));
        event.registerEntityRenderer(ModEntityTypes.END_ISLANDS_ENDERMAN.get(), EndIslandsEndermanRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.FLOWER_FIELDS_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.FLOWER_FIELDS_ENDERMAN.get(), BaseEndermanEntityRenderer.FLOWER_FIELDS_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.ICE_SPIKES_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.ICE_SPIKES_ENDERMAN.get(), BaseEndermanEntityRenderer.ICE_SPIKES_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN.get(), MushroomFieldsEndermanRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.NETHER_WASTES_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.NETHER_WASTES_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.CORAL_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.CORAL_ENDERMAN.get(), BaseEndermanEntityRenderer.CORAL_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.SAVANNA_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SAVANNA_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.SNOWY_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SNOWY_ENDERMAN.get(), BaseEndermanEntityRenderer.SNOWY_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SOULSAND_VALLEY_ENDERMAN.get(), BaseEndermanEntityRenderer.SOULSAND_VALLEY_ANIMATION, false));
        event.registerEntityRenderer(ModEntityTypes.SWAMP_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SWAMP_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.WARPED_FOREST_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.WARPED_FOREST_ENDERMAN.get(), BaseEndermanEntityRenderer.WARPED_FOREST_ANIMATION));
        event.registerEntityRenderer(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN.get(), BaseEndermanEntityRenderer.WINDSWEPT_HILLS_ANIMATION));

        // Pets
        event.registerEntityRenderer(ModEntityTypes.PET_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.PET_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.HAMMERHEAD_PET_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.HAMMERHEAD_PET_ENDERMAN.get()));
        event.registerEntityRenderer(ModEntityTypes.AXOLOTL_PET_ENDERMAN.get(), c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.AXOLOTL_PET_ENDERMAN.get()));

        // Summons
        event.registerEntityRenderer(ModEntityTypes.SCARAB.get(), ScarabRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SPIRIT.get(), SpiritRenderer::new);

        // Projectiles
        event.registerEntityRenderer(ModEntityTypes.ENDER_BULLET.get(), EnderBulletRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CORRUPTED_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ANCIENT_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SOUL_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.BUBBLE_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SUMMONER_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ICY_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.WARPED_PEARL.get(), ThrownItemRenderer::new);
    }

    public static void onRegisterClientHud(RenderGuiEvent.Post event) {
        FlashOverlay.render(event.getGuiGraphics());
    }

    public static Level getLevel() {
        return Minecraft.getInstance().level;
    }
}
