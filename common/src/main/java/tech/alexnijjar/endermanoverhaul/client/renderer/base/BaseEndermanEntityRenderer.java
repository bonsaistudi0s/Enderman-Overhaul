package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

public class BaseEndermanEntityRenderer<T extends BaseEnderman> extends GeoEntityRenderer<T> {
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman");
    public static final ResourceLocation ICE_SPIKES_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "ice_spikes_enderman");
    public static final ResourceLocation SNOWY_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "snowy_enderman");
    public static final ResourceLocation DESERT_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "desert_enderman");
    public static final ResourceLocation SOULSAND_VALLEY_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "soulsand_valley_enderman");
    public static final ResourceLocation END_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "end_enderman");
    public static final ResourceLocation FLOWER_FIELDS_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "flower_fields_enderman");
    public static final ResourceLocation END_ISLANDS_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "end_islands_enderman");
    public static final ResourceLocation WARPED_FOREST_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "warped_forest_enderman");
    public static final ResourceLocation OCEAN_ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "ocean_enderman");

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, EntityType<?> enderman) {
        this(renderManager, enderman, ANIMATION);
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, EntityType<?> enderman, ResourceLocation animation) {
        this(renderManager, enderman, animation, true);
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, EntityType<?> enderman, ResourceLocation animation, boolean turnsHead) {
        this(renderManager,
            BuiltInRegistries.ENTITY_TYPE.getKey(enderman),
            getTexture(enderman),
            animation,
            getGlowTexture(enderman),
            turnsHead);
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, ResourceLocation assetPath, ResourceLocation texture, ResourceLocation animation, ResourceLocation glow, boolean turnsHead) {
        super(renderManager, new BaseEndermanModel<>(assetPath, turnsHead, texture, animation));
        if (glow != null) {
            addRenderLayer(new CustomEnderEyesLayer<>(this, glow));
        }
        addRenderLayer(new CustomCarriedBlockLayer<>(this, renderManager.getBlockRenderDispatcher(), () -> this.animatable));
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, BaseEndermanModel<T> model) {
        super(renderManager, model);
    }

    @Override
    public @NotNull Vec3 getRenderOffset(T entity, float partialTicks) {
        if (entity.isCreepy() && entity.canShake()) {
            Level level = entity.level();
            return new Vec3(level.random.nextGaussian() * 0.02, 0.0, level.random.nextGaussian() * 0.02);
        } else {
            return super.getRenderOffset(entity, partialTicks);
        }
    }

    public static ResourceLocation getTexture(EntityType<?> enderman) {
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(enderman).getPath();
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "%s/%s".formatted(name.replace("_enderman", ""), name));
    }

    public static ResourceLocation getGlowTexture(EntityType<?> enderman) {
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(enderman).getPath();
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/%s/%s_glow.png".formatted(name.replace("_enderman", ""), name));
    }
}
