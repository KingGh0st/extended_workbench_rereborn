package com.garouelcazador.extended_workbench.items.custom;

import com.garouelcazador.extended_workbench.misc.EWDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtendedCrossbowItem extends CrossbowItem {
   public ExtendedCrossbowItem(Properties properties) {
      super(properties);
   }

   public void performShooting(@NotNull Level level, @NotNull LivingEntity entity, @NotNull InteractionHand hand, @NotNull ItemStack itemStack, float power, float accuracy, @Nullable LivingEntity p_329478_) {
      super.performShooting(level, entity, hand, itemStack, !(entity instanceof Player) && hasExtendedCharge(itemStack) ? power * 1.5F : power, accuracy, p_329478_);
   }

   public int getUseDuration(@NotNull ItemStack itemStack, @NotNull LivingEntity entity) {
      return (int)((float)super.getUseDuration(itemStack, entity) * 18.0F / 11.0F);
   }

   public static boolean hasExtendedCharge(ItemStack itemStack) {
      return (Boolean)itemStack.getOrDefault((DataComponentType)EWDataComponents.EXTENDED_CHARGED_PROJECTILES.get(), false);
   }

   public static void setExtendedCharge(ItemStack itemStack, boolean value) {
      itemStack.set((DataComponentType)EWDataComponents.EXTENDED_CHARGED_PROJECTILES.get(), value);
   }
}
