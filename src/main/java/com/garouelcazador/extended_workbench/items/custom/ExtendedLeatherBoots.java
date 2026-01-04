package com.garouelcazador.extended_workbench.items.custom;

import com.garouelcazador.extended_workbench.items.EWItems;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class ExtendedLeatherBoots extends ArmorItem {
   public ExtendedLeatherBoots(Holder<ArmorMaterial> p_329451_, Type p_266831_, Properties p_40388_) {
      super(p_329451_, p_266831_, p_40388_);
   }

   public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
      return super.canWalkOnPowderedSnow(stack, wearer) || stack.is((Item)EWItems.EXTENDED_LEATHER_BOOTS.get());
   }
}
