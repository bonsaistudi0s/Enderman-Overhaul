package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends EntityTypeTagsProvider {

    public ModEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, completableFuture, EndermanOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ModEntityTypes.PEARLS.stream().forEach(pearl -> tag(ModEntityTypeTags.ENDER_PEARLS).add(pearl.get()));
        tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(ModEntityTypes.CORAL_ENDERMAN.get());
        tag(EntityTypeTags.ARTHROPOD).add(ModEntityTypes.SCARAB.get());
        tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(ModEntityTypes.SCARAB.get());
    }
}
