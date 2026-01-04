package com.garouelcazador.extended_workbench.mixins.client;

import com.garouelcazador.extended_workbench.items.custom.ExtendedCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({AnimationUtils.class})
public final class AnimationUtilsMixin {
   private AnimationUtilsMixin() {
   }

   @WrapOperation(
      method = {"animateCrossbowCharge"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/item/CrossbowItem;getChargeDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"
)}
   )
   private static int extendedWorkbench$animateCrossbowCharge(ItemStack itemStack, LivingEntity entity, Operation<Integer> original) {
      Item item = itemStack.getItem();
      return item instanceof ExtendedCrossbowItem ? item.getUseDuration(itemStack, entity) : (Integer)original.call(new Object[]{itemStack, entity});
   }
}
