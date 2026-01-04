package com.garouelcazador.extended_workbench.misc;

import com.garouelcazador.extended_workbench.items.EWItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EWCreativeModeTab {
   private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB;
   public static final DeferredHolder<CreativeModeTab, ? extends CreativeModeTab> EXTENDED_WORKBENCH_TAB;

   private EWCreativeModeTab() {
   }

   public static void register(IEventBus modEventBus) {
      CREATIVE_MODE_TAB.register(modEventBus);
   }

   static {
      CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "extended_workbench");
      EXTENDED_WORKBENCH_TAB = CREATIVE_MODE_TAB.register("extended_workbench", () -> {
         return CreativeModeTab.builder().icon(() -> {
            return new ItemStack((ItemLike)EWItems.EXTENDED_NETHERITE_SWORD.get());
         }).title(Component.translatable("itemGroup.extended_workbench")).displayItems((parameters, output) -> {
            EWItems.getEntries().forEach((registry) -> {
               output.accept((ItemLike)registry.get());
            });
         }).build();
      });
   }
}
