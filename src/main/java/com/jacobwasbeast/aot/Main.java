package com.jacobwasbeast.aot;

import com.jacobwasbeast.aot.events.PlayerEventHandler;
import com.jacobwasbeast.aot.network.PacketHandler;
import com.jacobwasbeast.aot.register.RegisterEntity;
import com.jacobwasbeast.aot.register.RegisterGUI;
import com.jacobwasbeast.aot.register.RegisterItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
    public static final String MOD_ID = "aot";

    public static final ItemGroup ITEM_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, "aot_mod_group")).icon(() -> new ItemStack(RegisterItems.ODM_GEAR_ITEM)).build();

    @Override
    public void onInitialize() {
        RegisterItems.register();
        RegisterGUI.register();
        PacketHandler.registerServerReceivers();
        PlayerEventHandler.register();
        RegisterEntity.register();
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries ->
                {
                    entries.add(RegisterItems.ODM_GEAR_ITEM);
                    entries.add(RegisterItems.ODM_SWORDS_ITEM);
                    entries.add(RegisterItems.ODM_GASS_ITEM);
                    entries.add(RegisterItems.ODM_THUNDER_SPEAR_ITEM);
                    entries.add(RegisterItems.SyringeItem);
                    entries.add(RegisterItems.TitanInjection);
                    entries.add(RegisterItems.TRAINEE_UNIFORM_CHEST_ITEM);
                    entries.add(RegisterItems.TRAINEE_UNIFORM_LEGS_ITEM);
                    entries.add(RegisterItems.TRAINEE_UNIFORM_BOOTS_ITEM);
                    entries.add(RegisterItems.GARRISON_CAPE_ITEM);
                    entries.add(RegisterItems.MILITARY_POLICE_CAPE_ITEM);
                    entries.add(RegisterItems.SURVEY_CORPS_CAPE_ITEM);
                    entries.add(RegisterItems.SPIKED_RING_ITEM);
                }

                );
    }


}
