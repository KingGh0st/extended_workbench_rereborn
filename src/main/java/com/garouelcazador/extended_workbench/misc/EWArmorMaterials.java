package com.garouelcazador.extended_workbench.misc;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EWArmorMaterials {
   private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIAL;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_LEATHER;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_CHAIN;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_IRON;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_GOLD;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_DIAMOND;
   public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EXTENDED_NETHERITE;

   private EWArmorMaterials() {
   }

   private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<Type, Integer> armorValue, int durability, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
      List<Layer> list = List.of(new Layer(ResourceLocation.fromNamespaceAndPath("extended_workbench", name)));
      return register(name, armorValue, durability, equipSound, toughness, knockbackResistance, repairIngredient, list);
   }

   private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, EnumMap<Type, Integer> armorValue, int durability, Holder<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<Layer> armorLayers) {
      EnumMap<Type, Integer> enummap = new EnumMap(Type.class);
      Type[] var9 = Type.values();
      int var10 = var9.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         Type armoritem$type = var9[var11];
         enummap.put(armoritem$type, (Integer)armorValue.get(armoritem$type));
      }

      return ARMOR_MATERIAL.register(name, () -> {
         return new ArmorMaterial(enummap, durability, equipSound, repairIngredient, armorLayers, toughness, knockbackResistance);
      });
   }

   public static void register(IEventBus modEventBus) {
      ARMOR_MATERIAL.register(modEventBus);
   }

   static {
      ARMOR_MATERIAL = DeferredRegister.create(Registries.ARMOR_MATERIAL, "extended_workbench");
      EXTENDED_LEATHER = register("extended_leather", (EnumMap)Util.make(new EnumMap(Type.class), (p_327101_) -> {
         p_327101_.put(Type.BOOTS, 1);
         p_327101_.put(Type.LEGGINGS, 2);
         p_327101_.put(Type.CHESTPLATE, 3);
         p_327101_.put(Type.HELMET, 1);
         p_327101_.put(Type.BODY, 3);
      }), 19, SoundEvents.ARMOR_EQUIP_LEATHER, 1.0F, 0.0F, () -> {
         return Ingredient.of(new ItemLike[]{Items.LEATHER});
      }, List.of(new Layer(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_leather"), "", true), new Layer(ResourceLocation.fromNamespaceAndPath("extended_workbench", "extended_leather"), "_overlay", false)));
      EXTENDED_CHAIN = register("extended_chainmail", (EnumMap)Util.make(new EnumMap(Type.class), (p_327098_) -> {
         p_327098_.put(Type.BOOTS, 1);
         p_327098_.put(Type.LEGGINGS, 4);
         p_327098_.put(Type.CHESTPLATE, 5);
         p_327098_.put(Type.HELMET, 2);
         p_327098_.put(Type.BODY, 4);
      }), 18, SoundEvents.ARMOR_EQUIP_CHAIN, 1.0F, 0.0F, () -> {
         return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
      });
      EXTENDED_IRON = register("extended_iron", (EnumMap)Util.make(new EnumMap(Type.class), (p_327096_) -> {
         p_327096_.put(Type.BOOTS, 2);
         p_327096_.put(Type.LEGGINGS, 5);
         p_327096_.put(Type.CHESTPLATE, 6);
         p_327096_.put(Type.HELMET, 2);
         p_327096_.put(Type.BODY, 5);
      }), 14, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, () -> {
         return Ingredient.of(new ItemLike[]{Items.IRON_INGOT});
      });
      EXTENDED_GOLD = register("extended_gold", (EnumMap)Util.make(new EnumMap(Type.class), (p_327100_) -> {
         p_327100_.put(Type.BOOTS, 1);
         p_327100_.put(Type.LEGGINGS, 3);
         p_327100_.put(Type.CHESTPLATE, 5);
         p_327100_.put(Type.HELMET, 2);
         p_327100_.put(Type.BODY, 7);
      }), 38, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0.0F, () -> {
         return Ingredient.of(new ItemLike[]{Items.GOLD_INGOT});
      });
      EXTENDED_DIAMOND = register("extended_diamond", (EnumMap)Util.make(new EnumMap(Type.class), (p_327102_) -> {
         p_327102_.put(Type.BOOTS, 3);
         p_327102_.put(Type.LEGGINGS, 6);
         p_327102_.put(Type.CHESTPLATE, 8);
         p_327102_.put(Type.HELMET, 3);
         p_327102_.put(Type.BODY, 11);
      }), 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0F, 0.0F, () -> {
         return Ingredient.of(new ItemLike[]{Items.DIAMOND});
      });
      EXTENDED_NETHERITE = register("extended_netherite", (EnumMap)Util.make(new EnumMap(Type.class), (p_327103_) -> {
         p_327103_.put(Type.BOOTS, 3);
         p_327103_.put(Type.LEGGINGS, 6);
         p_327103_.put(Type.CHESTPLATE, 8);
         p_327103_.put(Type.HELMET, 3);
         p_327103_.put(Type.BODY, 11);
      }), 23, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.1F, () -> {
         return Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT});
      });
   }
}
