package tech.alexnijjar.endermanoverhaul.client.utils.forge;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;

import java.util.function.Supplier;

public class ClientPlatformUtilsImpl {
    public static <T extends Entity> void registerRenderer(Supplier<EntityType<T>> entity, EntityRendererProvider<T> provider) {
        EntityRenderers.register(entity.get(), provider);
    }

    public static void registerItemProperty(Item item, ResourceLocation name, ClampedItemPropertyFunction property) {
        ItemProperties.register(item, name, property);
    }

    public static void registerReplacedRenderer(Class<? extends IAnimatable> clazz, GeoReplacedEntityRenderer<?> renderer) {
    }
}
