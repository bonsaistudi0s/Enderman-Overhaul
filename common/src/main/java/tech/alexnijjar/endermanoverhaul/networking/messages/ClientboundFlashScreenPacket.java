package tech.alexnijjar.endermanoverhaul.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.client.gui.FlashOverlay;

public record ClientboundFlashScreenPacket() implements Packet<ClientboundFlashScreenPacket> {

    public static final ResourceLocation ID = new ResourceLocation(EndermanOverhaul.MOD_ID, "flash_screen");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundFlashScreenPacket> getHandler() {
        return HANDLER;
    }

    public static class Handler implements PacketHandler<ClientboundFlashScreenPacket> {
        @Override
        public void encode(ClientboundFlashScreenPacket packet, FriendlyByteBuf buf) {}

        @Override
        public ClientboundFlashScreenPacket decode(FriendlyByteBuf buf) {
            return new ClientboundFlashScreenPacket();
        }

        @Override
        public PacketContext handle(ClientboundFlashScreenPacket packet) {
            return (player, level) -> FlashOverlay.shouldFlash = true;
        }
    }
}
