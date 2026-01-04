package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.items.custom.ExtendedBowItem;
import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Definitions;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({AbstractSkeleton.class})
public final class AbstractSkeletonMixin {
   private AbstractSkeletonMixin() {
   }

   @ModifyArg(
      method = "populateDefaultEquipmentSlots",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/AbstractSkeleton;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"),
      index = 1
   )
   private ItemStack extendedWorkbench$populateDefaultEquipmentSlots(ItemStack itemStack, @Local(argsOnly = true) RandomSource random) {
      return (double)random.nextFloat() < EWHelper.getReplaceNativeEquipmentChance() ? EWHelper.tryToConvertExtendedVersion(itemStack) : itemStack;
   }


   @Inject(method = "performRangedAttack", at = @At("HEAD"))
   private void extendedWorkbench$performRangedAttack(LivingEntity target, float velocity, CallbackInfo ci, @Share("flag") LocalBooleanRef booleanRef) {
      AbstractSkeleton host = (AbstractSkeleton)(Object)this;
      booleanRef.set(host.getMainHandItem().getItem() instanceof ExtendedBowItem);
   }

   @ModifyExpressionValue(
      method = "performRangedAttack",
      at = @At("MIXINEXTRAS:EXPRESSION")
   )
   @Expression("d1 + d3 * @(?)")
   @Definitions({
      @Definition(id = "d1", local = @Local(type = double.class, ordinal = 1)),
      @Definition(id = "d3", local = @Local(type = double.class, ordinal = 3))
   })
   private double extendedWorkbench$modifyRangedAttackDamage(double value, @Share("flag") LocalBooleanRef booleanRef) {
      return booleanRef.get() ? value / 2.0D : value;
   }

   @ModifyArg(
      method = "performRangedAttack",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;shoot(DDDFF)V"),
      index = 3
   )
   private float extendedWorkbench$modifyArrowPower(float power, @Share("flag") LocalBooleanRef booleanRef) {
      return booleanRef.get() ? power * 1.5F : power;
   }

   @ModifyReturnValue(method = "canFireProjectileWeapon", at = @At("RETURN"))
   private boolean extendedWorkbench$canFireProjectileWeapon(boolean original, ProjectileWeaponItem weaponItem) {
      return original || weaponItem == EWItems.EXTENDED_BOW.get();
   }

   @ModifyArg(
      method = "reassessWeaponGoal",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/RangedBowAttackGoal;setMinAttackInterval(I)V")
   )
   private int extendedWorkbench$modifyMinAttackInterval(int arg) {
      AbstractSkeleton host = (AbstractSkeleton)(Object)this;
      return host.getMainHandItem().getItem() instanceof ExtendedBowItem ? (int)((float)arg * 1.5F) : arg;
   }


}
