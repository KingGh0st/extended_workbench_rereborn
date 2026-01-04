package com.garouelcazador.extended_workbench.compat;

import com.garouelcazador.extended_workbench.recipe.ExtendedCraftingRecipe;
import com.garouelcazador.extended_workbench.screens.inventory.EWMenuTypes;
import com.garouelcazador.extended_workbench.screens.inventory.ExtendedCraftingMenu;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtendedRecipeTransferInfo implements IRecipeTransferInfo<ExtendedCraftingMenu, ExtendedCraftingRecipe> {

    @Override
    public Class<? extends ExtendedCraftingMenu> getContainerClass() {
        return ExtendedCraftingMenu.class;
    }

    @Override
    public Optional<MenuType<ExtendedCraftingMenu>> getMenuType() {
        return Optional.of(EWMenuTypes.EXTENDED_WORKBENCH_MENU.get());
    }

    @Override
    public RecipeType<ExtendedCraftingRecipe> getRecipeType() {
        return ExtendedCraftingCategory.TYPE;
    }

    @Override
    public boolean canHandle(ExtendedCraftingMenu container, ExtendedCraftingRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ExtendedCraftingMenu container, ExtendedCraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        // Slots 1 - 18: Crafting grid
        for (int i = 1; i <= 18; i++) {
            slots.add(container.getSlot(i));
        }
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ExtendedCraftingMenu container, ExtendedCraftingRecipe recipe) {
        List<Slot> slots = new ArrayList<>();
        // Slots 19 - 54: Player inventory
        for (int i = 19; i <= 54; i++) {
            slots.add(container.getSlot(i));
        }
        return slots;
    }
}