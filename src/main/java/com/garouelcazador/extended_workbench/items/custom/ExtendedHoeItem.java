package com.garouelcazador.extended_workbench.items.custom;

import com.mojang.datafixers.util.Pair;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
// ToolActions may be provided by mappings or by Forge; use reflection instead of a direct import
// import net.minecraft.world.item.ToolActions;
import org.jetbrains.annotations.NotNull;

public class ExtendedHoeItem extends HoeItem {
   public ExtendedHoeItem(Tier p_41336_, Properties p_41339_) {
      super(p_41336_, p_41339_);
   }

   @NotNull
   public InteractionResult useOn(UseOnContext useOnContext) {
      Level level = useOnContext.getLevel();
      BlockPos blockpos = useOnContext.getClickedPos();
      BlockState toolModifiedState = getToolModifiedStateReflective(level.getBlockState(blockpos), useOnContext);
      Pair<Predicate<UseOnContext>, Function<UseOnContext, Integer>> pair = toolModifiedState == null ? null : Pair.of((ctx) -> {
         return true;
      }, ewChangeIntoState(toolModifiedState));
      if (pair == null) {
         return InteractionResult.PASS;
      } else {
         Predicate<UseOnContext> predicate = (Predicate)pair.getFirst();
         Function<UseOnContext, Integer> consumer = (Function)pair.getSecond();
         if (predicate.test(useOnContext)) {
            Player player = useOnContext.getPlayer();
            level.playSound(player, blockpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide && player != null) {
               useOnContext.getItemInHand().hurtAndBreak((Integer)consumer.apply(useOnContext), player, LivingEntity.getSlotForHand(useOnContext.getHand()));
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public static Function<UseOnContext, Integer> ewChangeIntoState(@NotNull BlockState blockState) {
      return (useOnContext) -> {
         Level level = useOnContext.getLevel();
         ItemStack handItem = useOnContext.getItemInHand();
         if (handItem.isEmpty()) {
            return 0;
         } else {
            BlockPos blockPos = useOnContext.getClickedPos();
            Player player = useOnContext.getPlayer();
            int maxDamage = handItem.getMaxDamage();
            int originalDurability = maxDamage - handItem.getDamageValue();
            level.setBlock(blockPos, blockState, 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, Context.of(player, blockState));
            int durability = originalDurability - 1;
            if (durability > 0) {
               for(int x = -1; x <= 1; ++x) {
                  for(int z = -1; z <= 1; ++z) {
                     if (x != 0 || z != 0) {
                        BlockPos actualBlock = new BlockPos(blockPos.getX() + x, blockPos.getY(), blockPos.getZ() + z);
                        if (getToolModifiedStateReflective(level.getBlockState(actualBlock), useOnContext) != null) {
                           level.setBlock(actualBlock, blockState, 11);
                           level.gameEvent(GameEvent.BLOCK_CHANGE, actualBlock, Context.of(player, blockState));
                           --durability;
                        }

                        if (durability == 0) {
                           break;
                        }
                     }
                  }

                  if (durability == 0) {
                     break;
                  }
               }
            }

            return originalDurability - durability;
         }
      };
   }

   // Reflection helpers to remain compatible across mappings / Forge
   private static Object getHoeTillAction() {
      try {
         Class<?> mc = Class.forName("net.minecraft.world.item.ToolActions");
         try {
            try {
               return mc.getField("HOE_TILL").get(null);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
         } catch (Exception ignored) {
         }
      } catch (ClassNotFoundException ignored) {
      }

      try {
         Class<?> forge = Class.forName("net.minecraftforge.common.ToolActions");
         try {
            try {
               return forge.getField("HOE_TILL").get(null);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
         } catch (Exception ignored) {
         }
      } catch (ClassNotFoundException ignored) {
      }

      return null;
   }

   private static BlockState getToolModifiedStateReflective(BlockState state, UseOnContext ctx) {
      Object hoe = getHoeTillAction();
      if (hoe == null) return null;

      try {
         java.lang.reflect.Method target = null;
         for (java.lang.reflect.Method m : state.getClass().getMethods()) {
            if (m.getName().equals("getToolModifiedState")) {
               Class<?>[] p = m.getParameterTypes();
               if (p.length == 3 && p[0].isAssignableFrom(UseOnContext.class) && p[2] == boolean.class) {
                  target = m;
                  break;
               }
            }
         }

         if (target == null) return null;
         Object res = target.invoke(state, ctx, hoe, false);
         return (BlockState)res;
      } catch (ReflectiveOperationException e) {
         return null;
      }
   }
}
