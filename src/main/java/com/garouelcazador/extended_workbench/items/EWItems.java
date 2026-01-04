package com.garouelcazador.extended_workbench.items;

import com.garouelcazador.extended_workbench.items.custom.ExtendedBowItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedCrossbowItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedGoldenArmorItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedHoeItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedLeatherBoots;
import com.garouelcazador.extended_workbench.items.custom.ExtendedShieldItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedSwordItem;
import com.garouelcazador.extended_workbench.items.custom.ExtendedTridentItem;
import com.garouelcazador.extended_workbench.misc.EWArmorMaterials;
import com.garouelcazador.extended_workbench.misc.EWHelper;
import com.garouelcazador.extended_workbench.misc.EWTiers;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.Tool;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder; 

public final class EWItems {
   public static final DeferredRegister.Items ITEMS;
   public static final DeferredItem<Item> EXTENDED_WOODEN_SWORD;
   public static final DeferredItem<Item> EXTENDED_WOODEN_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_WOODEN_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_WOODEN_AXE;
   public static final DeferredItem<Item> EXTENDED_WOODEN_HOE;
   public static final DeferredItem<Item> EXTENDED_STONE_SWORD;
   public static final DeferredItem<Item> EXTENDED_STONE_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_STONE_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_STONE_AXE;
   public static final DeferredItem<Item> EXTENDED_STONE_HOE;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_SWORD;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_AXE;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_HOE;
   public static final DeferredItem<Item> EXTENDED_IRON_SWORD;
   public static final DeferredItem<Item> EXTENDED_IRON_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_IRON_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_IRON_AXE;
   public static final DeferredItem<Item> EXTENDED_IRON_HOE;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_SWORD;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_AXE;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_HOE;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_SWORD;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_SHOVEL;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_PICKAXE;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_AXE;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_HOE;
   public static final DeferredItem<Item> EXTENDED_LEATHER_HELMET;
   public static final DeferredItem<Item> EXTENDED_LEATHER_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_LEATHER_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_LEATHER_BOOTS;
   public static final DeferredItem<Item> EXTENDED_CHAINMAIL_HELMET;
   public static final DeferredItem<Item> EXTENDED_CHAINMAIL_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_CHAINMAIL_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_CHAINMAIL_BOOTS;
   public static final DeferredItem<Item> EXTENDED_IRON_HELMET;
   public static final DeferredItem<Item> EXTENDED_IRON_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_IRON_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_IRON_BOOTS;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_HELMET;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_DIAMOND_BOOTS;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_HELMET;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_GOLDEN_BOOTS;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_HELMET;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_CHESTPLATE;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_LEGGINGS;
   public static final DeferredItem<Item> EXTENDED_NETHERITE_BOOTS;
   public static final DeferredItem<Item> EXTENDED_BOW;
   public static final DeferredItem<Item> EXTENDED_CROSSBOW;
   public static final DeferredItem<Item> EXTENDED_SHIELD;
   public static final DeferredItem<Item> EXTENDED_TRIDENT;
   public static final DeferredItem<Item> EXTENDED_MACE;

   private EWItems() {
   }

   public static void register(IEventBus eventBus) {
      ITEMS.register(eventBus);
   }

   public static Collection<DeferredHolder<Item, ? extends Item>> getEntries() {
      return ITEMS.getEntries();
   }

