package com.jacobwasbeast.aot.entity;

import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class LHookEntity extends ThrownItemEntity {
    private static double maxlength = 30;

    private static double maxDrawlength = 50;
    public LHookEntity(EntityType<LHookEntity> entityEntityType, World world) {
        super((EntityType<? extends ThrownItemEntity>) entityEntityType, world);
    }

    public LHookEntity(World world, UUID owner) {
        super((EntityType<? extends ThrownItemEntity>) RegisterEntity.LEFT_HOOK, world);
        setOwner(owner);
    }


    @Override
    protected Item getDefaultItem() {
        return null;
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        // set hookedOn to the block
        setHookedOn(getBlockPos());
        if (getLength()==0) {
            setLength(world.getPlayerByUuid(getOwnerTag()).getPos().distanceTo(this.getPos()));
        }
        System.out.println("Hooked on block");
    }

    @Override
    public void tick() {
        super.tick();
        UUID owner = getOwnerTag();
        BlockPos hookedOn = getHookedOn();

        if (owner==null) {
            this.remove(RemovalReason.DISCARDED);
            System.out.println("Owner is null");
        }
        if (world.getPlayerByUuid(owner)==null) {
            this.remove(RemovalReason.DISCARDED);
            System.out.println("Player is null");
        }
        else {
            if (world.getPlayerByUuid(owner).world!=this.world) {
                this.remove(RemovalReason.DISCARDED);
            }
            if (world.getPlayerByUuid(owner).getPos().distanceTo(this.getPos())>100) {
                this.remove(RemovalReason.DISCARDED);
            }
            if (PacketHandler.getODMGearFromTrinkets(world.getPlayerByUuid(owner))== ItemStack.EMPTY) {
                this.remove(RemovalReason.DISCARDED);
            }
            if (getHookedOn() != null) {
                // stay in place
                setPos(hookedOn.getX(), hookedOn.getY(), hookedOn.getZ());
                // pull player towards block
                if (world.getPlayerByUuid(owner).getPos().distanceTo(this.getPos())>getLength()) {
                    world.getPlayerByUuid(owner).setVelocity(this.getVelocity());
                }
            }
            else {

            }
        }

    }

    public void setHookedOn(BlockPos pos) {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        nbt.putLong("hookedOn", pos.asLong());
        this.readNbt(nbt);
    }
    public BlockPos getHookedOn() {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        return BlockPos.fromLong(nbt.getLong("hookedOn"));
    }
    public void setOwner(UUID uuid) {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        nbt.putUuid("owner", uuid);
        this.readNbt(nbt);
    }
    public UUID getOwnerTag() {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        return nbt.getUuid("owner");
    }
    public void setLength(double length) {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        nbt.putDouble("length", length);
        this.readNbt(nbt);
    }
    public double getLength() {
        NbtCompound nbt = this.writeNbt(new NbtCompound());
        if (!nbt.contains("length")) {
            return 0;
        }
        return nbt.getDouble("length");
    }
}
