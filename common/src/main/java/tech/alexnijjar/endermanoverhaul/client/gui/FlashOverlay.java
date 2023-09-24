package tech.alexnijjar.endermanoverhaul.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import tech.alexnijjar.endermanoverhaul.common.entities.EndEnderman;

public class FlashOverlay {
    public static boolean shouldFlash;

    public static void render(GuiGraphics graphics) {
        if (!shouldFlash) return;
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        int hurtTime = player.hurtTime;
        if (hurtTime < 5) return;
        var damageSource = player.getLastDamageSource();
        if (damageSource == null) return;
        var entity = damageSource.getEntity();
        if (!(entity instanceof EndEnderman)) return;
        float alpha = (hurtTime - 5) * 0.05f;

        graphics.setColor(1.0f, 1.0f, 1.0f, alpha);
        graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), 0xFFFFFFFF);
        graphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (hurtTime == 5) shouldFlash = false;
    }
}
