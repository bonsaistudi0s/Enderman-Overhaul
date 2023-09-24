package tech.alexnijjar.endermanoverhaul.networking;

import com.teamresourceful.resourcefullib.common.networking.NetworkChannel;
import com.teamresourceful.resourcefullib.common.networking.base.NetworkDirection;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.networking.messages.ClientboundFlashScreenPacket;

public final class NetworkHandler {
    public static final NetworkChannel CHANNEL = new NetworkChannel(EndermanOverhaul.MOD_ID, 1, "main");

    public static void init() {
        CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, ClientboundFlashScreenPacket.ID, ClientboundFlashScreenPacket.HANDLER, ClientboundFlashScreenPacket.class);
    }
}
