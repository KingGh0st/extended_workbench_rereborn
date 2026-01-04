package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedShieldItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({LivingEntity.class})
public final class LivingEntityMixin {
   @Shadow
   protected ItemStack useItem;

   private LivingEntityMixin() {
   }

   @ModifyExpressionValue(
      method = {"isDamageSourceBlocked"},
      at = {@At(
   value = "CONSTANT",
   args = {"doubleValue=0.0"},
   ordinal = 1
)}
   )
   private double extendedWorkbench$isDamageSourceBlocked(double original) {
      return this.useItem.getItem() instanceof ExtendedShieldItem ? original + 0.16D : original;
   }
}
