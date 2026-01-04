package com.garouelcazador.extended_workbench.compat;

import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import com.garouelcazador.extended_workbench.recipe.EWRecipeTypes;
import com.garouelcazador.extended_workbench.recipe.ExtendedCraftingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

@JeiPlugin
public class JEIExtendedWorkbenchPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ExtendedWorkbench.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ExtendedCraftingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Minecraft.getInstance().level.getRecipeManager();

        List<ExtendedCraftingRecipe> recipes = rm.getAllRecipesFor(EWRecipeTypes.EXTENDED_CRAFTING_TYPE.get())
                .stream()
                .map(RecipeHolder::value)
                .filter(r -> r.getIngredients() != null && !r.getIngredients().isEmpty()) // Security filter
                .toList();

        registration.addRecipes(ExtendedCraftingCategory.TYPE, recipes);
    }

    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.CRAFTING_TABLE), ExtendedCraftingCategory.TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(mezz.jei.api.registration.IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new ExtendedRecipeTransferInfo());
    }
}