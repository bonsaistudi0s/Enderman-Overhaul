package io.github.padlocks.EndermanOverhaul.core.mixin.client;

import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandRendererMixin extends LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {

    @Inject(at = @At("RETURN"), method = "<init>")
    public void onConstruct (EntityRendererProvider.Context ctx, CallbackInfo cbi) {
        //addLayer(new BadlandsHoodRenderer<>(this));
    }


    private ArmorStandRendererMixin(EntityRendererProvider.Context context, ArmorStandArmorModel entityModel, float f){
        super(context, entityModel, f);
        EndermanOverhaul.logger.info("hello from ArmorStandRendererMixin");
    }
}
