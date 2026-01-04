package com.garouelcazador.extended_workbench.compat;

import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import com.garouelcazador.extended_workbench.recipe.ExtendedCraftingRecipe;
import com.garouelcazador.extended_workbench.screens.inventory.ExtendedCraftingScreen;
import net.minecraft.world.level.block.Blocks; // Para usar la mesa original
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import mezz.jei.api.gui.drawable.IDrawableStatic;

public class ExtendedCraftingCategory implements IRecipeCategory<ExtendedCraftingRecipe> {
    public static final RecipeType<ExtendedCraftingRecipe> TYPE = 
        RecipeType.create(ExtendedWorkbench.MOD_ID, "extended_crafting", ExtendedCraftingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IGuiHelper guiHelper;

    public ExtendedCraftingCategory(IGuiHelper helper) {
        this.guiHelper = helper;
        this.background = helper.createBlankDrawable(176, 130);
        
        ItemStack iconStack = new ItemStack(Blocks.CRAFTING_TABLE);
        iconStack.set(net.minecraft.core.component.DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, iconStack);
    }

    @Override
    public RecipeType<ExtendedCraftingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public void draw(ExtendedCraftingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(ExtendedCraftingScreen.CRAFTING_TABLE_LOCATION, 90, 55, 87, 62, 27, 23);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei." + ExtendedWorkbench.MOD_ID + ".category");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ExtendedCraftingRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredients();
        if (ingredients == null || ingredients.isEmpty()) return;
        IDrawable staticSlot = guiHelper.getSlotDrawable();

        for (int row = 0; row < 6; ++row) {
            for (int col = 0; col < 3; ++col) {
                int index = col + (row * 3);
                
                var slot = builder.addSlot(RecipeIngredientRole.INPUT, 30 + col * 18, 10 + row * 18)
                    .setBackground(staticSlot, -1, -1);
                if (index < ingredients.size()) {
                    slot.addIngredients(ingredients.get(index));
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 55)
            .setBackground(staticSlot, -1, -1)
            .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()));
    }
}