package tech.alexnijjar.endermanoverhaul.common.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationData;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantComponents;
import tech.alexnijjar.endermanoverhaul.common.items.base.CustomGeoArmorItem;

import java.util.List;

public class HoodItem extends CustomGeoArmorItem {
    public HoodItem(Properties properties) {
        super(HoodMaterial.MATERIAL, EquipmentSlot.CHEST, properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        tooltipComponents.add(ConstantComponents.HOOD_TOOLTIP);
    }
}
