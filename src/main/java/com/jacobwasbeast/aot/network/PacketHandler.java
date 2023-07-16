package com.jacobwasbeast.aot.network;

import com.jacobwasbeast.aot.entity.LHookEntity;
import com.jacobwasbeast.aot.entity.RHookEntity;
import com.jacobwasbeast.aot.gui.ODMInventoryScreenHandler;
import com.jacobwasbeast.aot.item.ODM.ODMGearItem;
import com.jacobwasbeast.aot.titan.TitanTransformHandler;
import com.jacobwasbeast.aot.titan.TitanType;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.List;
import java.util.UUID;

public class PacketHandler {
    public static final Identifier TRANSFORM_ANIMATION_PACKET_ID = new Identifier("aot", "transform_animation");
    public static final Identifier TRANSFORM_PACKET_ID = new Identifier("aot", "transform");

    public static final Identifier PURE_PACKET_ID = new Identifier("aot", "pure");
    public static final Identifier OPEN_ODM_INVENTORY_PACKET_ID = new Identifier("aot", "open_odm_inventory");
    public static final Identifier OPEN_ODM_INCREASE_LENGTH = new Identifier("aot", "open_odm_increase_length");
    public static final Identifier OPEN_ODM_DECREASE_LENGTH = new Identifier("aot", "open_odm_decrease_length");
    public static final Identifier ODM_SHOOT_RIGHT_HOOK = new Identifier("aot", "odm_shoot_right_hook");
    public static final Identifier ODM_SHOOT_LEFT_HOOK = new Identifier("aot", "odm_shoot_left_hook");

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(PURE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                TitanTransformHandler.setTransformed(player, TitanType.PURE);
                TitanTransformHandler.setPlayerTitanForm(player, TitanType.PURE);
                // Create packet for transformation
                tranform(player,TitanType.PURE,server);

            });


        });
        ServerPlayNetworking.registerGlobalReceiver(ODM_SHOOT_RIGHT_HOOK, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (getODMGearFromTrinkets(player).equals(ItemStack.EMPTY)) {
                    return;
                }

                if (player.getUuid() == null) {
                    System.out.println("Player UUID is null");
                    return;
                }

                ItemStack odmGear = getODMGearFromTrinkets(player);
                RHookEntity hook = new RHookEntity(player.world, player);
                hook.setOwner(player);
                // set the position of the hook to the player's position
                hook.setPos(player.getX(), player.getY(), player.getZ());
                // set the velocity and rotation of the hook based on the player's look vector and going in a straight line
                hook.setVelocity(player.getRotationVector().multiply(2, 2, 2));
                // spawn the hook in the world
                NbtCompound nbt = odmGear.getOrCreateNbt();

                if (nbt.contains("righthook")) {
                    player.world.getEntityById(nbt.getInt("righthook")).remove(Entity.RemovalReason.DISCARDED);
                    nbt.remove("righthook");
                }

                nbt.putInt("righthook", hook.getId());
                odmGear.setNbt(nbt);
                System.out.println("right hook id: " + hook.getId());
                player.world.spawnEntity(hook);
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(OPEN_ODM_INCREASE_LENGTH, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (getODMGearFromTrinkets(player).equals(ItemStack.EMPTY)) {
                    return;
                }

                if (player.getUuid() == null) {
                    System.out.println("Player UUID is null");
                    return;
                }

                ItemStack odmGear = getODMGearFromTrinkets(player);
                NbtCompound nbt = odmGear.getOrCreateNbt();
                if (nbt.contains("righthook")) {
                    Entity entity = player.world.getEntityById(nbt.getInt("righthook"));
                    if (entity instanceof RHookEntity) {
                        RHookEntity hook = (RHookEntity) entity;
                        if (hook.getLength() < hook.maxlength) {
                            hook.setLength(hook.getLength() + 0.1);
                        }
                    }
                }
                if (nbt.contains("lefthook")) {
                    Entity entity = player.world.getEntityById(nbt.getInt("lefthook"));
                    if (entity instanceof LHookEntity) {
                        LHookEntity hook = (LHookEntity) entity;
                        hook.setLength(hook.getLength() + 0.1);
                        System.out.println("right hook length: " + hook.getLength());
                    }
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(OPEN_ODM_DECREASE_LENGTH, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if (getODMGearFromTrinkets(player).equals(ItemStack.EMPTY)) {
                    return;
                }

                if (player.getUuid() == null) {
                    System.out.println("Player UUID is null");
                    return;
                }

                ItemStack odmGear = getODMGearFromTrinkets(player);
                NbtCompound nbt = odmGear.getOrCreateNbt();
                if (nbt.contains("righthook")) {
                    Entity entity = player.world.getEntityById(nbt.getInt("righthook"));
                    if (entity instanceof RHookEntity) {
                        RHookEntity hook = (RHookEntity) entity;
                        if (hook.getLength() > 1f) {
                            hook.setLength(hook.getLength() - 1f);
                            System.out.println("right hook length: " + hook.getLength());
                        }
                    }
                }
                if (nbt.contains("lefthook")) {
                    Entity entity = player.world.getEntityById(nbt.getInt("lefthook"));
                    if (entity instanceof LHookEntity) {
                        LHookEntity hook = (LHookEntity) entity;
                        hook.setLength(hook.getLength() - 0.1f);
                    }
                }
            });
        });
        ServerPlayNetworking.registerGlobalReceiver(TRANSFORM_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            // This will be executed on the server when it receives a transform packet

            // Read the data from the packet
            String typeName = buf.readString(32767); // max length is 32767
            TitanType type = TitanType.valueOf(typeName);

            // Schedule the transformation to be executed on the server thread
            server.execute(() -> {
                tranform(player,type,server);
            });


        });
        ServerPlayNetworking.registerGlobalReceiver(PacketHandler.OPEN_ODM_INVENTORY_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                ItemStack odmGear = getODMGearFromTrinkets(player);
                if (odmGear.getItem() instanceof ODMGearItem) {
                    ODMInventoryScreenHandler screenHandler = new ODMInventoryScreenHandler(0, player.getInventory(), odmGear);
                    player.openHandledScreen(screenHandler);
                }
            });
        });







    }

    private static void tranform(ServerPlayerEntity player, TitanType type, MinecraftServer server) {
        TitanTransformHandler.setTransformed(player, type);
        // Create packet for transformation animation
        PacketByteBuf animationBuf = new PacketByteBuf(Unpooled.buffer());
        animationBuf.writeUuid(player.getUuid());

        // Send the packet to all connected clients
        for (ServerPlayerEntity connectedPlayer : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(connectedPlayer, TRANSFORM_ANIMATION_PACKET_ID, animationBuf);
        }
        player.world.createExplosion(player, player.getX(), player.getY(), player.getZ(), 10, World.ExplosionSourceType.TNT);
        LightningEntity lightningEntity = new LightningEntity(EntityType.LIGHTNING_BOLT, player.world);
        lightningEntity.setPos(player.getX(), player.getY(), player.getZ());
        player.world.spawnEntity(lightningEntity);
    }

    public static ItemStack getODMGearFromTrinkets(PlayerEntity player) {
        // Get odm gear from trinkets 1.19.2
        ItemStack odmGear = ItemStack.EMPTY;
        TrinketComponent component = TrinketsApi.getTrinketComponent(player).get();
        if (component != null) {
            List<Pair<SlotReference, ItemStack>> trinkets = component.getEquipped(itemStack -> {
                if (itemStack.getItem() instanceof ODMGearItem) {
                    return true;
                }
                return false;
            });
            if (trinkets.size() > 0) {
                odmGear = trinkets.get(0).getRight();
            }
        }
        return odmGear;
    }


}
