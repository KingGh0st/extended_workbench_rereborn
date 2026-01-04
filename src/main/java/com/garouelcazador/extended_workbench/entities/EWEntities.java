package com.garouelcazador.extended_workbench.entities;

import com.garouelcazador.extended_workbench.entities.custom.ExtendedThrownTrident;
import java.util.function.Supplier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EWEntities {
   private static final DeferredRegister<EntityType<?>> ENTITY_TYPES;
   public static final DeferredHolder<EntityType<?>, ? extends EntityType<?>> EXTENDED_TRIDENT;

   private EWEntities() {
   }

   public static void register(IEventBus modEventBus) {
      ENTITY_TYPES.register(modEventBus);
   }

   static {
      ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, "extended_workbench");
      EXTENDED_TRIDENT = ENTITY_TYPES.register("extended_trident", () -> {
         return Builder.<ExtendedThrownTrident>of((type, lvl) -> new ExtendedThrownTrident(type, lvl), MobCategory.MISC).sized(0.6F, 0.6F).eyeHeight(0.13F).clientTrackingRange(4).updateInterval(20).build("extended_trident");
      });
   }
}
