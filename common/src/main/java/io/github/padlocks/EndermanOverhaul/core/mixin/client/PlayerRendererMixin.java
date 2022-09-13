package io.github.padlocks.EndermanOverhaul.core.mixin.client;

import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerRendererMixin(EntityRendererFactory.Context context, PlayerEntityModel<AbstractClientPlayerEntity> entityModel, float f) {
        super(context, entityModel, f);
        EndermanOverhaul.logger.info("hello from ArmorStandRendererMixin");
    }

    @Inject(at = @At("RETURN"), method = "<init>")
    private void G$init(EntityRendererFactory.Context context, boolean bl, CallbackInfo ci) {
        //this.addLayer(new BadlandsHoodRenderer<>(this));
    }

}
