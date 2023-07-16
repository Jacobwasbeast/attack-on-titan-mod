package com.jacobwasbeast.aot.entity;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

import static org.joml.Math.lerp;

public class RHookEntity extends ArrowEntity {
    private double length = 0;
    public double maxlength = 30;
    private BlockPos hookedOn = null;

    public RHookEntity(EntityType<RHookEntity> type, World world) {
        super(type, world);
    }

    public RHookEntity(World world, LivingEntity owner) {
        super(RegisterEntity.RIGHT_HOOK, world);
        setOwner(owner);
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if (state.isAir()) {
            return;
        }
        if (getOwner() == null) {
            return;
        }
        if (world == null) {
            return;
        }
        LivingEntity ownerEntity = world.getPlayerByUuid(getOwner().getUuid());

        if (ownerEntity.getPos().distanceTo(this.getPos()) > maxlength) {
            discardHook();
            return;
        }
        // Check if world or owner is null before proceeding
        if (world == null) {
            System.out.println("World is null");
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        if (getOwner() == null) {
            System.out.println("Owner is null");
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        if (world.getPlayerByUuid(getOwner().getUuid()) == null) {
            System.out.println("Player is null");
            this.remove(RemovalReason.DISCARDED);
            return;
        }
        if (getLength() < 0.5) {
            setHookedOn(getBlockPos());
            setLength(world.getPlayerByUuid(getOwner().getUuid()).getPos().distanceTo(this.getPos()));
            System.out.println("setLength");
        }
    }

    @Override
    public void tick() {

        if (world == null) {
            super.tick();
            System.out.println("World is null");
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        if (getOwner() == null) {
            super.tick();
            System.out.println("Owner is null");
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        LivingEntity ownerEntity = world.getPlayerByUuid(getOwner().getUuid());

        if (ownerEntity == null || !ownerEntity.world.equals(this.world) || getHookedOn() == null || PacketHandler.getODMGearFromTrinkets((PlayerEntity) ownerEntity).isEmpty()) {
            // if it has been alive for more than 5 ticks, remove it
            if (this.age > 30) {
                discardHook();
            }
            else {
                super.tick();
            }
            return;
        }
        else {
            super.tick();
        }
        if (ownerEntity.getPos().distanceTo(this.getPos()) > maxlength) {
            discardHook();
            return;
        }
        setPos(getHookedOn().getX(), getHookedOn().getY(), getHookedOn().getZ());
        while (getLength() > maxlength) {
            setLength(length--);
        }
        if (ownerEntity.getPos().distanceTo(this.getPos()) > getLength()) {
            System.out.println(length);
            Vec3d direction = this.getPos().subtract(ownerEntity.getPos()).normalize();
            // using Lerp to make the hook move smoothly
            if (ownerEntity.getPos().distanceTo(this.getPos()) > getLength()+10) {
                // pull the player towards the hook
                ownerEntity.addVelocity(direction.multiply(0.5, 0.5, 0.5));
            }
            else {
                // pull the player towards the hook using lerp to make it smooth like a hook
                ownerEntity.addVelocity(direction.multiply(0.5, 0.5, 0.5).multiply(lerp(0.1F, 0.5F, 0.5F), lerp(0.1F, 0.5F, 0.5F), lerp(0.1F, 0.5F, 0.5F)));

            }
        }
    }

    private void discardHook() {
        // if hook is not near player, apply velocity to hook to bring it back
        if (getOwner() != null) {
            LivingEntity ownerEntity = world.getPlayerByUuid(getOwner().getUuid());
            if (ownerEntity != null) {
                if (ownerEntity.getPos().distanceTo(this.getPos()) > 3) {
                    Vec3d direction = ownerEntity.getPos().subtract(this.getPos()).normalize();
                    this.addVelocity(direction.multiply(0.5, 0.5, 0.5));
                }
                else {
                    this.remove(RemovalReason.DISCARDED);
                }
            }
            else {
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.hookedOn = nbt.contains("hookedOn") ? BlockPos.fromLong(nbt.getLong("hookedOn")) : null;
        this.length = nbt.contains("length") ? nbt.getDouble("length") : 0;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (this.hookedOn != null) nbt.putLong("hookedOn", this.hookedOn.asLong());
        nbt.putDouble("length", this.length);
        return nbt;
    }

    private BlockPos getHookedOn() {
        return this.hookedOn;
    }

    private void setHookedOn(BlockPos pos) {
        this.hookedOn = pos;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return this.length;
    }
}
