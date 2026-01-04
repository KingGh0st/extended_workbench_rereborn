package com.garouelcazador.extended_workbench.mixins.client;

import com.garouelcazador.extended_workbench.client.renderer.EWItemRenderer;
import java.util.List;
import java.util.Map;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.BlockStateModelLoader.LoadedJson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ModelBakery.class})
public abstract class ModelBakeryMixin {
   @Shadow
   protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation var1);

   private ModelBakeryMixin() {
   }

   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")}
   )
   private void extendedWorkbench$init(BlockColors p_249183_, ProfilerFiller p_252014_, Map<ResourceLocation, BlockModel> p_251087_, Map<ResourceLocation, List<LoadedJson>> p_250416_, CallbackInfo ci) {
      this.loadSpecialItemModelAndDependencies(EWItemRenderer.EXTENDED_TRIDENT_IN_HAND_MODEL);
   }
}
