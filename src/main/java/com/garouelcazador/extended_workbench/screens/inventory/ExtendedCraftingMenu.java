package com.garouelcazador.extended_workbench.screens.inventory;

import com.garouelcazador.extended_workbench.recipe.EWRecipeTypes;
import com.garouelcazador.extended_workbench.recipe.ExtendedCraftingRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

import net.minecraft.world.entity.player.StackedContents;

public class ExtendedCraftingMenu extends RecipeBookMenu<CraftingInput, Recipe<CraftingInput>> {
   private final CraftingContainer craftSlots;
   private final ResultContainer resultSlots;
   private final ContainerLevelAccess access;
   private final Player player;

   public ExtendedCraftingMenu(int containerId, Inventory playerInventory, net.minecraft.network.RegistryFriendlyByteBuf buf) {
      this(containerId, playerInventory, ContainerLevelAccess.NULL);
   }

   public ExtendedCraftingMenu(int containerID, Inventory inventory, ContainerLevelAccess access) {
      super(EWMenuTypes.EXTENDED_WORKBENCH_MENU.get(), containerID);
      this.craftSlots = new TransientCraftingContainer(this, 3, 6);
      this.resultSlots = new ResultContainer();
      this.access = access;
      this.player = inventory.player;

      this.addSlot(new ExtendedResultSlot(inventory.player, this.craftSlots, this.resultSlots, 0, 124, 63));

      for (int row = 0; row < 6; ++row) {
         for (int col = 0; col < 3; ++col) {
            this.addSlot(new Slot(this.craftSlots, col + row * 3, 30 + col * 18, 17 + row * 18));
         }
      }

      int invYStart = 138; 
      for (int row = 0; row < 3; ++row) {
         for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, invYStart + row * 18));
         }
      }

      // Hotbar
      for (int col = 0; col < 9; ++col) {
         this.addSlot(new Slot(inventory, col, 8 + col * 18, 196));
      }
   }

   @Override
   public void slotsChanged(Container container) {
      this.access.execute((level, pos) -> {
         if (!level.isClientSide) {
            CraftingInput input = this.craftSlots.asCraftInput();

            Optional<RecipeHolder<ExtendedCraftingRecipe>> extended = level.getServer().getRecipeManager()
                  .getRecipeFor(EWRecipeTypes.EXTENDED_CRAFTING_TYPE.get(), input, level);

            if (extended.isPresent()) {
               this.resultSlots.setItem(0, extended.get().value().assemble(input, level.registryAccess()));
            } else {
               Optional<RecipeHolder<CraftingRecipe>> vanilla = level.getServer().getRecipeManager()
                     .getRecipeFor(RecipeType.CRAFTING, input, level);
               
               if (vanilla.isPresent()) {
                  this.resultSlots.setItem(0, vanilla.get().value().assemble(input, level.registryAccess()));
               } else {
                  this.resultSlots.setItem(0, ItemStack.EMPTY);
               }
            }
            this.broadcastChanges();
         }
      });
   }

   @Override
   public void clicked(int slotId, int button, ClickType clickType, Player player) {
      super.clicked(slotId, button, clickType, player);
      this.slotsChanged(this.craftSlots);
}

   @Override
   public boolean stillValid(Player player) {
      return true; // Testing
   }

   @Override
   public @NotNull ItemStack quickMoveStack(Player player, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         
         if (index == 0) { // Result to inv
            if (!this.moveItemStackTo(itemstack1, 19, 55, true)) {
               return ItemStack.EMPTY;
            }
            slot.onQuickCraft(itemstack1, itemstack);
         } else if (index >= 19 && index < 55) { // From inventory to crafting table
            if (!this.moveItemStackTo(itemstack1, 1, 19, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 19, 55, false)) { // From crafting table to inventory
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, itemstack1);
      }
      return itemstack;
   }

   @Override
   public void fillCraftSlotsStackedContents(StackedContents contents) {
      this.craftSlots.fillStackedContents(contents);
   }

   @Override
   public void clearCraftingContent() {
      this.craftSlots.clearContent();
      this.resultSlots.clearContent();
   }

   @Override
   public void removed(Player player) {
      super.removed(player);
      // Interface closing
      this.access.execute((level, pos) -> {
         this.clearContainer(player, this.craftSlots);
      });
   }

   @Override
   public boolean recipeMatches(RecipeHolder<Recipe<CraftingInput>> recipe) {
      return recipe.value().matches(this.craftSlots.asCraftInput(), this.player.level());
   }

   @Override
   public int getResultSlotIndex() { return 0; }

   @Override
   public int getGridWidth() { return 3; }

   @Override
   public int getGridHeight() { return 6; }

   @Override
   public int getSize() { return 19; }

   @Override
   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   @Override
   public boolean shouldMoveToInventory(int slotIndex) {
      return slotIndex != this.getResultSlotIndex();
   }
}