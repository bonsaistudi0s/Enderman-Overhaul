package tech.alexnijjar.endermanoverhaul.mixins.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.items.HoodItem;

@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster implements NeutralMob {

    @Shadow
    @Final
    private static AttributeModifier SPEED_MODIFIER_ATTACKING;

    public EnderManMixin(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("ConstantValue")
    @Inject(
        method = "aiStep",
        at = @At("HEAD"),
        cancellable = true
    )
    private void endermanoverhaul$aiStep(CallbackInfo ci) {
        if ((Object) this instanceof BaseEnderman enderman && (!enderman.hasParticles() || enderman.getCustomParticles() != null)) {
            this.jumping = false;
            if (!this.level().isClientSide()) {
                this.updatePersistentAnger((ServerLevel) this.level(), true);
            }
            super.aiStep();
            ci.cancel();
        }
    }

    @SuppressWarnings("ConstantValue")
    @Inject(
        method = "setTarget",
        at = @At("TAIL")
    )
    private void endermanoverhaul$setTarget(@Nullable LivingEntity target, CallbackInfo ci) {
        if ((Object) this instanceof BaseEnderman enderman && !enderman.speedUpWhenAngry()) {
            AttributeInstance attributeInstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
            attributeInstance.removeModifier(SPEED_MODIFIER_ATTACKING.getId());
        }
    }

    @Inject(
        method = "isLookingAtMe",
        at = @At("HEAD"),
        cancellable = true
    )
    private void endermanoverhaul$isLookingAtMe(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof HoodItem) {
            cir.setReturnValue(false);
        }
    }
}
