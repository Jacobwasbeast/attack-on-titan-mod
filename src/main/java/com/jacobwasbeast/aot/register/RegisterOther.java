package com.jacobwasbeast.aot.register;

import com.jacobwasbeast.aot.Main;
import com.jacobwasbeast.aot.item.ODM.ODMGearItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
//import dev.felnull.specialmodelloader.api.SpecialModelLoaderAPI;
//import dev.felnull.specialmodelloader.api.event.SpecialModelLoaderEvents;
import dev.felnull.specialmodelloader.api.SpecialModelLoaderAPI;
import dev.felnull.specialmodelloader.api.data.SpecialModelDataGenHelper;
import dev.felnull.specialmodelloader.api.event.SpecialModelLoaderEvents;
import dev.felnull.specialmodelloader.api.model.ModelOption;
import dev.felnull.specialmodelloader.api.model.obj.ObjModelOption;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Vector;

public class RegisterOther {
    public static void register() {
        SpecialModelLoaderEvents.LOAD_SCOPE.register(location -> Main.MOD_ID.equals(location.getNamespace()));
        TrinketRendererRegistry.registerRenderer(RegisterItems.ODM_GEAR_ITEM, new TrinketRenderer() {
            @Override
            public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
                if (stack.getItem() instanceof ODMGearItem) {
                    matrices.push();
                    // render it at the legs
                    // use hips as the pivot point
                    matrices.translate(-0.05D, 1D, -0.3D);
                    matrices.multiply(new Quaternionf(new AxisAngle4d(3,1,0d,0)));
                    matrices.multiply(new Quaternionf(new AxisAngle4d(3,0,1d,0)));
                    if (entity.isSneaking()) {
                        matrices.translate(0, 0.2, 0);
                        matrices.multiply(new Quaternionf(new AxisAngle4d(0.5,1,0d,0)));
                    }
                    BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModel(stack, entity.world, entity, 0);


                    MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, model);
                    matrices.pop();
                }
            }


        });
    }
}
