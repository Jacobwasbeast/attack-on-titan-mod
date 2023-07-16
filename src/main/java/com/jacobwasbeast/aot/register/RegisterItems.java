package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.Main;
import com.jacobwasbeast.aot.item.Armor.TraineeUniformBootsItem;
import com.jacobwasbeast.aot.item.Armor.TraineeUniformChestItem;
import com.jacobwasbeast.aot.item.Armor.TraineeUniformLegsItem;
import com.jacobwasbeast.aot.item.Capes.GarrisonCapeItem;
import com.jacobwasbeast.aot.item.Capes.MilitaryPoliceCapeItem;
import com.jacobwasbeast.aot.item.Capes.SurveyCorpsCapeItem;
import com.jacobwasbeast.aot.item.ODM.GassItem;
import com.jacobwasbeast.aot.item.ODM.ODMGearItem;
import com.jacobwasbeast.aot.item.ODM.ODMSwordsItem;
import com.jacobwasbeast.aot.item.ODM.ODMThunderSpearsItem;
import com.jacobwasbeast.aot.item.Titan.SpikedRingItem;
import com.jacobwasbeast.aot.item.Titan.SyringeItem;
import com.jacobwasbeast.aot.item.Titan.TitanInjectionItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.FoodComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
public class RegisterItems {
    // All your item instances go here...
    public static final ODMGearItem ODM_GEAR_ITEM = new ODMGearItem(new FabricItemSettings().maxCount(1));
    public static final ODMSwordsItem ODM_SWORDS_ITEM = new ODMSwordsItem(new FabricItemSettings().maxCount(4));
    public static final GassItem ODM_GASS_ITEM = new GassItem(new FabricItemSettings().maxCount(1).maxDamage(1000));
    public static final ODMThunderSpearsItem ODM_THUNDER_SPEAR_ITEM = new ODMThunderSpearsItem(new FabricItemSettings().maxCount(1));

    public static final SyringeItem SyringeItem = new SyringeItem(new FabricItemSettings().maxCount(1));

    public static final TitanInjectionItem TitanInjection = new TitanInjectionItem(new FabricItemSettings().maxCount(1).food(new FoodComponent.Builder().hunger(0).saturationModifier(0).alwaysEdible().build()));
    public static final TraineeUniformChestItem TRAINEE_UNIFORM_CHEST_ITEM = new TraineeUniformChestItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final TraineeUniformLegsItem TRAINEE_UNIFORM_LEGS_ITEM = new TraineeUniformLegsItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final TraineeUniformBootsItem TRAINEE_UNIFORM_BOOTS_ITEM = new TraineeUniformBootsItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final GarrisonCapeItem GARRISON_CAPE_ITEM = new GarrisonCapeItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final MilitaryPoliceCapeItem MILITARY_POLICE_CAPE_ITEM = new MilitaryPoliceCapeItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final SurveyCorpsCapeItem SURVEY_CORPS_CAPE_ITEM = new SurveyCorpsCapeItem(new FabricItemSettings().maxCount(1).maxDamage(100));
    public static final SpikedRingItem SPIKED_RING_ITEM = new SpikedRingItem(new FabricItemSettings().maxCount(1).maxDamage(100));

    public static void register() {
        // Register your items
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "odm_gear"), ODM_GEAR_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "odm_swords"), ODM_SWORDS_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "odm_gass"), ODM_GASS_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "odm_thunder_spears"), ODM_THUNDER_SPEAR_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "syringe"), SyringeItem);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "titan_injection"), TitanInjection);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "trainee_uniform_chest"), TRAINEE_UNIFORM_CHEST_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "trainee_uniform_legs"), TRAINEE_UNIFORM_LEGS_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "trainee_uniform_boots"), TRAINEE_UNIFORM_BOOTS_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "garrison_cape"), GARRISON_CAPE_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "military_police_cape"), MILITARY_POLICE_CAPE_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "survey_corps_cape"), SURVEY_CORPS_CAPE_ITEM);
        Registry.register(Registries.ITEM, new Identifier(Main.MOD_ID, "spiked_ring"), SPIKED_RING_ITEM);
    }
}
