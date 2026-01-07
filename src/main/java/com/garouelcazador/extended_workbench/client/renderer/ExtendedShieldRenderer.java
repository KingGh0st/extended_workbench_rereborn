package com.garouelcazador.extended_workbench.client.renderer;

import com.garouelcazador.extended_workbench.client.model.EWModelBakery;
import com.garouelcazador.extended_workbench.client.model.custom.ExtendedShieldModel;
import com.garouelcazador.extended_workbench.client.model.geom.EWModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ExtendedShieldRenderer extends BlockEntityWithoutLevelRenderer {
      public static final ExtendedShieldRenderer INSTANCE = new ExtendedShieldRenderer();
      protected ExtendedShieldModel extendedShieldModel;

      public ExtendedShieldRenderer() {
         super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
         this.extendedShieldModel = new ExtendedShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_SHIELD));
      }

      @Override
      public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
         this.extendedShieldModel = new ExtendedShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(EWModelLayers.EXTENDED_SHIELD));
      }

      @Override
      public void renderByItem(ItemStack itemStack, @NotNull ItemDisplayContext displayContext, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light, int overlay) {
         BannerPatternLayers bannerpatterns = itemStack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
         DyeColor dyecolor = itemStack.get(DataComponents.BASE_COLOR);

         Material baseMaterial = EWModelBakery.NO_PATTERN_EXTENDED_SHIELD; //Base texture for extended shield
         
         poseStack.pushPose();
         poseStack.scale(1.0F, -1.0F, -1.0F);
         
         VertexConsumer handleConsumer = baseMaterial.sprite().wrap(ItemRenderer.getFoilBufferDirect(bufferSource, RenderType.entityCutout(baseMaterial.atlasLocation()), true, itemStack.hasFoil()));
         this.extendedShieldModel.handle().render(poseStack, handleConsumer, light, overlay);

         if (!bannerpatterns.layers().isEmpty() || dyecolor != null) {
               // Banner patterns
               BannerRenderer.renderPatterns(
                  poseStack, 
                  bufferSource, 
                  light, 
                  overlay, 
                  this.extendedShieldModel.plate(),
                  baseMaterial, 
                  false, 
                  Objects.requireNonNullElse(dyecolor, DyeColor.WHITE), 
                  bannerpatterns, 
                  itemStack.hasFoil()
               );
         } else {
               this.extendedShieldModel.plate().render(poseStack, handleConsumer, light, overlay);
         }

         poseStack.popPose();
      }
}