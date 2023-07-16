package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.gui.ODMInventoryScreen;
import com.jacobwasbeast.aot.gui.ODMInventoryScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.jacobwasbeast.aot.Main.MOD_ID;
import static com.jacobwasbeast.aot.network.PacketHandler.getODMGearFromTrinkets;

public class RegisterGUI {
    public static ScreenHandlerType<ODMInventoryScreenHandler> ODM_INVENTORY_SCREEN_HANDLER;


    public static void register() {
        ODM_INVENTORY_SCREEN_HANDLER = ScreenHandlerRegistry.registerExtended(new Identifier(MOD_ID, "odm_inventory"), (syncId, inventory, buf) -> new ODMInventoryScreenHandler(syncId, inventory, getODMGearFromTrinkets(inventory.player)));

    }

    public static void registerClient() {
        ScreenRegistry.register(RegisterGUI.ODM_INVENTORY_SCREEN_HANDLER, ODMInventoryScreen::new);
    }

}
