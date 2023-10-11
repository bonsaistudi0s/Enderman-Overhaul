package tech.alexnijjar.endermanoverhaul.mixins.common;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

@Mixin(TheEndGatewayBlockEntity.class)
public abstract class TheEndGatewayBlockEntityMixin {

    @Shadow
    private boolean exactTeleport;
    @Shadow
    private BlockPos exitPortal;

    @Inject(
        method = "teleportEntity",
        at = @At(
            target = "Lnet/minecraft/world/level/block/entity/TheEndGatewayBlockEntity;teleportEntity(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/block/entity/TheEndGatewayBlockEntity;)V",
            value = "HEAD")
    )
    private static void endermanoverhaul$teleportEntity(Level level, BlockPos pos, BlockState state, Entity entity, TheEndGatewayBlockEntity blockEntity, CallbackInfo ci) {
        if (entity.getType().is(ModEntityTypeTags.ENDER_PEARLS)) {
            Entity owner = ((Projectile) entity).getOwner();
            if (owner instanceof ServerPlayer) {
                CriteriaTriggers.ENTER_BLOCK.trigger((ServerPlayer) owner, state);
            }

            if (!((Object) blockEntity instanceof TheEndGatewayBlockEntityMixin gateway)) return;

            BlockPos blockPos = gateway.exactTeleport ? gateway.exitPortal : findExitPosition(level, gateway.exitPortal);
            if (owner != null) {
                owner.setPortalCooldown();
                owner.teleportToWithTicket(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                entity.discard();
            } else {
                entity.setPortalCooldown();
                entity.teleportToWithTicket(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
            }
        }
    }

    @Shadow
    private static BlockPos findExitPosition(Level level, BlockPos pos) {
        return null;
    }
}
