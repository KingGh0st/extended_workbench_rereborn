package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.garouelcazador.extended_workbench.screens.inventory.ExtendedCraftingMenu;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CraftingTableBlock.class})
public final class CraftingTableBlockMixin extends Block {
   @Unique
   private final CraftingTableBlock extendedWorkbench$targetClass = (CraftingTableBlock)(Object)this;

   private CraftingTableBlockMixin(Properties properties) {
      super(properties);
   }

   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")}
   )
   private void extendedWorkbench$init(Properties p_52225_, CallbackInfo ci) {
      if (this.extendedWorkbench$targetClass.getClass() == CraftingTableBlock.class) {
         this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(EWHelper.EXTENDED, 0));
      }

   }

   @ModifyReturnValue(
      method = {"getMenuProvider"},
      at = {@At("RETURN")}
   )
   private MenuProvider extendedWorkbench$getMenuProvider(MenuProvider original, BlockState blockState, Level level, BlockPos blockPos) {
      if (this.extendedWorkbench$targetClass.getClass() != CraftingTableBlock.class) {
         return original;
      } else {
         int value = (Integer)blockState.getValue(EWHelper.EXTENDED);
         return (MenuProvider)(value >= 1 && value <= 4 ? new SimpleMenuProvider((integer, inventory, player) -> {
            return new ExtendedCraftingMenu(integer, inventory, ContainerLevelAccess.create(level, blockPos));
         }, Component.translatable("container.extended_workbench.extended_crafting")) : original);
      }
   }
}
