package tech.alexnijjar.endermanoverhaul.mixins.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    protected ItemStack useItem;

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
        method = "hurt",
        at = @At("HEAD")
    )
    private void endermanoverhaul$hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!(((Object) this) instanceof Player entity)) return;
        if (level().isClientSide()) return;
        if (!useItem.is(ModItems.CORRUPTED_SHIELD.get())) return;
        if (source.is(DamageTypeTags.IS_FIRE) && entity.hasEffect(MobEffects.FIRE_RESISTANCE)) return;
        if (!(amount >= 3.0f) || !entity.isDamageSourceBlocked(source)) return;
        ((Player) (Object) this).awardStat(Stats.ITEM_USED.get(this.useItem.getItem()));

        int i = 1 + Mth.floor(amount);
        InteractionHand interactionHand = entity.getUsedItemHand();
        this.useItem.hurtAndBreak(i, entity, (player) ->
            player.broadcastBreakEvent(interactionHand));
        if (this.useItem.isEmpty()) {
            if (interactionHand == InteractionHand.MAIN_HAND) {
                this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            } else {
                this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            }

            this.useItem = ItemStack.EMPTY;
            this.playSound(SoundEvents.SHIELD_BREAK, 0.8f, 0.8f + this.level().random.nextFloat() * 0.4f);
        }
    }

    @Inject(
        method = "blockUsingShield",
        at = @At("TAIL")
    )
    private void endermanoverhaul$blockUsingShield(LivingEntity attacker, CallbackInfo ci) {
        if (!(((Object) this) instanceof Player player)) return;
        if (!useItem.is(ModItems.CORRUPTED_SHIELD.get())) return;
        if (attacker.getType().is(ModEntityTypeTags.CANT_BE_TELEPORTED)) return;

        if (attacker.level().random.nextInt(4) != 0) {
            ModUtils.teleportTarget(attacker.level(), attacker, 32);
            attacker.hurt(attacker.damageSources().fall(), 2.0f);
        }

        if (attacker.canDisableShield()) {
            float f = 0.25f + EnchantmentHelper.getBlockEfficiency(player) * 0.0375f;

            if (this.random.nextFloat() < f) {
                player.getCooldowns().addCooldown(ModItems.CORRUPTED_SHIELD.get(), 100);
                player.stopUsingItem();
                this.level().broadcastEntityEvent(this, (byte) 30);
            }
        }
    }
}
