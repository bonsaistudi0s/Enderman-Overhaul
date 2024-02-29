package tech.alexnijjar.endermanoverhaul.common.network.messages;


import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.DatalessPacketType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;

import java.util.function.Consumer;

public record ClientboundFlashScreenPacket() implements Packet<ClientboundFlashScreenPacket> {

    public static final ServerboundPacketType<ClientboundFlashScreenPacket> TYPE = new Type();

    @Override
    public PacketType<ClientboundFlashScreenPacket> type() {
        return TYPE;
    }

    private static class Type extends DatalessPacketType<ClientboundFlashScreenPacket> implements ServerboundPacketType<ClientboundFlashScreenPacket> {

        public Type() {
            super(ClientboundFlashScreenPacket.class,
                new ResourceLocation(EndermanOverhaul.MOD_ID, "flash_screen"),
                ClientboundFlashScreenPacket::new);
        }

        @Override
        public Consumer<Player> handle(ClientboundFlashScreenPacket message) {
            return player -> FlashOverlay.shouldFlash = true;
        }
    }
}
