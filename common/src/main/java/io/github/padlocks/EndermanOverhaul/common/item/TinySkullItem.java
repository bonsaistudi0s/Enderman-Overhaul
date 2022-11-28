package io.github.padlocks.EndermanOverhaul.common.item;

import dev.architectury.injectables.annotations.PlatformOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import org.jetbrains.annotations.Nullable;

public class TinySkullItem extends WallStandingBlockItem {
    public TinySkullItem(Block block, Block block2, Item.Settings settings) {
        super(block, block2, settings);
    }

    @PlatformOnly("forge")
    public @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @PlatformOnly("fabric")
    public @Nullable net.fabricmc.fabric.api.item.v1.EquipmentSlotProvider fabric_getEquipmentSlotProvider() {
        return stack -> EquipmentSlot.HEAD;
    }
}