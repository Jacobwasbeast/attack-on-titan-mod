package com.jacobwasbeast.aot.events;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterKeyBinding;
import com.jacobwasbeast.aot.titan.TitanTransformHandler;
import com.jacobwasbeast.aot.titan.TitanType;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public class PlayerEventHandler {

    public static void register() {
        // Example of an event callback when a player attacks an entity
        AttackEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            if (TitanTransformHandler.getPlayerTitanType(player).equals(TitanType.NONE)|| (PacketHandler.getODMGearFromTrinkets(player).equals(ItemStack.EMPTY)&& !RegisterKeyBinding.odmMode)) {
                // handle logic if player is in Titan form and attacks an entity
                return ActionResult.SUCCESS;
            } else {
                return ActionResult.PASS;
            }
        }));

        // Example of an event callback when a player uses an item
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (!TitanTransformHandler.getPlayerTitanType(player).equals(TitanType.NONE)|| (!PacketHandler.getODMGearFromTrinkets(player).equals(ItemStack.EMPTY)&& RegisterKeyBinding.odmMode)) {
                // handle logic if player is in Titan form and tries to use an item
                return TypedActionResult.fail(player.getStackInHand(hand));
            } else {
                return TypedActionResult.pass(player.getStackInHand(hand));
            }
        });

        // Add more event callbacks as needed
    }
}
