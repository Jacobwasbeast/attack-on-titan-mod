package com.jacobwasbeast.aot.mixin;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterKeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HotbarMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "render")
    private void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!PacketHandler.getODMGearFromTrinkets(MinecraftClient.getInstance().player).equals(ItemStack.EMPTY)) {
            if (RegisterKeyBinding.odmMode) {
                // draw ODM MODE is ON in green text
                client.textRenderer.draw(matrices, "ODM MODE is ON", 2, 2, 0x00FF00);
            }
            else {
                // draw ODM MODE is OFF in red text
                client.textRenderer.draw(matrices, "ODM MODE is OFF", 2, 2, 0xFF0000);
            }
        }
    }
}
