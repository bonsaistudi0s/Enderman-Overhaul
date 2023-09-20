package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeTab;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

import java.util.function.Supplier;

public class ModItems {
    public static final ResourcefulRegistry<Item> ITEMS = ResourcefulRegistries.create(BuiltInRegistries.ITEM, EndermanOverhaul.MOD_ID);
    public static final Supplier<CreativeModeTab> TAB = new ResourcefulCreativeTab(new ResourceLocation(EndermanOverhaul.MOD_ID, "main"))
        .setItemIcon(() -> Items.ENDER_PEARL)
        .addRegistry(ITEMS)
        .build();

    public static final RegistryEntry<Item> TINY_SKULL = ITEMS.register("tiny_skull", () -> new BlockItem(ModBlocks.TINY_SKULL.get(), new Item.Properties().stacksTo(16)));
}
