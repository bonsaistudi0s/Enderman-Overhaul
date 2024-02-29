package tech.alexnijjar.endermanoverhaul.common;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import tech.alexnijjar.endermanoverhaul.common.network.NetworkHandler;
import tech.alexnijjar.endermanoverhaul.common.network.messages.ClientboundFlashScreenPacket;

public class ModUtils {
    public static <T extends ParticleOptions> void sendParticles(ServerLevel level, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    public static void teleportTarget(Level level, LivingEntity target, int range) {
        if (level.isClientSide()) return;
        double x = target.getX();
        double y = target.getY();
        double z = target.getZ();

        for (int i = 0; i < range; i++) {
            double g = target.getX() + (level.random.nextDouble() - 0.5) * range;
            double h = Mth.clamp(target.getY() + (level.random.nextInt(range) - 8), level.getMinBuildHeight(), level.getMinBuildHeight() + ((ServerLevel) level).getLogicalHeight() - 1);
            double j = target.getZ() + (level.random.nextDouble() - 0.5) * range;
            if (target.isPassenger()) {
                target.stopRiding();
            }

            Vec3 position = target.position();
            if (target.randomTeleport(g, h, j, true)) {
                level.gameEvent(GameEvent.TELEPORT, position, GameEvent.Context.of(target));
                SoundEvent soundEvent = SoundEvents.CHORUS_FRUIT_TELEPORT;
                level.playSound(null, x, y, z, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (target instanceof Player player) {
                    NetworkHandler.CHANNEL.sendToPlayer(new ClientboundFlashScreenPacket(), player);
                }
                target.playSound(soundEvent, 1.0F, 1.0F);
                break;
            }
        }
    }
}
