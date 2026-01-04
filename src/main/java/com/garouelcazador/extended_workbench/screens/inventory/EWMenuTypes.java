package com.garouelcazador.extended_workbench.screens.inventory;

import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

public final class EWMenuTypes {
      public static final DeferredRegister<MenuType<?>> MENUS = 
            DeferredRegister.create(Registries.MENU, ExtendedWorkbench.MOD_ID);

      public static final DeferredHolder<MenuType<?>, MenuType<ExtendedCraftingMenu>> EXTENDED_WORKBENCH_MENU =
            MENUS.register("extended_workbench_menu", () -> IMenuTypeExtension.create(ExtendedCraftingMenu::new));

      public static void register(IEventBus eventBus) {
            MENUS.register(eventBus);
      }
}