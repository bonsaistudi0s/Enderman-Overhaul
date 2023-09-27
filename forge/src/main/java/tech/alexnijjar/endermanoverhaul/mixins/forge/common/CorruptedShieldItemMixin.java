package tech.alexnijjar.endermanoverhaul.mixins.forge.common;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.spongepowered.asm.mixin.Mixin;
import tech.alexnijjar.endermanoverhaul.client.EndermanOverhaulClient;
import tech.alexnijjar.endermanoverhaul.common.items.tools.CorruptedShielditem;

import java.util.function.Consumer;

@Mixin(CorruptedShielditem.class)
public abstract class CorruptedShieldItemMixin extends ShieldItem {

    public CorruptedShieldItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
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
}
