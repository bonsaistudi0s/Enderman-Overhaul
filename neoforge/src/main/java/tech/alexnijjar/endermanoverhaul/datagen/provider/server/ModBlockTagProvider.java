package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.tags.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends TagsProvider<Block> {

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, completableFuture, EndermanOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.COAL_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.IRON_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.DIAMOND_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.GOLD_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.REDSTONE_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.LAPIS_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.EMERALD_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.COPPER_ORES.location()));
        tag(ModBlockTags.CAVE_ENDERMAN_HOLDEABLE).add(TagEntry.tag(BlockTags.BASE_STONE_OVERWORLD.location()));
    }
}
