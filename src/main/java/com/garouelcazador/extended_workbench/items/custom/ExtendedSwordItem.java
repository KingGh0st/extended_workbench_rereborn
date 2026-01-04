package com.garouelcazador.extended_workbench.items.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class ExtendedSwordItem extends SwordItem {
   public ExtendedSwordItem(Tier p_43269_, Properties p_43272_) {
      super(p_43269_, p_43272_);
   }

   @NotNull
   public AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
      return target.getBoundingBox().inflate(1.25D, 0.25D, 1.25D);
   }
}
