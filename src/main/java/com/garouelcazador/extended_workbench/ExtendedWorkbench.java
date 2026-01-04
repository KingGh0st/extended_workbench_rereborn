package com.garouelcazador.extended_workbench;

import com.garouelcazador.extended_workbench.config.EWServerConfig;
import com.garouelcazador.extended_workbench.entities.EWEntities;
import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.misc.EWArmorMaterials;
import com.garouelcazador.extended_workbench.misc.EWCreativeModeTab;
import com.garouelcazador.extended_workbench.misc.EWDataComponents;
import com.garouelcazador.extended_workbench.recipe.EWRecipeTypes;
import com.garouelcazador.extended_workbench.screens.inventory.EWMenuTypes;

import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import com.garouelcazador.extended_workbench.events.EWClientEvents;
import com.garouelcazador.extended_workbench.events.EWEvents;

@Mod("extended_workbench")
public final class ExtendedWorkbench {
   public static final String MOD_ID = "extended_workbench";

   public ExtendedWorkbench(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(ModConfig.Type.SERVER, EWServerConfig.SPEC);
      EWItems.register(modEventBus);
      EWMenuTypes.register(modEventBus);
      EWCreativeModeTab.register(modEventBus);
      EWArmorMaterials.register(modEventBus);
      EWDataComponents.register(modEventBus);
      EWEntities.register(modEventBus);
      EWRecipeTypes.register(modEventBus);

      // Register client mod event listeners (setup only; other client registrations are handled via @EventBusSubscriber)
      modEventBus.addListener(EWClientEvents.ClientModEvents::onClientSetup);

      // Register Forge bus events (register subscriber classes rather than instances)
      //NeoForge.EVENT_BUS.register(EWClientEvents.class);
      //NeoForge.EVENT_BUS.register(EWEvents.class);
   }
}
