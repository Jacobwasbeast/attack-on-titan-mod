package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.Main;
import com.jacobwasbeast.aot.entity.LHookEntity;
import com.jacobwasbeast.aot.entity.RHookEntity;
import com.jacobwasbeast.aot.entity.ThunderSpearProjectileEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RegisterRenderer {

    public static void register() {
        EntityRendererRegistry.register(RegisterEntity.RIGHT_HOOK, (context) ->
                new ArrowEntityRenderer(context));
        EntityRendererRegistry.register(RegisterEntity.LEFT_HOOK, (context) ->
                new FlyingItemEntityRenderer(context));
        EntityRendererRegistry.register(RegisterEntity.THUNDER_SPEAR_PROJECTILE_ENTITY_ENTITY_TYPE, (context) ->
                new ArrowEntityRenderer(context));
    }
}
