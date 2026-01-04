package com.garouelcazador.extended_workbench.client.renderer;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class EWItemRenderer {
   public static final ModelResourceLocation EXTENDED_TRIDENT_MODEL = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_trident"));
   public static final ModelResourceLocation EXTENDED_TRIDENT_IN_HAND_MODEL = ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_trident_in_hand"));

   private EWItemRenderer() {
   }
}
