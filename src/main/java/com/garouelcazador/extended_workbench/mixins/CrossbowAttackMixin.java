package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedCrossbowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.CrossbowAttack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({CrossbowAttack.class})
public final class CrossbowAttackMixin {
   private CrossbowAttackMixin() {
   }

   @WrapOperation(
      method = {"crossbowAttack"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/item/CrossbowItem;getChargeDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"
)}
   )
   private int extendedWorkbench$crossbowAttack(ItemStack itemStack, LivingEntity entity, Operation<Integer> original) {
      Item item = itemStack.getItem();
      return item instanceof ExtendedCrossbowItem ? item.getUseDuration(itemStack, entity) : (Integer)original.call(new Object[]{itemStack, entity});
   }
}
