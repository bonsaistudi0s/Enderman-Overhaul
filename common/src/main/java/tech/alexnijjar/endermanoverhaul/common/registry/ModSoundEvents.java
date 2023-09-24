package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModSoundEvents {
    public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, EndermanOverhaul.MOD_ID);

    public static final RegistryEntry<SoundEvent> CAVE_ENDERMAN_AMBIENT = SOUND_EVENTS.register("cave_enderman_ambient", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "cave_enderman_ambient")));

    public static final RegistryEntry<SoundEvent> CAVE_ENDERMAN_HURT = SOUND_EVENTS.register("cave_enderman_hurt", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "cave_enderman_hurt")));

    public static final RegistryEntry<SoundEvent> PLANT_ENDERMAN_AMBIENT = SOUND_EVENTS.register("plant_enderman_ambient", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "plant_enderman_ambient")));

    public static final RegistryEntry<SoundEvent> PLANT_ENDERMAN_HURT = SOUND_EVENTS.register("plant_enderman_hurt", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "plant_enderman_hurt")));

    public static final RegistryEntry<SoundEvent> TALL_ENDERMAN_AMBIENT = SOUND_EVENTS.register("tall_enderman_ambient", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "tall_enderman_ambient")));

    public static final RegistryEntry<SoundEvent> TALL_ENDERMAN_DEATH = SOUND_EVENTS.register("tall_enderman_death", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "tall_enderman_death")));

    public static final RegistryEntry<SoundEvent> TALL_ENDERMAN_STARE = SOUND_EVENTS.register("tall_enderman_stare", () ->
        SoundEvent.createVariableRangeEvent(new ResourceLocation(EndermanOverhaul.MOD_ID, "tall_enderman_stare")));
}
