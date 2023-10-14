package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

import java.util.Objects;

public class ModEntityTypeTagProvider extends TagsProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, Registry.ENTITY_TYPE, EndermanOverhaul.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        ModEntityTypes.PEARLS.stream().forEach(p -> tag(ModEntityTypeTags.ENDER_PEARLS).add(TagEntry.element(p.getId())));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WARDEN))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WITHER))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON))));
        tag(ModEntityTypeTags.CANT_BE_TELEPORTED).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ARMOR_STAND))));
    }
}
