package com.garouelcazador.extended_workbench.mixins.client;

import com.garouelcazador.extended_workbench.client.renderer.EWItemRenderer;
import com.garouelcazador.extended_workbench.items.EWItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

   @Shadow
   @Final
   private ItemModelShaper itemModelShaper;

   @ModifyExpressionValue(
         method = "render",
         at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
         )
   )
   private boolean extendedWorkbench$supportExtendedTrident(boolean original, ItemStack itemStack) {
      return original || itemStack.is(EWItems.EXTENDED_TRIDENT.get());
   }

   @WrapOperation(
         method = "getModel",
         at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;"
         )
   )
   private BakedModel extendedWorkbench$getExtendedTridentModel(ItemModelShaper instance, ItemStack itemStack, Operation<BakedModel> original) {
      if (itemStack.is(EWItems.EXTENDED_TRIDENT.get())) {
         return this.itemModelShaper.getModelManager().getModel(EWItemRenderer.EXTENDED_TRIDENT_IN_HAND_MODEL);
      }
      return original.call(instance, itemStack);
   }

   @WrapOperation(
         method = "render",
         at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderModelLists(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/item/ItemStack;IILcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;)V")
   )
   private void extendedWorkbench$swapTridentModel(ItemRenderer instance, BakedModel model, ItemStack stack, int combinedLight, int combinedOverlay, com.mojang.blaze3d.vertex.PoseStack poseStack, com.mojang.blaze3d.vertex.VertexConsumer buffer, Operation<Void> original) {
      if (stack.is(EWItems.EXTENDED_TRIDENT.get())) {
         BakedModel customModel = this.itemModelShaper.getModelManager().getModel(EWItemRenderer.EXTENDED_TRIDENT_MODEL);
         original.call(instance, customModel, stack, combinedLight, combinedOverlay, poseStack, buffer);
      } else {
         original.call(instance, model, stack, combinedLight, combinedOverlay, poseStack, buffer);
      }
   }
}