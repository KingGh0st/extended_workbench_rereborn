package com.garouelcazador.extended_workbench.events;

import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import com.garouelcazador.extended_workbench.client.model.custom.ExtendedShieldModel;
import com.garouelcazador.extended_workbench.client.model.custom.ExtendedTridentModel;
import com.garouelcazador.extended_workbench.client.model.geom.EWModelLayers;
import com.garouelcazador.extended_workbench.client.renderer.ExtendedThrownTridentRenderer;
import com.garouelcazador.extended_workbench.entities.EWEntities;
import com.garouelcazador.extended_workbench.entities.custom.ExtendedThrownTrident;
import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.items.custom.ExtendedBowItem;
import com.garouelcazador.extended_workbench.screens.inventory.EWMenuTypes;
import com.garouelcazador.extended_workbench.screens.inventory.ExtendedCraftingMenu;
import com.garouelcazador.extended_workbench.screens.inventory.ExtendedCraftingScreen;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public final class EWClientEvents {
   private EWClientEvents() {}

   @EventBusSubscriber(modid = ExtendedWorkbench.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
   public static final class ClientModEvents {
      private ClientModEvents() {}

      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         event.enqueueWork(() -> {
            //Bow
            ItemProperties.register(EWItems.EXTENDED_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (pStack, pLevel, pEntity, pSeed) -> {
               if (pEntity == null) return 0.0F;
               return pEntity.getUseItem() != pStack ? 0.0F : (float)(pStack.getUseDuration(pEntity) - pEntity.getUseItemRemainingTicks()) / 20.0F;
            });
            ItemProperties.register(EWItems.EXTENDED_BOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (pStack, pLevel, pEntity, pSeed) -> {
               return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F;
            });

            //Crossbow
            ItemProperties.register(EWItems.EXTENDED_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (pStack, pLevel, pEntity, pSeed) -> {
               if (pEntity == null) return 0.0F;
               return CrossbowItem.isCharged(pStack) ? 0.0F : (float)(pStack.getUseDuration(pEntity) - pEntity.getUseItemRemainingTicks()) / (float)CrossbowItem.getChargeDuration(pStack, pEntity);
            });
            ItemProperties.register(EWItems.EXTENDED_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("pulling"), (pStack, pLevel, pEntity, pSeed) -> {
               return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack && !CrossbowItem.isCharged(pStack) ? 1.0F : 0.0F;
            });
            ItemProperties.register(EWItems.EXTENDED_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("charged"), (pStack, pLevel, pEntity, pSeed) -> {
               return CrossbowItem.isCharged(pStack) ? 1.0F : 0.0F;
            });
            ItemProperties.register(EWItems.EXTENDED_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (pStack, pLevel, pEntity, pSeed) -> {
               ChargedProjectiles charged = pStack.get(DataComponents.CHARGED_PROJECTILES);
               return charged != null && charged.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
            });

            //Shield
            ItemProperties.register(EWItems.EXTENDED_SHIELD.get(), ResourceLocation.withDefaultNamespace("blocking"), (pStack, pLevel, pEntity, pSeed) -> {
               return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F;
            });

            //Trident
            ItemProperties.register(EWItems.EXTENDED_TRIDENT.get(), ResourceLocation.withDefaultNamespace("throwing"), (pStack, pLevel, pEntity, pSeed) -> {
               return pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F;
            });
         });
      }

      //Leather colors
      @SubscribeEvent
      public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((itemStack, color) -> {
               return color > 0 ? -1 : DyedItemColor.getOrDefault(itemStack, -6265536);
            }, EWItems.EXTENDED_LEATHER_HELMET.get(), EWItems.EXTENDED_LEATHER_CHESTPLATE.get(), 
               EWItems.EXTENDED_LEATHER_LEGGINGS.get(), EWItems.EXTENDED_LEATHER_BOOTS.get());
      }

      @SubscribeEvent
      public static void registerScreens(RegisterMenuScreensEvent event) {

         event.register(
            (net.minecraft.world.inventory.MenuType<ExtendedCraftingMenu>) EWMenuTypes.EXTENDED_WORKBENCH_MENU.get(), 
            ExtendedCraftingScreen::new
         );
      }

      @SubscribeEvent
      public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
         event.registerLayerDefinition(EWModelLayers.EXTENDED_SHIELD, ExtendedShieldModel::createLayer);
         event.registerLayerDefinition(EWModelLayers.EXTENDED_TRIDENT, ExtendedTridentModel::createLayer);
      }

      @SubscribeEvent
      public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
         event.registerEntityRenderer(
            (net.minecraft.world.entity.EntityType<ExtendedThrownTrident>) EWEntities.EXTENDED_TRIDENT.get(), 
            ExtendedThrownTridentRenderer::new
         );
      }
   }

   @EventBusSubscriber(modid = ExtendedWorkbench.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
   public static final class ClientForgeEvents {
      private ClientForgeEvents() {}

      @SubscribeEvent
      public static void onFOVUpdate(ComputeFovModifierEvent event) {
         Player entity = event.getPlayer();
         Item item = entity.getUseItem().getItem();
         if (item instanceof ExtendedBowItem) {
               float fovModifier = (float)((int)((float)entity.getTicksUsingItem() * 0.8F)) / 30.0F;
               fovModifier = Math.min(fovModifier, 1.0F);
               fovModifier *= fovModifier;
               event.setNewFovModifier(event.getNewFovModifier() * (1.0F - fovModifier * 0.15F));
         }
      }
   }
}