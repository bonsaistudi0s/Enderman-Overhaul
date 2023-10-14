package tech.alexnijjar.endermanoverhaul.common.registry.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static Supplier<Item> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int primaryColor, int secondaryColor, Item.Properties properties) {
        return () -> new SpawnEggItem(type.get(), primaryColor, secondaryColor, properties);
    }

    public static CreativeModeTab createTab(ResourceLocation loc, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(loc, icon);
    }
}
