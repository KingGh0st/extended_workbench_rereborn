package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.misc.EWHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Piglin.class)
public abstract class PiglinMixin {

   @Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
   private void extendedWorkbench$replacePiglinEquipment(RandomSource random, DifficultyInstance difficulty, CallbackInfo ci) {
      Piglin piglin = (Piglin) (Object) this;
      ItemStack mainHand = piglin.getItemBySlot(EquipmentSlot.MAINHAND);

      if (!mainHand.isEmpty() && (double) random.nextFloat() < EWHelper.getReplaceNativeEquipmentChance()) {
         ItemStack converted = EWHelper.tryToConvertExtendedVersion(mainHand);
         if (converted != mainHand) {
               piglin.setItemSlot(EquipmentSlot.MAINHAND, converted);
         }
      }
   }

   @Inject(method = "canReplaceCurrentItem", at = @At("HEAD"), cancellable = true)
   private void extendedWorkbench$allowPickingUpExtendedWeapons(ItemStack candidate, CallbackInfoReturnable<Boolean> cir) {
      if (candidate.is(EWItems.EXTENDED_CROSSBOW.get())) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "canFireProjectileWeapon", at = @At("HEAD"), cancellable = true)
   private void extendedWorkbench$supportExtendedCrossbow(net.minecraft.world.item.ProjectileWeaponItem item, CallbackInfoReturnable<Boolean> cir) {
      if (item instanceof com.garouelcazador.extended_workbench.items.custom.ExtendedCrossbowItem) {
         cir.setReturnValue(true);
      }
   }
}