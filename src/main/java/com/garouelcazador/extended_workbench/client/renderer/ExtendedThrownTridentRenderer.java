package com.garouelcazador.extended_workbench.client.renderer;

import com.garouelcazador.extended_workbench.client.model.custom.ExtendedTridentModel;
import com.garouelcazador.extended_workbench.client.model.geom.EWModelLayers;
import com.garouelcazador.extended_workbench.entities.custom.ExtendedThrownTrident;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ExtendedThrownTridentRenderer extends EntityRenderer<ExtendedThrownTrident> {
   public static final ResourceLocation EXTENDED_TRIDENT_LOCATION = ResourceLocation.fromNamespaceAndPath("extended_workbench", "textures/entity/extended_trident.png");
   private final ExtendedTridentModel model;

   public ExtendedThrownTridentRenderer(Context p_174420_) {
      super(p_174420_);
      this.model = new ExtendedTridentModel(p_174420_.bakeLayer(EWModelLayers.EXTENDED_TRIDENT));
   }

   public void render(ExtendedThrownTrident p_116111_, float p_116112_, float p_116113_, PoseStack p_116114_, @NotNull MultiBufferSource p_116115_, int p_116116_) {
      p_116114_.pushPose();
      p_116114_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.yRotO, p_116111_.getYRot()) - 90.0F));
      p_116114_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_116113_, p_116111_.xRotO, p_116111_.getXRot()) + 90.0F));
      VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(this.getTextureLocation(p_116111_)), false, p_116111_.isFoil());
      this.model.renderToBuffer(p_116114_, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY);
      p_116114_.popPose();
      super.render(p_116111_, p_116112_, p_116113_, p_116114_, p_116115_, p_116116_);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull ExtendedThrownTrident p_116109_) {
      return EXTENDED_TRIDENT_LOCATION;
   }
}
