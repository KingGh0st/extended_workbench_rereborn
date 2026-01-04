package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({Zombie.class})
public final class ZombieMixin {
   private ZombieMixin() {
   }

   @ModifyArg(
      method = {"populateDefaultEquipmentSlots"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/monster/Zombie;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"
),
      index = 1
   )
   private ItemStack extendedWorkbench$populateDefaultEquipmentSlots(ItemStack itemStack, @Local(argsOnly = true) RandomSource random) {
      return (double)random.nextFloat() < EWHelper.getReplaceEquipmentChance() ? EWHelper.tryToConvertExtendedVersion(itemStack) : itemStack;
   }
}
