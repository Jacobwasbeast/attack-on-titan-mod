package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.Main;
import com.jacobwasbeast.aot.entity.LHookEntity;
import com.jacobwasbeast.aot.entity.RHookEntity;
import com.jacobwasbeast.aot.entity.ThunderSpearProjectileEntity;
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
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegisterEntity {
    // All your item instances go here...
    public static final EntityType<ThunderSpearProjectileEntity> THUNDER_SPEAR_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "thunder_spear_projectile_entity"),
            FabricEntityTypeBuilder.<ThunderSpearProjectileEntity>create(SpawnGroup.MISC, ThunderSpearProjectileEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );
    public static final EntityType<LHookEntity> LEFT_HOOK = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "odm_left_hook"),
            FabricEntityTypeBuilder.<LHookEntity>create(SpawnGroup.MISC, LHookEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );
    public static final EntityType<RHookEntity> RIGHT_HOOK = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(Main.MOD_ID, "odm_right_hook"),
            FabricEntityTypeBuilder.<RHookEntity>create(SpawnGroup.MISC, RHookEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
    );
    public static void register() {

    }
}
