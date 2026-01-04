package com.garouelcazador.extended_workbench.misc;

import com.garouelcazador.extended_workbench.config.EWServerConfig;
import com.garouelcazador.extended_workbench.items.EWItems;
import java.util.Iterator;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public final class EWHelper {
   public static final IntegerProperty EXTENDED = IntegerProperty.create("extended", 0, 4);
   public static final ResourceLocation BASE_BLOCK_INTERACTION_RANGE_ID = ResourceLocation.fromNamespaceAndPath("extended_workbench", "base_block_interaction_range");
   public static final ResourceLocation BASE_ENTITY_INTERACTION_RANGE_ID = ResourceLocation.fromNamespaceAndPath("extended_workbench", "base_entity_interaction_range");

   private EWHelper() {
   }

   @NotNull
   public static ItemAttributeModifiers createAllAttributes(float damage, float attackSpeed, float entityAndBlockInteractionRange) {
      return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)damage, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, (double)entityAndBlockInteractionRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, (double)entityAndBlockInteractionRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
   }

   @NotNull
   public static ItemAttributeModifiers createAllAttributes(Tier tier, float damage, float attackSpeed, float entityAndBlockInteractionRange) {
      return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(damage + tier.getAttackDamageBonus()), Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, (double)entityAndBlockInteractionRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, (double)entityAndBlockInteractionRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
   }

   @NotNull
   public static ItemAttributeModifiers createWeaponAttributes(Tier tier, int damage, float attackSpeed, float attackRange) {
      return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)((float)damage + tier.getAttackDamageBonus()), Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(BASE_ENTITY_INTERACTION_RANGE_ID, (double)attackRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
   }

   @NotNull
   public static ItemAttributeModifiers createDiggerAttributes(Tier tier, float damage, float attackSpeed, float blockInteractionRange) {
      return ItemAttributeModifiers.builder().add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (double)(damage + tier.getAttackDamageBonus()), Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, (double)attackSpeed, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BASE_BLOCK_INTERACTION_RANGE_ID, (double)blockInteractionRange, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build();
   }

   public static double getReplaceEquipmentChance() {
      try {
         return (Double)EWServerConfig.EXTENDED_EQUIPMENT_PROBABILITY.get();
      } catch (Exception var1) {
         return (Double)EWServerConfig.EXTENDED_EQUIPMENT_PROBABILITY.getDefault();
      }
   }

   public static double getReplaceNativeEquipmentChance() {
      try {
         return (Double)EWServerConfig.NATIVE_EXTENDED_EQUIPMENT_PROBABILITY.get();
      } catch (Exception var1) {
         return (Double)EWServerConfig.NATIVE_EXTENDED_EQUIPMENT_PROBABILITY.getDefault();
      }
   }

   public static Item tryToConvertExtendedVersion(Item originalItem) {
      ResourceLocation itemKey = BuiltInRegistries.ITEM.getKey(originalItem);
      if (itemKey == null) {
         return originalItem;
      } else {
         for (DeferredHolder<Item, ? extends Item> item1 : EWItems.getEntries()) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item1.get());
            if (id != null && id.getPath().equals("extended_" + itemKey.getPath())) {
               return item1.get();
            }
         }

         return originalItem;
      }
   }

   public static ItemStack tryToConvertExtendedVersion(ItemStack originalStack) {
      Item item = originalStack.getItem();
      Item extendedVersion = tryToConvertExtendedVersion(item);
      if (extendedVersion == item) {
         return originalStack;
      } else {
      Holder<Item> itemHolder = Holder.direct(extendedVersion);
         return itemHolder != null ? new ItemStack(itemHolder, originalStack.getCount(), originalStack.getComponentsPatch()) : originalStack;
      }
   }

   public static int directionToType(Direction direction) {
      switch(direction) {
      case NORTH:
         return 4;
      case EAST:
         return 1;
      case SOUTH:
         return 2;
      case WEST:
         return 3;
      default:
         throw new IllegalStateException("Invalid cardinal point from: extended_workbench");
      }
   }

   public static Direction typeToDirection(int type) {
      switch(type) {
      case 1:
         return Direction.EAST;
      case 2:
         return Direction.SOUTH;
      case 3:
         return Direction.WEST;
      case 4:
         return Direction.NORTH;
      default:
         return null;
      }
   }
}
