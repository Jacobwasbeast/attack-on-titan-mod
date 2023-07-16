package com.jacobwasbeast.aot.item.ODM;

import com.jacobwasbeast.aot.item.BaseItemTrinket;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.ArrayList;
import java.util.List;

public class ODMGearItem extends BaseItemTrinket implements Trinket {
    public static final int INVENTORY_SIZE = 10; // 8 slots for swords, 2 for thunder spears
    public ODMGearItem(Item.Settings settings) {
        super(settings);
    }
    public static List<ItemStack> getInventory(ItemStack stack) {
        List<ItemStack> inventory = new ArrayList<>();
        NbtCompound tag = stack.getOrCreateNbt();
        NbtList tagList = tag.getList("ODMInventory", NbtElement.COMPOUND_TYPE);
        if (tagList.size() == 0) {
            for (int i = 0; i < INVENTORY_SIZE; i++) {
                inventory.add(ItemStack.EMPTY);
            }
            return inventory;
        }
        for (int i = 0; i < tagList.size(); i++) {
            System.out.println(tagList.getCompound(i));
            inventory.add(ItemStack.fromNbt(tagList.getCompound(i)));
        }
        return inventory;
    }



    public static void setInventory(ItemStack stack, List<ItemStack> inventory) {
        NbtCompound tag = stack.getOrCreateNbt();
        NbtList tagList = new NbtList();
        for (ItemStack itemStack : inventory) {
            tagList.add(itemStack.writeNbt(new NbtCompound()));
        }
        tag.put("ODMInventory", tagList);
        stack.setNbt(tag);
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt.contains("righthook")) {
            if (entity.world.getEntityById(nbt.getInt("righthook")) == null) {
                nbt.remove("righthook");
                stack.setNbt(nbt);
            }
        }
        if (nbt.contains("lefthook")) {
            if (entity.world.getEntityById(nbt.getInt("lefthook")) == null) {
                nbt.remove("lefthook");
                stack.setNbt(nbt);
            }
        }

    }
}
