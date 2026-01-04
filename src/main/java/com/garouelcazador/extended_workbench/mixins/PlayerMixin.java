package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.items.custom.ExtendedShieldItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Player.class})
public final class PlayerMixin {
   private PlayerMixin() {
   }

   @Inject(
      method = {"disableShield"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void extendedWorkbench$disableShield(CallbackInfo ci) {
      Player instance = (Player)(Object)this;
      if (instance.getRandom().nextInt(4) == 0 && instance.getUseItem().getItem() instanceof ExtendedShieldItem) {
         ci.cancel();
      }

   }
}
