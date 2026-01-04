package com.garouelcazador.extended_workbench.items.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

public class ExtendedBowItem extends BowItem {
   public static final int MAX_DRAW_DURATION = 30;

   public ExtendedBowItem(Properties properties) {
      super(properties);
   }

   public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity entity) {
      return 44000;
   }

   public static float getPowerForTime(int useDuration) {
      float f = (float)useDuration / 20.0F;
      return Math.min((f * f + f * 2.0F) / 3.0F, 1.5F);
   }
}
