package com.garouelcazador.extended_workbench.mixins;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Definitions;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({ServerEntity.class})
public final class ServerEntityMixin {
   @Shadow
   @Final
   private Entity entity;

   private ServerEntityMixin() {
   }

   @WrapOperation(
      method = {"sendChanges"},
      at = {@At("MIXINEXTRAS:EXPRESSION")}
   )
   @Definitions({@Definition(
   id = "tickCount",
   field = {"Lnet/minecraft/server/level/ServerEntity;tickCount:I"}
), @Definition(
   id = "updateInterval",
   field = {"Lnet/minecraft/server/level/ServerEntity;updateInterval:I"}
)})
   @Expression({"this.tickCount % this.updateInterval == 0"})
   private boolean extendedWorkbench$sendChanges(int left, int right, Operation<Boolean> original) {
      boolean var10000;
      if (!(Boolean)original.call(new Object[]{left, right})) {
         label26: {
            Entity var5 = this.entity;
            if (var5 instanceof AbstractArrow) {
               AbstractArrow arrow = (AbstractArrow)var5;
               if (arrow.getDeltaMovement().lengthSqr() > 16.0D) {
                  break label26;
               }
            }

            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }
}
