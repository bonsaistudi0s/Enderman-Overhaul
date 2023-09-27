package tech.alexnijjar.endermanoverhaul.mixins.fabric.common;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShielditem;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(CorruptedShielditem.class)
public abstract class CorruptedShieldItemMixin extends ShieldItem implements GeoItem {

    @Unique
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public CorruptedShieldItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = EndermanOverhaulClient.getItemRenderer(CorruptedShieldItemMixin.this);
                }

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}
