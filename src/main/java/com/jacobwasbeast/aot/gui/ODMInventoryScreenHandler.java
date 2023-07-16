package com.jacobwasbeast.aot.gui;

import com.jacobwasbeast.aot.register.RegisterGUI;
import com.jacobwasbeast.aot.gui.slots.GasSlot;
import com.jacobwasbeast.aot.gui.slots.SwordSlot;
import com.jacobwasbeast.aot.gui.slots.ThunderSpearSlot;
import com.jacobwasbeast.aot.item.ODM.ODMGearItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.List;

import static com.jacobwasbeast.aot.network.PacketHandler.getODMGearFromTrinkets;

public class ODMInventoryScreenHandler extends ScreenHandler implements ExtendedScreenHandlerFactory {
    private final ItemStack odmGear;
    private final int syncId;
    private final PlayerInventory playerInventory;
    private final SimpleInventory odmInventorytoSave;
    private final Text title;

    public ODMInventoryScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack odmGear) {
        super(RegisterGUI.ODM_INVENTORY_SCREEN_HANDLER, syncId);
        this.syncId = syncId;
        this.playerInventory = playerInventory;
        this.odmGear = odmGear;
        this.title = Text.literal("ODM Gear");
        List<ItemStack> odmInventory = ODMGearItem.getInventory(odmGear);
        odmInventorytoSave = new SimpleInventory(odmInventory.toArray(new ItemStack[0]));
        // add one slot on the left for swords and one on the right for swords
        // add one slot on the left for thunder spear and one on the right for thunder spear
        // add one slot in the left for gas and one on the right for gas
        this.addSlot(new SwordSlot(odmInventorytoSave, 0, 8 , 14));
        this.addSlot(new SwordSlot(odmInventorytoSave,  1, 8 + 9 * 18, 14));
        this.addSlot(new ThunderSpearSlot(odmInventorytoSave, 2, 8, 34));
        this.addSlot(new ThunderSpearSlot(odmInventorytoSave, 3, 8 + 9 * 18, 34));
        this.addSlot(new GasSlot(odmInventorytoSave, 4, 8, 54));
        this.addSlot(new GasSlot(odmInventorytoSave, 5, 8 + 9 * 18, 54));

        // add slots for the player inventory
        int m;
        int l;
        for (l = 0; l < 3; ++l) {
            for (m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + l * 9 + 9, 8 + m * 18, 84 + l * 18));
            }
        }
        for (l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            List<ItemStack> odmInventory = ODMGearItem.getInventory(odmGear);
            if (index < odmInventory.size()) {
                if (!this.insertItem(originalStack, odmInventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, odmInventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        ODMGearItem.setInventory(getODMGearFromTrinkets(player), odmInventorytoSave.stacks);
        for (int i = 0; i < odmInventorytoSave.size(); i++) {
            System.out.println(odmInventorytoSave.stacks.get(i));
        }
    }

    @Override
    public Text getDisplayName() {
        return title;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ODMInventoryScreenHandler(syncId, playerInventory, getODMGearFromTrinkets(player));
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        List<ItemStack> odmInventory = ODMGearItem.getInventory(getODMGearFromTrinkets(player));
        buf.writeVarInt(odmInventory.size());
        for (ItemStack stack : odmInventory) {
            buf.writeItemStack(stack);
        }
    }
}
