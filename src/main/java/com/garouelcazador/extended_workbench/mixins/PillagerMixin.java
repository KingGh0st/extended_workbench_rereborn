package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({Pillager.class})
public final class PillagerMixin {
   private PillagerMixin() {
   }

   @ModifyArg(
      method = {"populateDefaultEquipmentSlots"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/monster/Pillager;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"
),
      index = 1
   )
   private ItemStack extendedWorkbench$populateDefaultEquipmentSlots(ItemStack itemStack, @Local(argsOnly = true) RandomSource random) {
      return (double)random.nextFloat() < EWHelper.getReplaceNativeEquipmentChance() ? EWHelper.tryToConvertExtendedVersion(itemStack) : itemStack;
   }

   @ModifyReturnValue(
      method = {"canFireProjectileWeapon"},
      at = {@At("RETURN")}
   )
   private boolean extendedWorkbench$canFireProjectileWeapon(boolean original, ProjectileWeaponItem weaponItem) {
      return original || weaponItem == EWItems.EXTENDED_CROSSBOW.get();
   }
}
