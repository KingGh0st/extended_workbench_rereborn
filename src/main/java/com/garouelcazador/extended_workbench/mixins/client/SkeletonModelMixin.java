package com.garouelcazador.extended_workbench.mixins.client;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkeletonModel.class)
public abstract class SkeletonModelMixin {

   @ModifyExpressionValue(
         method = "prepareMobModel",
         at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
         )
   )
   private boolean extendedWorkbench$prepareMobModel(boolean original, Mob entity, float limbSwing, float limbSwingAmount, float partialTick) {
      if (entity == null) return original;
      ItemStack itemStack = entity.getMainHandItem();
      return original || itemStack.is(EWItems.EXTENDED_BOW.get());
   }

   @ModifyExpressionValue(
         method = "setupAnim",
         at = @At(
                  value = "INVOKE",
                  target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"
         )
   )
   private boolean extendedWorkbench$setupAnim(boolean original, Mob entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entity == null) return original;
      ItemStack itemStack = entity.getMainHandItem();
      return original || itemStack.is(EWItems.EXTENDED_BOW.get());
   }
}