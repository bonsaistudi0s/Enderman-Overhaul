package tech.alexnijjar.endermanoverhaul.common.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModBlockTags {
    public static final TagKey<Block> CAVE_ENDERMAN_HOLDEABLE = tag("cave_enderman_holdable");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(EndermanOverhaul.MOD_ID, name));
    }
}
