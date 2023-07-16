package com.jacobwasbeast.aot.gui.slots;

import com.jacobwasbeast.aot.item.ODM.ODMThunderSpearsItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class ThunderSpearSlot extends Slot {
    public ThunderSpearSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        if (stack.getItem() instanceof ODMThunderSpearsItem) {
            return true;
        }
        return false;
    }
}
