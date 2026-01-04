package com.garouelcazador.extended_workbench.client.model.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class EWModelLayers {
   public static final ModelLayerLocation EXTENDED_SHIELD = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_shield"), "main");
   public static final ModelLayerLocation EXTENDED_TRIDENT = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_trident"), "main");

   private EWModelLayers() {
   }
}
