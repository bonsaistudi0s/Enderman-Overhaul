package tech.alexnijjar.endermanoverhaul.mixins.forge.common;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import tech.alexnijjar.endermanoverhaul.client.renderers.items.HoodRenderer;
import tech.alexnijjar.endermanoverhaul.common.items.base.CustomGeoArmorItem;

import java.util.function.Consumer;

@Mixin(CustomGeoArmorItem.class)
public abstract class CustomGeoArmorItemMixin extends Item {

    public CustomGeoArmorItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null) {
                    this.renderer = new HoodRenderer(itemStack.getItem());
                }

                this.renderer.setCurrentItem(livingEntity, itemStack, equipmentSlot);
                this.renderer.applyEntityStats(original);

                return this.renderer;
            }
        });
    }
}