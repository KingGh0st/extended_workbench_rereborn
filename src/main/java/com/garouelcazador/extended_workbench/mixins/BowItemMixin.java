package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedBowItem;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public abstract class BowItemMixin {

   @WrapOperation(
      method = "releaseUsing",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BowItem;getPowerForTime(I)F")
   )
   private float extendedWorkbench$releaseUsingPower(int ticks, Operation<Float> original, @Local(argsOnly = true) ItemStack bowStack) {
      if (bowStack.getItem() instanceof ExtendedBowItem) {
         return ExtendedBowItem.getPowerForTime(ticks);
      }
      return original.call(ticks);
   }

   @WrapOperation(
      method = "releaseUsing",
      at = @At("MIXINEXTRAS:EXPRESSION")
   )
   @Expression("f == 1.0")
   @Definition(id = "f", local = @Local(type = float.class))
   private boolean extendedWorkbench$releaseUsingCondition(float left, float right, Operation<Boolean> original, @Local(argsOnly = true) ItemStack bowStack) {
      boolean isFullPower = original.call(left, right);
      return isFullPower || (left > right && bowStack.getItem() instanceof ExtendedBowItem);
   }
}
