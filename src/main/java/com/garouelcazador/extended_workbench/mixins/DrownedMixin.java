package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.config.EWServerConfig;
import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.items.custom.ExtendedTridentItem;
import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Drowned.class})
public final class DrownedMixin {
   private DrownedMixin() {
   }

   @ModifyArg(
      method = {"populateDefaultEquipmentSlots"},
      at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/entity/monster/Drowned;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V",
      ordinal = 0
      )
   )
   private ItemStack extendedWorkbench$populateDefaultEquipmentSlots(ItemStack itemStack, @Local(argsOnly = true) RandomSource random) {
      return (double)random.nextFloat() < extendedWorkbench$getExtendedTridentProbability() ? EWHelper.tryToConvertExtendedVersion(itemStack) : itemStack;
   }

   @ModifyReturnValue(
      method = {"canReplaceCurrentItem"},
      at = {@At(
      value = "RETURN",
      ordinal = 1
      )}
   )
   private boolean extendedWorkbench$canReplaceCurrentItem(boolean original, ItemStack candidate, ItemStack existing) {
      return original || candidate.is((Item)EWItems.EXTENDED_TRIDENT.get());
   }

   @ModifyReturnValue(
      method = {"canReplaceCurrentItem"},
      at = {@At(
      value = "RETURN",
      ordinal = 2
   )}
   )
   private boolean extendedWorkbench$canReplaceCurrentItem$1(boolean original, ItemStack candidate, ItemStack existing) {
      if (existing.is((Item)EWItems.EXTENDED_TRIDENT.get())) {
         return candidate.is((Item)EWItems.EXTENDED_TRIDENT.get()) && candidate.getDamageValue() < existing.getDamageValue();
      } else {
         return original || existing.is((Item)EWItems.EXTENDED_TRIDENT.get());
      }
   }

   @Unique
   private static double extendedWorkbench$getExtendedTridentProbability() {
      try {
         return (Double)EWServerConfig.EXTENDED_TRIDENT_PROBABILITY.get();
      } catch (Exception var1) {
         return (Double)EWServerConfig.EXTENDED_TRIDENT_PROBABILITY.getDefault();
      }
   }

   @Inject(method = "canReplaceCurrentItem", at = @At("HEAD"), cancellable = true)
   private void extendedWorkbench$checkReplacement(ItemStack candidate, ItemStack existing, CallbackInfoReturnable<Boolean> cir) {
      if (existing.getItem() instanceof ExtendedTridentItem && !(candidate.getItem() instanceof ExtendedTridentItem)) {
         cir.setReturnValue(false);
      }
   }

}
