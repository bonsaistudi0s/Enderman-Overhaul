package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagProvider extends TagsProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.ENTITY_TYPE, completableFuture, EndermanOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        ModEntityTypes.PEARLS.stream().forEach(p -> tag(ModEntityTypeTags.ENDER_PEARLS).add(TagEntry.element(p.getId())));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.WARDEN))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.WITHER))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ENDER_DRAGON))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ARMOR_STAND))));

        tag(EntityTypeTags.CAN_BREATHE_UNDER_WATER).add(TagEntry.element(Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(ModEntityTypes.CORAL_ENDERMAN.get()))));
    }
}
