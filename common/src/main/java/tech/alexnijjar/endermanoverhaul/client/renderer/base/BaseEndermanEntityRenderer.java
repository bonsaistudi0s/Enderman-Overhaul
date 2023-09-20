package tech.alexnijjar.endermanoverhaul.client.renderer.base;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

public class BaseEndermanEntityRenderer<T extends BaseEnderman> extends GeoEntityRenderer<T> {
    public static final ResourceLocation ANIMATION = new ResourceLocation(EndermanOverhaul.MOD_ID, "enderman");

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, EntityType<?> enderman) {
        this(renderManager,
            BuiltInRegistries.ENTITY_TYPE.getKey(enderman),
            getTexture(enderman),
            getGlowTexture(enderman));
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, ResourceLocation assetPath, ResourceLocation texture, ResourceLocation glow) {
        this(renderManager, assetPath, texture, ANIMATION, glow);
    }

    public BaseEndermanEntityRenderer(EntityRendererProvider.Context renderManager, ResourceLocation assetPath, ResourceLocation texture, ResourceLocation animation, ResourceLocation glow) {
        super(renderManager, new DefaultedEntityGeoModel<T>(assetPath)
            .withAltTexture(texture)
            .withAltAnimations(animation));
        addRenderLayer(new CustomEnderEyesLayer<>(this, glow));
    }

    public @NotNull Vec3 getRenderOffset(T entity, float partialTicks) {
        if (entity.isCreepy()) {
            Level level = entity.level();
            return new Vec3(level.random.nextGaussian() * 0.02, 0.0, level.random.nextGaussian() * 0.02);
        } else {
            return super.getRenderOffset(entity, partialTicks);
        }
    }

    private static ResourceLocation getTexture(EntityType<?> enderman) {
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(enderman).getPath();
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "%s/%s".formatted(name, name));
    }

    private static ResourceLocation getGlowTexture(EntityType<?> enderman) {
        String name = BuiltInRegistries.ENTITY_TYPE.getKey(enderman).getPath();
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "textures/entity/%s/%s_glow.png".formatted(name, name));
    }
}
