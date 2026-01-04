package com.garouelcazador.extended_workbench.mixins;

import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class})
public final class BlockMixin {
   @Unique
   private static final Direction[] EXTENDED_WORKBENCH$HORIZONTAL;
   @Unique
   private final Block extendedWorkbench$targetClass = (Block)(Object)this;

   private BlockMixin() {
   }

   @Inject(
      method = {"createBlockStateDefinition"},
      at = {@At("TAIL")}
   )
   private void extendedWorkbench$createBlockStateDefinition(Builder<Block, BlockState> blockBlockStateBuilder, CallbackInfo ci) {
      if (this.extendedWorkbench$targetClass.getClass() == CraftingTableBlock.class) {
         blockBlockStateBuilder.add(new Property[]{EWHelper.EXTENDED});
      }

   }

   @ModifyReturnValue(
      method = {"getStateForPlacement"},
      at = {@At("RETURN")}
   )
   private BlockState extendedWorkbench$getStateForPlacement(BlockState original, BlockPlaceContext blockPlaceContext) {
      if (this.extendedWorkbench$targetClass.getClass() != CraftingTableBlock.class) {
         return original;
      } else {
         CraftingTableBlock instance = (CraftingTableBlock)this.extendedWorkbench$targetClass;
         int craftingTableType = 0;
         BlockPos blockPos = blockPlaceContext.getClickedPos();
         Direction getDirectionFace = blockPlaceContext.getClickedFace();
         Direction directionToBaseBlock = null;
         Direction[] var8 = EXTENDED_WORKBENCH$HORIZONTAL;
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            Direction blockDirection = var8[var10];
            blockDirection = blockDirection.getOpposite();
            BlockState candidateBlock = blockPlaceContext.getLevel().getBlockState(blockPos.relative(blockDirection));
            if (candidateBlock.is(instance) && (Integer)candidateBlock.getValue(EWHelper.EXTENDED) == 0) {
               directionToBaseBlock = blockDirection;
               break;
            }
         }

         boolean flag = blockPlaceContext.isSecondaryUseActive();
         if (getDirectionFace.getAxis().isHorizontal() && flag) {
            BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPos.relative(getDirectionFace.getOpposite()));
            if (blockState.is(instance) && (Integer)blockState.getValue(EWHelper.EXTENDED) == 0) {
               craftingTableType = EWHelper.directionToType(getDirectionFace.getOpposite());
            }
         }

         if (craftingTableType == 0 && !flag && directionToBaseBlock != null) {
            craftingTableType = EWHelper.directionToType(directionToBaseBlock);
         }

         return (BlockState)original.setValue(EWHelper.EXTENDED, craftingTableType);
      }
   }

   static {
      EXTENDED_WORKBENCH$HORIZONTAL = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
   }
}
