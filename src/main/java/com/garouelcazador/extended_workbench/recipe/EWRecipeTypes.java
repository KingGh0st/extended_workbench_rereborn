package com.garouelcazador.extended_workbench.recipe;

import com.garouelcazador.extended_workbench.ExtendedWorkbench;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EWRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = 
        DeferredRegister.create(Registries.RECIPE_TYPE, ExtendedWorkbench.MOD_ID);
        
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = 
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, ExtendedWorkbench.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ExtendedCraftingRecipe>> EXTENDED_CRAFTING_TYPE = 
        RECIPE_TYPES.register("extended_crafting", () -> new RecipeType<ExtendedCraftingRecipe>() {
            @Override
            public String toString() {
                return "extended_workbench:extended_crafting";
            }
        });

    public static final DeferredHolder<RecipeSerializer<?>, ExtendedCraftingRecipe.Serializer> EXTENDED_CRAFTING_SERIALIZER = 
        RECIPE_SERIALIZERS.register("extended_crafting", ExtendedCraftingRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}