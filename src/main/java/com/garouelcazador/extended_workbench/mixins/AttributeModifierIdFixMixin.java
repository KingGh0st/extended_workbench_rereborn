package com.garouelcazador.extended_workbench.mixins;

import com.google.common.collect.ImmutableMap.Builder;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import java.util.UUID;
import net.minecraft.util.datafix.fixes.AttributeModifierIdFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({AttributeModifierIdFix.class})
public final class AttributeModifierIdFixMixin {
   private AttributeModifierIdFixMixin() {
   }

   @ModifyReceiver(
      method = {"<clinit>"},
      at = {@At(
   value = "INVOKE",
   target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"
)}
   )
   private static Builder<UUID, String> extendedWorkbench$clinit(Builder<UUID, String> instance) {
      return instance.put(UUID.fromString("1E1B9A06-440F-4344-A35D-4B644C2A030E"), "extended_workbench:base_block_interaction_range").put(UUID.fromString("BDB63367-5830-46B0-B974-DC6DA3DEDC26"), "extended_workbench:base_entity_interaction_range");
   }
}
