package com.jacobwasbeast.aot.item.ODM;

import com.jacobwasbeast.aot.entity.ThunderSpearProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ODMThunderSpearsItem extends Item {
    public ODMThunderSpearsItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {  // Ensures this runs on the server only
            ThunderSpearProjectileEntity thunderSpearProjectile = new ThunderSpearProjectileEntity(world, user);
            thunderSpearProjectile.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(thunderSpearProjectile);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
