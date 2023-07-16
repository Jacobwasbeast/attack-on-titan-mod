package com.jacobwasbeast.aot.entity;

import com.jacobwasbeast.aot.register.RegisterEntity;
import com.jacobwasbeast.aot.register.RegisterItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThunderSpearProjectileEntity extends ArrowEntity {
    LivingEntity owner;
    public ThunderSpearProjectileEntity(EntityType<ThunderSpearProjectileEntity> entityEntityType, World world) {
        super(entityEntityType, world);
    }

    public ThunderSpearProjectileEntity(World world, LivingEntity owner) {
        super(RegisterEntity.THUNDER_SPEAR_PROJECTILE_ENTITY_ENTITY_TYPE, world);
        this.owner = owner;
    }
    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.world.isClient) {  // Ensures this runs on the server only
            this.world.createExplosion(this, this.getX(), this.getBodyY(0.0625D), this.getZ(), 4.0F, World.ExplosionSourceType.TNT);
            this.remove(RemovalReason.DISCARDED);  // Remove the entity from the world after explosion
        }
    }

}
