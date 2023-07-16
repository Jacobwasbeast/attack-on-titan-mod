package com.jacobwasbeast.aot;

import com.jacobwasbeast.aot.item.ODM.ODMGearItem;
import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.*;
import com.jacobwasbeast.aot.titan.TitanTransformHandler;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class MainClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        RegisterKeyBinding.register();
        RegisterOther.register();
        RegisterGUI.registerClient();
        registerClientReceivers();
        RegisterRenderer.register();
    }

    @Environment(EnvType.CLIENT)
    public static void registerClientReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(PacketHandler.TRANSFORM_ANIMATION_PACKET_ID, (client, handler, buf, responseSender) -> {
            UUID playerUuid = buf.readUuid();

            client.execute(() -> {
                // Assuming MinecraftClient#world and MinecraftClient#player are accessible
                PlayerEntity transformingPlayer = client.world.getPlayerByUuid(playerUuid);
                if (transformingPlayer != null) {
                    // TODO: Start transformation animation for transformingPlayer
                    LightningEntity lighting = new LightningEntity(EntityType.LIGHTNING_BOLT, client.world);
                    lighting.setPos(transformingPlayer.getX(), transformingPlayer.getY(), transformingPlayer.getZ());
                }
            });
        });
    }
}
