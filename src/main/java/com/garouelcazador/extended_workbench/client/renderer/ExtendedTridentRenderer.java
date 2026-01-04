package com.garouelcazador.extended_workbench.client.renderer;

import com.garouelcazador.extended_workbench.client.model.custom.ExtendedTridentModel;
import com.garouelcazador.extended_workbench.client.model.geom.EWModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ExtendedTridentRenderer extends BlockEntityWithoutLevelRenderer {
   public static final ExtendedTridentRenderer INSTANCE = new ExtendedTridentRenderer();
   protected ExtendedTridentModel extendedTridentModel;

   public ExtendedTridentRenderer() {
      super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      this.extendedTridentModel = new ExtendedTridentModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_TRIDENT));
   }

   public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
      this.extendedTridentModel = new ExtendedTridentModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_TRIDENT));
   }

   public void renderByItem(ItemStack itemStack, @NotNull ItemDisplayContext ItemDisplayContext, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
      poseStack.pushPose();
      poseStack.scale(1.0F, -1.0F, -1.0F);
      this.extendedTridentModel.renderToBuffer(poseStack, ItemRenderer.getFoilBufferDirect(bufferSource, this.extendedTridentModel.renderType(ExtendedTridentModel.TEXTURE), false, itemStack.hasFoil()), light, overlay);
      poseStack.popPose();
   }
}
