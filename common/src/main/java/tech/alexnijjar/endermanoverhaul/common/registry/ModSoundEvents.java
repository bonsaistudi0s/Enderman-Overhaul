package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModSoundEvents {
    public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, EndermanOverhaul.MOD_ID);

}
