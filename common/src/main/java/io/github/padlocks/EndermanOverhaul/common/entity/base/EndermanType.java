package io.github.padlocks.EndermanOverhaul.common.entity.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;

public record EndermanType(
        Identifier texture,
        Identifier glowingTexture,
        Identifier model,

        String walkAnimation,

        String idleAnimation,

        String angryAnimation,

        String runAnimation,

        int melee,
        Collection<Class<? extends LivingEntity>> attackingEntities,
        Collection<DamageSource> immunities,
        Collection<TagKey> restrictedToBlocks,
        DefaultAttributeContainer attributes,

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
        ParticleEffect particleEffect,
        boolean teleportOnInjury,
        int chanceToSpawnWithRiches,
        boolean canPickupBlocks
        ) {
    public static class Builder {
        private Identifier texture;
        private Identifier glowingTexture;
        private Identifier model;
        private String walkAnimation = "animation.enderman.walk";
        private String idleAnimation = "animation.enderman.idle";
        private String angryAnimation = "animation.enderman.angry";
        private String runAnimation = "animation.enderman.run";

        private int melee = 0;
        private final List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private final Collection<DamageSource> immunities = new ArrayList<>();
        private final DefaultAttributeContainer.Builder attributes = EndermanEntity.createEndermanAttributes();
        private final Collection<TagKey> restrictedToBlocks = new ArrayList<>();

        private Supplier<SoundEvent> deathSound = () -> SoundEvents.ENTITY_ENDERMAN_DEATH;
        private Supplier<SoundEvent> hurtSound = () -> SoundEvents.ENTITY_ENDERMAN_HURT;
        private Supplier<SoundEvent> ambientSound = () -> SoundEvents.ENTITY_ENDERMAN_AMBIENT;
        private Supplier<SoundEvent> screamSound = () -> SoundEvents.ENTITY_ENDERMAN_SCREAM;
        private Supplier<SoundEvent> stareSound = () -> SoundEvents.ENTITY_ENDERMAN_STARE;
        private Supplier<SoundEvent> teleportSound = () -> SoundEvents.ENTITY_ENDERMAN_TELEPORT;
        private boolean usesAngryAnimation = false;
        private boolean provokedWithEyeContact = true;
        private boolean runsWhenAngry = true;
        private boolean canTeleport = true;
        private ParticleEffect particleEffect = ParticleTypes.PORTAL;
        private boolean teleportOnInjury = false;
        private int chanceToSpawnWithRiches = 0;
        private boolean canPickupBlocks = true;

        public Builder setTexture(Identifier texture) {
            this.texture = texture;
            return this;
        }

        public Builder setGlowingTexture(Identifier glowingTexture) {
            this.glowingTexture = glowingTexture;
            return this;
        }


        public Builder setModel(Identifier model) {
            this.model = model;
            return this;
        }

        public Builder setWalkAnimation(String animation) {
            this.walkAnimation = animation;
            return this;
        }

        public Builder setIdleAnimation(String animation) {
            this.idleAnimation = animation;
            return this;
        }

        public Builder setAngryAnimation(String animation) {
            this.angryAnimation = animation;
            return this;
        }

        public Builder setRunAnimation(String animation) {
            this.runAnimation = animation;
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

        public Builder addAttribute(EntityAttribute attribute, double value) {
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

        public Builder setParticleEffect(ParticleEffect effect) {
            this.particleEffect = effect;
            return this;
        }

        public ParticleEffect getParticleEffect() {
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
            return new EndermanType(texture, glowingTexture, model, walkAnimation, idleAnimation, angryAnimation, runAnimation, melee, attackingEntities, immunities, restrictedToBlocks, attributes.build(), deathSound, hurtSound, ambientSound, screamSound, stareSound, teleportSound, usesAngryAnimation, canTeleport, runsWhenAngry, provokedWithEyeContact, particleEffect, teleportOnInjury, chanceToSpawnWithRiches, canPickupBlocks);
        }
    }
}
