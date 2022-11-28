package io.github.padlocks.EndermanOverhaul.common.registry;

import gg.moonflower.pollen.api.registry.PollinatedBlockRegistry;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import io.github.padlocks.EndermanOverhaul.common.block.TinySkullBlock;
import io.github.padlocks.EndermanOverhaul.common.block.TinySkullWallBlock;
import io.github.padlocks.EndermanOverhaul.common.item.TinySkullItem;
import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class ModBlocks {
    public static final PollinatedRegistry<Block> BLOCKS = PollinatedRegistry.create(Registry.BLOCK, EndermanOverhaul.MOD_ID);
    public static final Block TINY_SKULL = registerBlock("tiny_skull", new TinySkullBlock());
    public static final Block TINY_SKULL_WALL = registerBlock("tiny_skull_wall", new TinySkullWallBlock());
    //public static final Block TINY_SKULL = new TinySkullBlock();

    //public static final Block TINY_SKULL_WALL = new WallBlock(AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque());

    private static <B extends Block> B registerBlock(String name, B block) {
        return BLOCKS.register(name, () -> block).get();
    }
}
