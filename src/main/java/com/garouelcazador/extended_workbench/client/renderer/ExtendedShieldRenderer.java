package com.garouelcazador.extended_workbench.client.renderer;

import com.garouelcazador.extended_workbench.client.model.EWModelBakery;
import com.garouelcazador.extended_workbench.client.model.custom.ExtendedShieldModel;
import com.garouelcazador.extended_workbench.client.model.geom.EWModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

public class ExtendedShieldRenderer extends BlockEntityWithoutLevelRenderer {
   public static final ExtendedShieldRenderer INSTANCE = new ExtendedShieldRenderer();
   protected ExtendedShieldModel extendedShieldModel;

   public ExtendedShieldRenderer() {
      super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
      this.extendedShieldModel = new ExtendedShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_SHIELD));
   }

   public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
      this.extendedShieldModel = new ExtendedShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_SHIELD));
   }

   public void renderByItem(ItemStack itemStack, @NotNull ItemDisplayContext ItemDisplayContext, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
      BannerPatternLayers bannerpatternlayers = (BannerPatternLayers)itemStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
      DyeColor dyecolor = (DyeColor)itemStack.get(DataComponents.BASE_COLOR);
      boolean flag = !bannerpatternlayers.layers().isEmpty() || dyecolor != null;
      poseStack.pushPose();
      poseStack.scale(1.0F, -1.0F, -1.0F);
      VertexConsumer vertexconsumer = EWModelBakery.NO_PATTERN_EXTENDED_SHIELD.sprite().wrap(ItemRenderer.getFoilBufferDirect(bufferSource, this.extendedShieldModel.renderType(EWModelBakery.NO_PATTERN_EXTENDED_SHIELD.atlasLocation()), true, itemStack.hasFoil()));
      this.extendedShieldModel.handle().render(poseStack, vertexconsumer, light, overlay);
      if (flag) {
         BannerRenderer.renderPatterns(poseStack, bufferSource, light, overlay, this.extendedShieldModel.plate(), EWModelBakery.NO_PATTERN_EXTENDED_SHIELD, false, (DyeColor)Objects.requireNonNullElse(dyecolor, DyeColor.WHITE), bannerpatternlayers, itemStack.hasFoil());
      } else {
         this.extendedShieldModel.plate().render(poseStack, vertexconsumer, light, overlay);
      }

      poseStack.popPose();
   }
}
