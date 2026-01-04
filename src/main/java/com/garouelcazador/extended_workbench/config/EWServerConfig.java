package com.garouelcazador.extended_workbench.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;

public final class EWServerConfig {
   public static final Builder BUILDER = new Builder();
   public static final ModConfigSpec SPEC;
   public static final DoubleValue EXTENDED_EQUIPMENT_PROBABILITY;
   public static final DoubleValue NATIVE_EXTENDED_EQUIPMENT_PROBABILITY;
   public static final DoubleValue EXTENDED_TRIDENT_PROBABILITY;

   private EWServerConfig() {
   }

   static {
      BUILDER.push("Configs for Extended Workbench");
      EXTENDED_EQUIPMENT_PROBABILITY = BUILDER.comment("Chance of replacing mob equipment with its extended version.").defineInRange("replaceEquipmentChance", 0.2D, 0.0D, 1.0D);
      NATIVE_EXTENDED_EQUIPMENT_PROBABILITY = BUILDER.comment("Chance of replacing equipment for mobs that always carry a weapon (skeletons, pillagers, vindicators, etc).").defineInRange("replaceNativeEquipmentChance", 0.1D, 0.0D, 1.0D);
      EXTENDED_TRIDENT_PROBABILITY = BUILDER.comment("Chance of a drowned replacing his trident with its extended version.").defineInRange("extendedTridentChance", 0.2D, 0.0D, 1.0D);
      BUILDER.pop();
      SPEC = BUILDER.build();
   }
}
