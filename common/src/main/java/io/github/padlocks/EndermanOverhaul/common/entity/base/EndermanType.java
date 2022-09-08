package io.github.padlocks.EndermanOverhaul.common.entity.base;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.EnderMan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public record EndermanType(
        ResourceLocation texture,
        ResourceLocation glowingTexture,
        ResourceLocation model,
        ResourceLocation animation,

        int melee,
        Collection<Class<? extends LivingEntity>> attackingEntities,
        Collection<DamageSource> immunities,
        Collection<TagKey> restrictedToBlocks,
        AttributeSupplier attributes,

        Supplier<SoundEvent> deathSound,
        Supplier<SoundEvent> hurtSound,
        Supplier<SoundEvent> ambientSound,
        Supplier<SoundEvent> screamSound,
        Supplier<SoundEvent> stareSound,
        Supplier<SoundEvent> teleportSound,

        boolean usesAngryAnimation,
        boolean provokedWithEyeContact,
        boolean runsWhenAngry,
        boolean canTeleport,
        ParticleOptions particleEffect,
        boolean teleportOnInjury,
        int chanceToSpawnWithRiches,
        boolean canPickupBlocks
        ) {
    public static class Builder {
        private ResourceLocation texture;
        private ResourceLocation glowingTexture;
        private ResourceLocation model;
        private ResourceLocation animation;

        private int melee = 0;
        private final List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private final Collection<DamageSource> immunities = new ArrayList<>();
        private final AttributeSupplier.Builder attributes = EnderMan.createAttributes();
        private Collection<TagKey> restrictedToBlocks = new ArrayList<>();

        private Supplier<SoundEvent> deathSound = () -> SoundEvents.ENDERMAN_DEATH;
        private Supplier<SoundEvent> hurtSound = () -> SoundEvents.ENDERMAN_HURT;
        private Supplier<SoundEvent> ambientSound = () -> SoundEvents.ENDERMAN_AMBIENT;
        private Supplier<SoundEvent> screamSound = () -> SoundEvents.ENDERMAN_SCREAM;
        private Supplier<SoundEvent> stareSound = () -> SoundEvents.ENDERMAN_STARE;
        private Supplier<SoundEvent> teleportSound = () -> SoundEvents.ENDERMAN_TELEPORT;
        private boolean usesAngryAnimation = false;
        private boolean provokedWithEyeContact = true;
        private boolean runsWhenAngry = true;
        private boolean canTeleport = true;
        private ParticleOptions particleEffect = ParticleTypes.PORTAL;
        private boolean teleportOnInjury = false;
        private int chanceToSpawnWithRiches = 0;
        private boolean canPickupBlocks = true;

        public Builder setTexture(ResourceLocation texture) {
            this.texture = texture;
            return this;
        }

        public Builder setGlowingTexture(ResourceLocation glowingTexture) {
            this.glowingTexture = glowingTexture;
            return this;
        }


        public Builder setModel(ResourceLocation model) {
            this.model = model;
            return this;
        }


        public Builder setAnimation(ResourceLocation animation) {
            this.animation = animation;
            return this;
        }

        public Builder setMelee(int melee) {
            this.melee = melee;
            return this;
        }

        public Builder addAttackingEntity(Class<? extends LivingEntity> attackingEntity) {
            this.attackingEntities.add(attackingEntity);
            return this;
        }

        public Builder addAttribute(Attribute attribute, double value) {
            this.attributes.add(attribute, value);
            return this;
        }

        public Builder setDeathSound(Supplier<SoundEvent> sound) {
            this.deathSound = sound;
            return this;
        }

        public Builder setHurtSound(Supplier<SoundEvent> sound) {
            this.hurtSound = sound;
            return this;
        }

        public Builder setAmbientSound(Supplier<SoundEvent> sound) {
            this.ambientSound = sound;
            return this;
        }

        public Builder setScreamSound(Supplier<SoundEvent> sound) {
            this.screamSound = sound;
            return this;
        }

        public Builder setStareSound(Supplier<SoundEvent> sound) {
            this.stareSound = sound;
            return this;
        }

        public Builder setTeleportSound(Supplier<SoundEvent> sound) {
            this.teleportSound = sound;
            return this;
        }

        public Builder setUsesAngryAnimation(boolean bool) {
            this.usesAngryAnimation = bool;
            return this;
        }

        public Boolean getUsesAngryAnimation() {
            return this.usesAngryAnimation;
        }

        public Builder setProvokedWithEyeContact(boolean bool) {
            this.provokedWithEyeContact = bool;
            return this;
        }

        public Boolean getProvokedWithEyeContact() {
            return this.provokedWithEyeContact;
        }

        public Builder setRunsWhenAngry(boolean bool) {
            this.runsWhenAngry = bool;
            return this;
        }

        public Boolean getRunsWhenAngry() {
            return this.runsWhenAngry;
        }

        public Builder setCanTeleport(boolean bool) {
            this.canTeleport = bool;
            return this;
        }

        public Boolean getCanTeleport() {
            return this.canTeleport;
        }

        public Builder setParticleEffect(ParticleOptions effect) {
            this.particleEffect = effect;
            return this;
        }

        public ParticleOptions getParticleEffect() {
            return this.particleEffect;
        }

        public Builder setTeleportOnInjury(boolean bool) {
            this.teleportOnInjury = bool;
            return this;
        }

        public Boolean getTeleportOnInjury() {
            return this.teleportOnInjury;
        }

        public Builder setChanceToSpawnWithRiches(int i) {
            this.chanceToSpawnWithRiches = i;
            return this;
        }

        public float getChanceToSpawnWithRiches() {
            return this.chanceToSpawnWithRiches;
        }

        public Builder setCanPickupBlocks(boolean bool) {
            this.canPickupBlocks = bool;
            return this;
        }

        public Boolean getCanPickupBlocks() {
            return canPickupBlocks;
        }

        public Builder addBlockPickupRestriction(TagKey<?> tag) {
            restrictedToBlocks.add(tag);
            return this;
        }

        public Collection<TagKey> getBlockRestrictionList() {
            return restrictedToBlocks;
        }

        public EndermanType build() {
            return new EndermanType(texture, glowingTexture, model, animation, melee, attackingEntities, immunities, restrictedToBlocks, attributes.build(), deathSound, hurtSound, ambientSound, screamSound, stareSound, teleportSound, usesAngryAnimation, canTeleport, runsWhenAngry, provokedWithEyeContact, particleEffect, teleportOnInjury, chanceToSpawnWithRiches, canPickupBlocks);
        }
    }
}
