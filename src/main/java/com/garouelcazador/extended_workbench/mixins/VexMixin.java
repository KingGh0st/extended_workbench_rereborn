package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({Vex.class})
public final class VexMixin {
   private VexMixin() {
   }

   @ModifyArg(
      method = {"populateDefaultEquipmentSlots"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/monster/Vex;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"
),
      index = 1
   )
   private ItemStack extendedWorkbench$populateDefaultEquipmentSlots(ItemStack itemStack, @Local(argsOnly = true) RandomSource random) {
      return (double)random.nextFloat() < EWHelper.getReplaceNativeEquipmentChance() ? EWHelper.tryToConvertExtendedVersion(itemStack) : itemStack;
   }
}
