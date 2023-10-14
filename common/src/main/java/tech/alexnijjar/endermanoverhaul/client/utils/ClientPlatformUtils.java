package tech.alexnijjar.endermanoverhaul.client.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.NotImplementedException;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

import java.util.function.Supplier;

public class ClientPlatformUtils {

    @ExpectPlatform
    public static <T extends Entity> void registerRenderer(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void registerReplacedRenderer(Class<? extends IAnimatable> clazz, GeoReplacedEntityRenderer<?> renderer) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void registerItemProperty(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        throw new NotImplementedException();
    }

    @FunctionalInterface
    public interface SpriteParticleRegistration<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}
