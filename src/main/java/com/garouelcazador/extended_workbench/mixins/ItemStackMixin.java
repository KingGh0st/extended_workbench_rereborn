package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemStack.class})
public final class ItemStackMixin {
   private ItemStackMixin() {
   }

   @Inject(
      method = {"addModifierTooltip"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;is(Lnet/minecraft/resources/ResourceLocation;)Z",
   ordinal = 0
)}
   )
   private void extendedWorkbench$addModifierTooltip(Consumer<Component> componentConsumer, Player player, Holder<Attribute> attributeHolder, AttributeModifier attributeModifier, CallbackInfo ci, @Local LocalDoubleRef doubleRef, @Local LocalBooleanRef booleanRef) {
      if (attributeModifier.is(EWHelper.BASE_BLOCK_INTERACTION_RANGE_ID)) {
         doubleRef.set(doubleRef.get() + player.getAttributeBaseValue(Attributes.BLOCK_INTERACTION_RANGE));
         booleanRef.set(true);
      } else if (attributeModifier.is(EWHelper.BASE_ENTITY_INTERACTION_RANGE_ID)) {
         doubleRef.set(doubleRef.get() + player.getAttributeBaseValue(Attributes.ENTITY_INTERACTION_RANGE));
         booleanRef.set(true);
      }

   }
}
