package de.fanta.playtime;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class GUI {
    private final MinecraftClient minecraft;
    private final TextRenderer fontRenderer;

    public GUI() {
        this.minecraft = MinecraftClient.getInstance();
        this.fontRenderer = minecraft.textRenderer;
        Thread updater = new Thread(() -> {
            try {
                while (true) {


                    Thread.sleep(5* 60 * 1000); // 5 minutes
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        updater.start();
    }

    public void onRenderGameOverlayPost(MatrixStack stack) {
        if (minecraft.options.debugEnabled) {
            return;
        }
        if (minecraft.currentScreen instanceof GameMenuScreen) {
            GlStateManager._clearColor(1.0f, 1.0f, 1.0f, 1.0f);
            renderOnlinetime(stack);
        }
    }

    private void renderOnlinetime(MatrixStack stack) {
        RenderSize result = new RenderSize(0, 0);
        result.width = getWith(result.width, "Playtime: " + TimespanUtil.formatTime(PlaytimeClient.getPlaytime()));
        this.fontRenderer.drawWithShadow(stack, "Playtime: " + TimespanUtil.formatTime(PlaytimeClient.getPlaytime()), 5, (30 + result.height + 9 / 2f), Color.WHITE.getRGB()); //x23
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

