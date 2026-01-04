package com.garouelcazador.extended_workbench.events;

import com.garouelcazador.extended_workbench.items.EWItems;
import com.garouelcazador.extended_workbench.items.custom.ExtendedTridentItem;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.bus.api.SubscribeEvent;
import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import net.neoforged.fml.common.EventBusSubscriber;

public final class EWEvents {
   private EWEvents() {
   }

   @EventBusSubscriber(modid = ExtendedWorkbench.MOD_ID)
   public static final class ForgeEvents {
      private ForgeEvents() {
      }

      @SubscribeEvent
      public static void furnaceFuelBurnTimeEvent(FurnaceFuelBurnTimeEvent event) {
         ItemStack itemStack = event.getItemStack();
         if (!itemStack.is((Item)EWItems.EXTENDED_BOW.get()) && !itemStack.is((Item)EWItems.EXTENDED_CROSSBOW.get())) {
            if (itemStack.is((Item)EWItems.EXTENDED_WOODEN_SHOVEL.get()) || itemStack.is((Item)EWItems.EXTENDED_WOODEN_SWORD.get()) || itemStack.is((Item)EWItems.EXTENDED_WOODEN_HOE.get()) || itemStack.is((Item)EWItems.EXTENDED_WOODEN_AXE.get()) || itemStack.is((Item)EWItems.EXTENDED_WOODEN_PICKAXE.get())) {
               event.setBurnTime(300);
            }
         } else {
            event.setBurnTime(450);
         }

      }

      @SubscribeEvent
      public static void onEntityJoin(EntityJoinLevelEvent event) {
         if (event.getEntity() instanceof Drowned drowned && !event.getLevel().isClientSide) {
            if (drowned.getMainHandItem().getItem() instanceof ExtendedTridentItem) {
                  drowned.goalSelector.addGoal(2, new net.minecraft.world.entity.ai.goal.RangedAttackGoal(drowned, 1.0D, 40, 10.0F));
            }
         }
      }
   }
}
