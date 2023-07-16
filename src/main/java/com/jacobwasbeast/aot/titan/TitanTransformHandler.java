package com.jacobwasbeast.aot.titan;

import com.jacobwasbeast.aot.network.PacketHandler;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class TitanTransformHandler {

    // Let's assume the tag for titan form is "in_titan_form"
    public static TitanType getPlayerTitanType(PlayerEntity player) {
        for (String tag : player.getCommandTags()) {
            if (tag.startsWith("titan_type:")) {
                return TitanType.valueOf(tag.substring(11));
            }
        }
        return TitanType.NONE;
    }

    public static void setPlayerTitanForm(PlayerEntity player, TitanType type) {
        player.getCommandTags().add("titan_type:" + type.name());
        sendPacketToServerToTransform(player, type);
    }
    public static void setTransformed(PlayerEntity player, TitanType type) {
        if (type.equals(TitanType.NONE)) {
            player.getCommandTags().remove("transformed");
            return;
        }
        player.getCommandTags().add("transformed");
    }
    public static void sendPacketToServerToTransform(PlayerEntity player, TitanType type) {
        if (player.world.isClient) { // Only send packet on the client side
            if (type.equals(TitanType.NONE)) {
                return;
            }
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeString(type.name());
            ClientPlayNetworking.send(PacketHandler.TRANSFORM_PACKET_ID, buf);
        }
    }

}
