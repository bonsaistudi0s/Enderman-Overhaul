package tech.alexnijjar.endermanoverhaul.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

public class FlashOverlay {
    public static boolean shouldFlash;

    public static void render(PoseStack graphics) {
        if (!shouldFlash) return;
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        int hurtTime = player.hurtTime;
        if (hurtTime < 5) return;
        var damageSource = player.getLastDamageSource();
        if (damageSource == null) return;
        float alpha = (hurtTime - 5) * 0.1f;

        int scaledWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int scaledHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
        GuiComponent.fill(graphics, 0, 0, scaledWidth, scaledHeight, 0xFFFFFFFF);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        if (hurtTime == 5) shouldFlash = false;
    }
}
