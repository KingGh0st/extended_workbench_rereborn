package com.garouelcazador.extended_workbench.client.model;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class EWModelBakery {
   public static final Material NO_PATTERN_EXTENDED_SHIELD;

   private EWModelBakery() {
   }

   static {
      NO_PATTERN_EXTENDED_SHIELD = new Material(Sheets.SHIELD_SHEET, ResourceLocation.fromNamespaceAndPath("extended_workbench", "entity/extended_shield_base_nopattern"));
   }
}
