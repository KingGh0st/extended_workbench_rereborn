package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedCrossbowItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CrossbowItem.class})
public final class CrossbowItemMixin {
   private CrossbowItemMixin() {
   }

   @Inject(
      method = {"releaseUsing"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/item/CrossbowItem;getChargingSounds(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/CrossbowItem$ChargingSounds;"
)}
   )
   private void extendedWorkbench$releaseUsing(ItemStack crossbowStack, Level level, LivingEntity entity, int p_40878_, CallbackInfo ci, @Local float f) {
      if (f >= 1.5F) {
         ExtendedCrossbowItem.setExtendedCharge(crossbowStack, true);
      }

   }

   @ModifyExpressionValue(
      method = {"getPowerForTime"},
      at = {@At(
   value = "CONSTANT",
   args = {"floatValue=1.0F"}
)}
   )
   private static float extendedWorkbench$getPowerForTime(float original, @Local(argsOnly = true) ItemStack crossbowStack) {
      return crossbowStack.getItem() instanceof ExtendedCrossbowItem ? 1.5F : original;
   }
}