   static {
      ITEMS = DeferredRegister.createItems("extended_workbench");
      EXTENDED_WOODEN_SWORD = ITEMS.register("extended_wooden_sword", () -> {
         return new ExtendedSwordItem(EWTiers.WOOD, (new Properties()).attributes(EWHelper.createWeaponAttributes(EWTiers.WOOD, 4, -2.7F, 2.5F)));
      });
      EXTENDED_WOODEN_SHOVEL = ITEMS.register("extended_wooden_shovel", () -> {
         return new ShovelItem(EWTiers.WOOD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.WOOD, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_WOODEN_PICKAXE = ITEMS.register("extended_wooden_pickaxe", () -> {
         return new PickaxeItem(EWTiers.WOOD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.WOOD, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_WOODEN_AXE = ITEMS.register("extended_wooden_axe", () -> {
         return new AxeItem(EWTiers.WOOD, (new Properties()).attributes(EWHelper.createAllAttributes(EWTiers.WOOD, 7.0F, -3.3F, 2.5F)));
      });
      EXTENDED_WOODEN_HOE = ITEMS.register("extended_wooden_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.WOOD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.WOOD, 0.0F, 0.0F, 2.5F)));
      });
      EXTENDED_STONE_SWORD = ITEMS.register("extended_stone_sword", () -> {
         return new ExtendedSwordItem(EWTiers.STONE, (new Properties()).attributes(EWHelper.createWeaponAttributes(EWTiers.STONE, 4, -2.7F, 2.5F)));
      });
      EXTENDED_STONE_SHOVEL = ITEMS.register("extended_stone_shovel", () -> {
         return new ShovelItem(EWTiers.STONE, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.STONE, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_STONE_PICKAXE = ITEMS.register("extended_stone_pickaxe", () -> {
         return new PickaxeItem(EWTiers.STONE, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.STONE, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_STONE_AXE = ITEMS.register("extended_stone_axe", () -> {
         return new AxeItem(EWTiers.STONE, (new Properties()).attributes(EWHelper.createAllAttributes(EWTiers.STONE, 8.0F, -3.3F, 2.5F)));
      });
      EXTENDED_STONE_HOE = ITEMS.register("extended_stone_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.STONE, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.STONE, -1.0F, 0.0F, 2.5F)));
      });
      EXTENDED_GOLDEN_SWORD = ITEMS.register("extended_golden_sword", () -> {
         return new ExtendedSwordItem(EWTiers.GOLD, (new Properties()).attributes(EWHelper.createWeaponAttributes(EWTiers.GOLD, 4, -2.7F, 2.5F)));
      });
      EXTENDED_GOLDEN_SHOVEL = ITEMS.register("extended_golden_shovel", () -> {
         return new ShovelItem(EWTiers.GOLD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.GOLD, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_GOLDEN_PICKAXE = ITEMS.register("extended_golden_pickaxe", () -> {
         return new PickaxeItem(EWTiers.GOLD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.GOLD, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_GOLDEN_AXE = ITEMS.register("extended_golden_axe", () -> {
         return new AxeItem(EWTiers.GOLD, (new Properties()).attributes(EWHelper.createAllAttributes(EWTiers.GOLD, 7.0F, -3.3F, 2.5F)));
      });
      EXTENDED_GOLDEN_HOE = ITEMS.register("extended_golden_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.GOLD, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.GOLD, 0.0F, 0.0F, 2.5F)));
      });
      EXTENDED_IRON_SWORD = ITEMS.register("extended_iron_sword", () -> {
         return new ExtendedSwordItem(EWTiers.IRON, (new Properties()).attributes(EWHelper.createWeaponAttributes(EWTiers.IRON, 4, -2.6F, 2.5F)));
      });
      EXTENDED_IRON_SHOVEL = ITEMS.register("extended_iron_shovel", () -> {
         return new ShovelItem(EWTiers.IRON, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.IRON, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_IRON_PICKAXE = ITEMS.register("extended_iron_pickaxe", () -> {
         return new PickaxeItem(EWTiers.IRON, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.IRON, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_IRON_AXE = ITEMS.register("extended_iron_axe", () -> {
         return new AxeItem(EWTiers.IRON, (new Properties()).attributes(EWHelper.createAllAttributes(EWTiers.IRON, 7.0F, -3.2F, 2.5F)));
      });
      EXTENDED_IRON_HOE = ITEMS.register("extended_iron_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.IRON, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.IRON, -2.0F, 0.0F, 2.5F)));
      });
      EXTENDED_DIAMOND_SWORD = ITEMS.register("extended_diamond_sword", () -> {
         return new ExtendedSwordItem(EWTiers.DIAMOND, (new Properties()).attributes(EWHelper.createWeaponAttributes(EWTiers.DIAMOND, 4, -2.6F, 2.5F)));
      });
      EXTENDED_DIAMOND_SHOVEL = ITEMS.register("extended_diamond_shovel", () -> {
         return new ShovelItem(EWTiers.DIAMOND, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.DIAMOND, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_DIAMOND_PICKAXE = ITEMS.register("extended_diamond_pickaxe", () -> {
         return new PickaxeItem(EWTiers.DIAMOND, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.DIAMOND, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_DIAMOND_AXE = ITEMS.register("extended_diamond_axe", () -> {
         return new AxeItem(EWTiers.DIAMOND, (new Properties()).attributes(EWHelper.createAllAttributes(EWTiers.DIAMOND, 6.0F, -3.1F, 2.5F)));
      });
      EXTENDED_DIAMOND_HOE = ITEMS.register("extended_diamond_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.DIAMOND, (new Properties()).attributes(EWHelper.createDiggerAttributes(EWTiers.DIAMOND, -3.0F, 0.0F, 2.5F)));
      });
      EXTENDED_NETHERITE_SWORD = ITEMS.register("extended_netherite_sword", () -> {
         return new ExtendedSwordItem(EWTiers.NETHERITE, (new Properties()).fireResistant().attributes(EWHelper.createWeaponAttributes(EWTiers.NETHERITE, 4, -2.6F, 2.5F)));
      });
      EXTENDED_NETHERITE_SHOVEL = ITEMS.register("extended_netherite_shovel", () -> {
         return new ShovelItem(EWTiers.NETHERITE, (new Properties()).fireResistant().attributes(EWHelper.createDiggerAttributes(EWTiers.NETHERITE, 1.5F, -3.1F, 2.5F)));
      });
      EXTENDED_NETHERITE_PICKAXE = ITEMS.register("extended_netherite_pickaxe", () -> {
         return new PickaxeItem(EWTiers.NETHERITE, (new Properties()).fireResistant().attributes(EWHelper.createDiggerAttributes(EWTiers.NETHERITE, 1.0F, -3.0F, 2.5F)));
      });
      EXTENDED_NETHERITE_AXE = ITEMS.register("extended_netherite_axe", () -> {
         return new AxeItem(EWTiers.NETHERITE, (new Properties()).fireResistant().attributes(EWHelper.createAllAttributes(EWTiers.NETHERITE, 6.0F, -3.1F, 2.5F)));
      });
      EXTENDED_NETHERITE_HOE = ITEMS.register("extended_netherite_hoe", () -> {
         return new ExtendedHoeItem(EWTiers.NETHERITE, (new Properties()).fireResistant().attributes(EWHelper.createDiggerAttributes(EWTiers.NETHERITE, -4.0F, 0.0F, 2.5F)));
      });
      EXTENDED_LEATHER_HELMET = ITEMS.register("extended_leather_helmet", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_LEATHER.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(6)));
      });
      EXTENDED_LEATHER_CHESTPLATE = ITEMS.register("extended_leather_chestplate", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_LEATHER.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(6)));
      });
      EXTENDED_LEATHER_LEGGINGS = ITEMS.register("extended_leather_leggings", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_LEATHER.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(6)));
      });
      EXTENDED_LEATHER_BOOTS = ITEMS.register("extended_leather_boots", () -> {
         return new ExtendedLeatherBoots(Holder.direct(EWArmorMaterials.EXTENDED_LEATHER.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(6)));
      });
      EXTENDED_CHAINMAIL_HELMET = ITEMS.register("extended_chainmail_helmet", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_CHAIN.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(19)));
      });
      EXTENDED_CHAINMAIL_CHESTPLATE = ITEMS.register("extended_chainmail_chestplate", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_CHAIN.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(19)));
      });
      EXTENDED_CHAINMAIL_LEGGINGS = ITEMS.register("extended_chainmail_leggings", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_CHAIN.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(19)));
      });
      EXTENDED_CHAINMAIL_BOOTS = ITEMS.register("extended_chainmail_boots", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_CHAIN.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(19)));
      });
      EXTENDED_IRON_HELMET = ITEMS.register("extended_iron_helmet", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_IRON.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(19)));
      });
      EXTENDED_IRON_CHESTPLATE = ITEMS.register("extended_iron_chestplate", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_IRON.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(19)));
      });
      EXTENDED_IRON_LEGGINGS = ITEMS.register("extended_iron_leggings", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_IRON.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(19)));
      });
      EXTENDED_IRON_BOOTS = ITEMS.register("extended_iron_boots", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_IRON.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(19)));
      });
      EXTENDED_DIAMOND_HELMET = ITEMS.register("extended_diamond_helmet", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_DIAMOND.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(41)));
      });
      EXTENDED_DIAMOND_CHESTPLATE = ITEMS.register("extended_diamond_chestplate", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_DIAMOND.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(41)));
      });
      EXTENDED_DIAMOND_LEGGINGS = ITEMS.register("extended_diamond_leggings", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_DIAMOND.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(41)));
      });
      EXTENDED_DIAMOND_BOOTS = ITEMS.register("extended_diamond_boots", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_DIAMOND.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(41)));
      });
      EXTENDED_GOLDEN_HELMET = ITEMS.register("extended_golden_helmet", () -> {
         return new ExtendedGoldenArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_GOLD.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(9)));
      });
      EXTENDED_GOLDEN_CHESTPLATE = ITEMS.register("extended_golden_chestplate", () -> {
         return new ExtendedGoldenArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_GOLD.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(9)));
      });
      EXTENDED_GOLDEN_LEGGINGS = ITEMS.register("extended_golden_leggings", () -> {
         return new ExtendedGoldenArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_GOLD.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(9)));
      });
      EXTENDED_GOLDEN_BOOTS = ITEMS.register("extended_golden_boots", () -> {
         return new ExtendedGoldenArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_GOLD.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(9)));
      });
      EXTENDED_NETHERITE_HELMET = ITEMS.register("extended_netherite_helmet", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_NETHERITE.get()), Type.HELMET, (new Properties()).durability(Type.HELMET.getDurability(46)).fireResistant());
      });
      EXTENDED_NETHERITE_CHESTPLATE = ITEMS.register("extended_netherite_chestplate", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_NETHERITE.get()), Type.CHESTPLATE, (new Properties()).durability(Type.CHESTPLATE.getDurability(46)).fireResistant());
      });
      EXTENDED_NETHERITE_LEGGINGS = ITEMS.register("extended_netherite_leggings", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_NETHERITE.get()), Type.LEGGINGS, (new Properties()).durability(Type.LEGGINGS.getDurability(46)).fireResistant());
      });
      EXTENDED_NETHERITE_BOOTS = ITEMS.register("extended_netherite_boots", () -> {
         return new ArmorItem(Holder.direct(EWArmorMaterials.EXTENDED_NETHERITE.get()), Type.BOOTS, (new Properties()).durability(Type.BOOTS.getDurability(46)).fireResistant());
      });
      EXTENDED_BOW = ITEMS.register("extended_bow", () -> {
         return new ExtendedBowItem((new Properties()).durability(576));
      });
      EXTENDED_CROSSBOW = ITEMS.register("extended_crossbow", () -> {
         return new ExtendedCrossbowItem((new Properties()).durability(698));
      });
      EXTENDED_SHIELD = ITEMS.register("extended_shield", () -> {
         return new ExtendedShieldItem((new Properties()).durability(504));
      });
      EXTENDED_TRIDENT = ITEMS.register("extended_trident", () -> {
         return new ExtendedTridentItem((new Properties()).rarity(Rarity.EPIC).durability(375).attributes(EWHelper.createAllAttributes(9.0F, -3.0F, 1.5F)).component(DataComponents.TOOL, new Tool(List.of(), 1.5F, 3)));
      });
      EXTENDED_MACE = ITEMS.register("extended_mace", () -> {
         return new MaceItem((new Properties()).rarity(Rarity.EPIC).durability(750).component(DataComponents.TOOL, new Tool(List.of(), 1.5F, 3)).attributes(EWHelper.createAllAttributes(5.5F, -3.4F, 1.5F)));
      });
   }
}
