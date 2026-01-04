package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedBowItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({ProjectileWeaponItem.class})
public final class ProjectileWeaponItemMixin {
   private ProjectileWeaponItemMixin() {
   }

   @ModifyArg(
      method = {"shoot"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;)V"
)
   )
   private int extendedWorkbench$shoot(int arg, @Local(argsOnly = true) ServerLevel level, @Local(argsOnly = true,ordinal = 0) float f) {
      return (ProjectileWeaponItem)(Object)this instanceof ExtendedBowItem && f >= 3.9F && level.random.nextInt(4) == 0 ? arg + 1 : arg;
   }
}
