package tech.alexnijjar.endermanoverhaul.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.config.EndermanOverhaulClientConfig;
import tech.alexnijjar.endermanoverhaul.client.particles.EndermanParticle;
import tech.alexnijjar.endermanoverhaul.client.renderer.EndIslandsEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.EnderBulletRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.MushroomFieldsEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.ReplacedEndermanRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.base.BaseEndermanEntityRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.items.CorruptedShieldRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.items.HoodRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.summons.ScarabRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderer.summons.SpiritRenderer;
import tech.alexnijjar.endermanoverhaul.client.utils.ClientPlatformUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.registry.ModParticleTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class EndermanOverhaulClient {
    private static final Map<Item, BlockEntityWithoutLevelRenderer> ITEM_RENDERERS = new HashMap<>();
    private static final Map<Item, Supplier<GeoArmorRenderer<?>>> ARMOR_RENDERERS = new HashMap<>();

    public static void init() {
        EndermanOverhaul.CONFIGURATOR.registerConfig(EndermanOverhaulClientConfig.class);
        registerEntityRenderers();
        registerArmorRenderers();
        registerItemRenderers();
        registerItemProperties();
    }

    private static void registerEntityRenderers() {
        if (EndermanOverhaulClientConfig.replaceDefaultEnderman) {
            ClientPlatformUtils.registerRenderer(() -> EntityType.ENDERMAN, ReplacedEndermanRenderer::new);
        }
        ClientPlatformUtils.registerRenderer(ModEntityTypes.BADLANDS_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.BADLANDS_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.CAVE_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.CAVE_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.CRIMSON_FOREST_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.CRIMSON_FOREST_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.DARK_OAK_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.DARK_OAK_ENDERMAN.get(), BaseEndermanEntityRenderer.DARK_OAK_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.DESERT_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.DESERT_ENDERMAN.get(), BaseEndermanEntityRenderer.DESERT_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.END_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.END_ENDERMAN.get(), BaseEndermanEntityRenderer.END_ANIMATION, false));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.END_ISLANDS_ENDERMAN, EndIslandsEndermanRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.FLOWER_FIELDS_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.FLOWER_FIELDS_ENDERMAN.get(), BaseEndermanEntityRenderer.FLOWER_FIELDS_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.ICE_SPIKES_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.ICE_SPIKES_ENDERMAN.get(), BaseEndermanEntityRenderer.ICE_SPIKES_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN, MushroomFieldsEndermanRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.NETHER_WASTES_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.NETHER_WASTES_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.OCEAN_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.OCEAN_ENDERMAN.get(), BaseEndermanEntityRenderer.OCEAN_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SAVANNA_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SAVANNA_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SNOWY_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SNOWY_ENDERMAN.get(), BaseEndermanEntityRenderer.SNOWY_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SOULSAND_VALLEY_ENDERMAN.get(), BaseEndermanEntityRenderer.SOULSAND_VALLEY_ANIMATION, false));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SWAMP_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.SWAMP_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.WARPED_FOREST_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.WARPED_FOREST_ENDERMAN.get(), BaseEndermanEntityRenderer.WARPED_FOREST_ANIMATION));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN.get(), BaseEndermanEntityRenderer.WINDSWEPT_HILLS_ANIMATION));

        // Pets
        ClientPlatformUtils.registerRenderer(ModEntityTypes.PET_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.PET_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.HAMMERHEAD_PET_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.HAMMERHEAD_PET_ENDERMAN.get()));
        ClientPlatformUtils.registerRenderer(ModEntityTypes.AXOLOTL_PET_ENDERMAN, c -> new BaseEndermanEntityRenderer<>(c, ModEntityTypes.AXOLOTL_PET_ENDERMAN.get()));

        // Summons
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SCARAB, ScarabRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SPIRIT, SpiritRenderer::new);

        // Projectiles
        ClientPlatformUtils.registerRenderer(ModEntityTypes.ENDER_BULLET, EnderBulletRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.CORRUPTED_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.ANCIENT_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SOUL_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.BUBBLE_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.SUMMONER_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.ICY_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.CRIMSON_PEARL, ThrownItemRenderer::new);
        ClientPlatformUtils.registerRenderer(ModEntityTypes.WARPED_PEARL, ThrownItemRenderer::new);
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType>> register) {
        register.accept(ModParticleTypes.DUST.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.7f, 0.7f, 0.7f));
        register.accept(ModParticleTypes.SNOW.get(), EndermanParticle.Provider::new);
        register.accept(ModParticleTypes.SAND.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.86f, 0.83f, 0.63f));
        register.accept(ModParticleTypes.SOUL_FIRE_FLAME.get(), EndermanParticle.Provider::new);
        register.accept(ModParticleTypes.BUBBLE.get(), EndermanParticle.Provider::new);
        register.accept(ModParticleTypes.FRIENDERMAN.get(), spriteSet -> new EndermanParticle.Provider(spriteSet, 0.07f, 0.93f, 0.84f));
    }

    public static void registerArmorRenderers() {
        ARMOR_RENDERERS.put(ModItems.BADLANDS_HOOD.get(), () -> new HoodRenderer(ModItems.BADLANDS_HOOD.get()));
        ARMOR_RENDERERS.put(ModItems.SAVANNAS_HOOD.get(), () -> new HoodRenderer(ModItems.SAVANNAS_HOOD.get()));
        ARMOR_RENDERERS.put(ModItems.SNOWY_HOOD.get(), () -> new HoodRenderer(ModItems.SNOWY_HOOD.get()));
    }

    private static void registerItemRenderers() {
        ITEM_RENDERERS.put(ModItems.CORRUPTED_SHIELD.get().asItem(), new CorruptedShieldRenderer());
    }

    public static void registerItemProperties() {
        ClientPlatformUtils.registerItemProperty(ModItems.CORRUPTED_SHIELD.get(), new ResourceLocation("blocking"), (stack, level, entity, i) ->
            entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
    }

    public static GeoArmorRenderer<?> getArmorRenderer(ItemLike item) {
        return ARMOR_RENDERERS.get(item.asItem()).get();
    }

    public static BlockEntityWithoutLevelRenderer getItemRenderer(ItemLike item) {
        return ITEM_RENDERERS.get(item.asItem());
    }
}
