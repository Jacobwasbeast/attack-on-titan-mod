package com.jacobwasbeast.aot.gui.slots;

import com.jacobwasbeast.aot.item.ODM.GassItem;
import com.jacobwasbeast.aot.item.ODM.ODMSwordsItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class GasSlot extends Slot {
    public GasSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.getItem() instanceof GassItem) {
            return true;
        }
        return false;
    }
}
