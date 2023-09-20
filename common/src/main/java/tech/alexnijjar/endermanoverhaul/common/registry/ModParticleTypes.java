package tech.alexnijjar.endermanoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

public class ModParticleTypes {
    public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, EndermanOverhaul.MOD_ID);

    public static final RegistryEntry<SimpleParticleType> DUST = PARTICLE_TYPES.register("dust", () -> new SimpleParticleType(false) {});
    public static final RegistryEntry<SimpleParticleType> SNOW = PARTICLE_TYPES.register("snow", () -> new SimpleParticleType(false) {});
    public static final RegistryEntry<SimpleParticleType> SAND = PARTICLE_TYPES.register("sand", () -> new SimpleParticleType(false) {});
    public static final RegistryEntry<SimpleParticleType> SOUL_FIRE_FLAME = PARTICLE_TYPES.register("soul_fire", () -> new SimpleParticleType(true) {});
    public static final RegistryEntry<SimpleParticleType> BUBBLE = PARTICLE_TYPES.register("bubble", () -> new SimpleParticleType(false) {});
}
