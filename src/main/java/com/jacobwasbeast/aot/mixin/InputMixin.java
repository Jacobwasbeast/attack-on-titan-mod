package com.jacobwasbeast.aot.mixin;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterKeyBinding;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this class will cancel mouse left and right click input if the player is in ODM mode
@Mixin(MinecraftClient.class)
public class InputMixin {
    @Shadow @Final public GameOptions options;

    @Shadow @Final public Mouse mouse;

    @Inject(at = @At("HEAD"), method = "handleInputEvents")
    public void doAttack(CallbackInfo ci) {
        if (RegisterKeyBinding.odmMode) {
            if (this.options.attackKey.isPressed()) {

            }
            if (this.options.useKey.isPressed()) {
                ClientPlayNetworking.send(PacketHandler.ODM_SHOOT_RIGHT_HOOK, new PacketByteBuf(Unpooled.buffer()));
                System.out.println("Right hook");
            }
        }
    }


}
