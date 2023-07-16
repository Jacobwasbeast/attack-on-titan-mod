package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.titan.TitanTransformHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class RegisterKeyBinding {
    public static KeyBinding odmInventory;
    public static KeyBinding transform;
    public static KeyBinding odmModeToggle;
    public static boolean odmMode = false;
    static int selectedslot = 0;
    static int prevselectedslot = 0;
    static boolean trans = false;
    public static void register() {
        odmInventory = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aot.odminventory",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,  // replace 'G' with the key you want to use for opening the GUI
                "category.aot.odminventory"
        ));
        odmModeToggle = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aot.odmtoggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_DELETE,  // replace 'G' with the key you want to use for opening the GUI
                "category.aot.odmtoggle"
        ));
        transform = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aot.transform",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,  // replace 'T' with the key you want to use for transforming
                "category.aot.transform"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (odmInventory.wasPressed()) {
                if (client.player != null) {
                    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());

                    // Write player UUID to the buffer
                    passedData.writeUuid(client.player.getUuid());

                    // Send packet to the server to open the ODM inventory
                    ClientPlayNetworking.send(PacketHandler.OPEN_ODM_INVENTORY_PACKET_ID, passedData);
                }
            }

        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (transform.wasPressed()) {
                if (client.player != null) {
                    TitanTransformHandler.sendPacketToServerToTransform(client.player, TitanTransformHandler.getPlayerTitanType(client.player));
                }
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (odmModeToggle.wasPressed()) {
                if (odmMode) {
                    if (client.player == null) {
                        return;
                    }
                    if (!PacketHandler.getODMGearFromTrinkets(client.player).equals(ItemStack.EMPTY)) {
                        if (trans) {
                            client.player.getInventory().selectedSlot = prevselectedslot;
                        }
                    }
                }
                odmMode = !odmMode;

            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (MinecraftClient.getInstance().options.sneakKey.wasPressed()) {
                if (odmMode) {
                    if (client.player == null) {
                        return;
                    }
                    if (!PacketHandler.getODMGearFromTrinkets(client.player).equals(ItemStack.EMPTY)) {
                        if (!trans) {
                            trans = true;
                            prevselectedslot = client.player.getInventory().selectedSlot;
                            client.player.getInventory().selectedSlot = 5;
                        }
                        if (client.player != null) {
                            if (client.player.getInventory().selectedSlot != 5) {
                                if (client.player.getInventory().selectedSlot == 6) {
                                    ClientPlayNetworking.send(PacketHandler.OPEN_ODM_INCREASE_LENGTH, new PacketByteBuf(Unpooled.buffer()));
                                } else {
                                    ClientPlayNetworking.send(PacketHandler.OPEN_ODM_DECREASE_LENGTH, new PacketByteBuf(Unpooled.buffer()));
                                }

                                client.player.getInventory().selectedSlot = 5;
                            }
                        }
                    }
                }
            }
        });
    }
}
