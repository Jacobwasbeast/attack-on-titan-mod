package com.jacobwasbeast.aot.item.Armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemGroup;

public class TraineeUniformChestItem extends ArmorItem {
    public TraineeUniformChestItem(Settings settings) {
        super(ArmorMaterials.LEATHER, Type.CHESTPLATE, settings);  // replace this with your mod-specific item group
    }
}
