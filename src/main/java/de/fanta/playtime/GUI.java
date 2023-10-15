package de.fanta.playtime;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;

import java.awt.*;

public class GUI {
    private final MinecraftClient minecraft;
    private final TextRenderer fontRenderer;

    public GUI() {
        this.minecraft = MinecraftClient.getInstance();
        this.fontRenderer = minecraft.textRenderer;
    }

    public void onRenderGameOverlayPost(DrawContext context) {
        if (minecraft.getDebugHud().shouldShowDebugHud()) {
            return;
        }
        if (minecraft.currentScreen instanceof GameMenuScreen) {
            GlStateManager._clearColor(1.0f, 1.0f, 1.0f, 1.0f);
            renderOnlinetime(context);
        }
    }

    private void renderOnlinetime(DrawContext context) {
        RenderSize result = new RenderSize(0, 0);
        result.width = getWith(result.width, "Playtime: " + TimespanUtil.formatTime(PlaytimeClient.getPlaytime()));
        context.drawText(this.fontRenderer, "Playtime: " + TimespanUtil.formatTime(PlaytimeClient.getPlaytime()), 5, (30 + result.height + 9 / 2), Color.WHITE.getRGB(), true);
    }

    private int getWith(int resultWidth, String text) {
        int width = this.fontRenderer.getWidth(text);
        return Math.max(width, resultWidth);
    }

    private static class RenderSize {
        int width;
        int height;

        RenderSize(int w, int h) {
            this.width = w;
            this.height = h;
        }
    }
}

