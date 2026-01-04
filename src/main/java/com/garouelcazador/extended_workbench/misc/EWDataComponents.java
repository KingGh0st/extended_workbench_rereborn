package com.garouelcazador.extended_workbench.misc;

import com.mojang.serialization.Codec;
import java.util.function.UnaryOperator;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EWDataComponents {
   private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = 
      DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, "extended_workbench");

   public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> EXTENDED_CHARGED_PROJECTILES = 
      register("charged_projectiles", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).cacheEncoding());

   private EWDataComponents() {
   }

   private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<Builder<T>> builderOperator) {
      return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
   }

   public static void register(IEventBus modEventBus) {
      DATA_COMPONENT_TYPE.register(modEventBus);
   }
}