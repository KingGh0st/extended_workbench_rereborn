package com.garouelcazador.extended_workbench.items.custom;

import com.garouelcazador.extended_workbench.client.renderer.ExtendedTridentRenderer;
import com.garouelcazador.extended_workbench.entities.custom.ExtendedThrownTrident;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ExtendedTridentItem extends TridentItem {
   public ExtendedTridentItem(Properties properties) {
      super(properties);
   }

   @Override
   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }


   public void releaseUsing(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity, int p_43397_) {
      int i = this.getUseDuration(itemStack, entity) - p_43397_;
      if (i >= 12  || (i >= 10 && !(entity instanceof Player))) {
         float f = EnchantmentHelper.getTridentSpinAttackStrength(itemStack, entity);
         if ((!(f > 0.0F) || entity.isInWaterOrRain()) && !isTooDamagedToUse(itemStack)) {
            Holder<SoundEvent> holder = (Holder)EnchantmentHelper.pickHighestLevel(itemStack, EnchantmentEffectComponents.TRIDENT_SOUND).orElse(SoundEvents.TRIDENT_THROW);
            if (!level.isClientSide) {
               itemStack.hurtAndBreak(1, entity, LivingEntity.getSlotForHand(entity.getUsedItemHand()));
               if (f == 0.0F) {
                  ExtendedThrownTrident throwntrident = new ExtendedThrownTrident(level, entity, itemStack);
                  throwntrident.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0.0F, 3.0F, 1.0F);

                  if(entity instanceof Player player){
                     if (player.hasInfiniteMaterials()) {
                        throwntrident.pickup = Pickup.CREATIVE_ONLY;
                     } else {
                        player.getInventory().removeItem(itemStack);
                     }
                     player.awardStat(Stats.ITEM_USED.get(this));
                  }
                  level.addFreshEntity(throwntrident);
                  level.playSound(null, throwntrident, (SoundEvent)holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
               }
            }

            
            if (f > 0.0F && entity instanceof Player player) {
               float f7 = player.getYRot();
               float f1 = player.getXRot();
               float f2 = -Mth.sin(f7 * 0.017453292F) * Mth.cos(f1 * 0.017453292F);
               float f3 = -Mth.sin(f1 * 0.017453292F);
               float f4 = Mth.cos(f7 * 0.017453292F) * Mth.cos(f1 * 0.017453292F);
               float f5 = Mth.sqrt(f2 * f2 + f3 * f3 + f4 * f4);
               f2 *= f / f5;
               f3 *= f / f5;
               f4 *= f / f5;
               player.push((double)f2, (double)f3, (double)f4);
               player.startAutoSpinAttack(20, 8.0F, itemStack);
               if (player.onGround()) {
                  float f6 = 1.1999999F;
                  player.move(MoverType.SELF, new Vec3(0.0D, 1.1999999284744263D, 0.0D));
               }

               level.playSound((Player)null, player, (SoundEvent)holder.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
         }
      }
   

   }

   @NotNull
   public Projectile asProjectile(@NotNull Level level, Position position, ItemStack itemStack, @NotNull Direction direction) {
      ExtendedThrownTrident throwntrident = new ExtendedThrownTrident(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
      throwntrident.pickup = Pickup.ALLOWED;
      return throwntrident;
   }

   public int getUseDuration(@NotNull ItemStack p_43419_, @NotNull LivingEntity p_344216_) {
      return 72000;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return ExtendedTridentRenderer.INSTANCE;
         }
      });
   }

   private static boolean isTooDamagedToUse(@NotNull ItemStack stack) {
      return stack.getDamageValue() >= stack.getMaxDamage() - 1;
   }
}
