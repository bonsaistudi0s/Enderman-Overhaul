package tech.alexnijjar.endermanoverhaul.mixins.common;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;

@Debug(export = true)
@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster implements NeutralMob {

    public EnderManMixin(EntityType<? extends EnderMan> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("ConstantValue")
    @Inject(method = "aiStep", at = @At("HEAD"), cancellable = true)
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
}
