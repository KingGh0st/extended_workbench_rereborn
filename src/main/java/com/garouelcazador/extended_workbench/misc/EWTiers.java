package com.garouelcazador.extended_workbench.misc;

import java.util.Objects;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.common.SimpleTier;

public final class EWTiers {
   public static final Tier WOOD;
   public static final Tier STONE;
   public static final Tier IRON;
   public static final Tier DIAMOND;
   public static final Tier GOLD;
   public static final Tier NETHERITE;

   private EWTiers() {
   }

   private static Tier upgradeTier(Tier tier) {
      int var10002 = Math.round((float)tier.getUses() * 1.25F);
      float var10003 = tier.getSpeed() + 0.5F;
      float var10004 = tier.getAttackDamageBonus();
      int var10005 = tier.getEnchantmentValue();
      Objects.requireNonNull(tier);
      return new SimpleTier(tier.getIncorrectBlocksForDrops(), var10002, var10003, var10004, var10005, tier::getRepairIngredient);
   }

   static {
      WOOD = upgradeTier(Tiers.WOOD);
      STONE = upgradeTier(Tiers.STONE);
      IRON = upgradeTier(Tiers.IRON);
      DIAMOND = upgradeTier(Tiers.DIAMOND);
      GOLD = upgradeTier(Tiers.GOLD);
      NETHERITE = upgradeTier(Tiers.NETHERITE);
   }
}
