package com.garouelcazador.extended_workbench.items.custom;

import com.garouelcazador.extended_workbench.client.renderer.ExtendedShieldRenderer;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ShieldItem;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class ExtendedShieldItem extends ShieldItem {
   public ExtendedShieldItem(Properties properties) {
      super(properties);
   }

   @Override
   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @Override
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return ExtendedShieldRenderer.INSTANCE;
         }
      });
   }
}
