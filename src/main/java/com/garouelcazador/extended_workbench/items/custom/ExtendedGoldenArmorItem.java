package com.garouelcazador.extended_workbench.items.custom;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;

public class ExtendedGoldenArmorItem extends ArmorItem {
   public ExtendedGoldenArmorItem(Holder<ArmorMaterial> p_329451_, Type p_266831_, Properties p_40388_) {
      super(p_329451_, p_266831_, p_40388_);
   }

   public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
      boolean var10000;
      if (!super.makesPiglinsNeutral(stack, wearer)) {
         label26: {
            Item var4 = stack.getItem();
            if (var4 instanceof ArmorItem) {
               ArmorItem armorItem = (ArmorItem)var4;
               if (armorItem.getMaterial() == ArmorMaterials.GOLD) {
                  break label26;
               }
            }

            var10000 = false;
            return var10000;
         }
      }

      var10000 = true;
      return var10000;
   }
}
