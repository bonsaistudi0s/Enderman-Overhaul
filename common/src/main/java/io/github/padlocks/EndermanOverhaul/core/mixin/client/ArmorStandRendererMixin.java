package io.github.padlocks.EndermanOverhaul.core.mixin.client;

import io.github.padlocks.EndermanOverhaul.core.EndermanOverhaul;
import net.minecraft.client.render.entity.ArmorStandEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.ArmorStandArmorEntityModel;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandEntityRenderer.class)
public abstract class ArmorStandRendererMixin extends LivingEntityRenderer<ArmorStandEntity, ArmorStandArmorEntityModel> {

    @Inject(at = @At("RETURN"), method = "<init>")
    public void onConstruct (EntityRendererFactory.Context ctx, CallbackInfo cbi) {
        //addLayer(new BadlandsHoodRenderer<>(this));
    }


    private ArmorStandRendererMixin(EntityRendererFactory.Context context, ArmorStandArmorEntityModel entityModel, float f){
        super(context, entityModel, f);
        EndermanOverhaul.logger.info("hello from ArmorStandRendererMixin");
    }
}
