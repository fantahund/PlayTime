package de.fanta.playtime.mixin;

import de.fanta.playtime.GUI;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
abstract class InGameHudMixin {
    private static GUI playtimegui;

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/InGameHud;debugHud:Lnet/minecraft/client/gui/hud/DebugHud;", opcode = Opcodes.GETFIELD, args = {"log=false"}))
    private void beforeRenderDebugScreen2(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (playtimegui == null)
            playtimegui = new GUI();
        playtimegui.onRenderGameOverlayPost(context);
    }
}
