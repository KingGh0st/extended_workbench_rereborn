package com.garouelcazador.extended_workbench.mixins.client;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DrownedModel.class)
public abstract class DrownedModelMixin {

   @ModifyExpressionValue(
      method = "prepareMobModel(Lnet/minecraft/world/entity/monster/Zombie;FFF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z" 
      )
   )
   private boolean extendedWorkbench$prepareMobModel(boolean original, Zombie entity, float limbSwing, float limbSwingAmount, float partialTick) {
      if (entity == null) return original;
      ItemStack itemStack = entity.getMainHandItem();
      return original || itemStack.is(EWItems.EXTENDED_TRIDENT.get());
   }
}