package com.garouelcazador.extended_workbench.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import com.mojang.serialization.Codec;

public class ExtendedCraftingRecipe implements Recipe<CraftingInput> {
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;

    public ExtendedCraftingRecipe(ItemStack result, NonNullList<Ingredient> ingredients) {
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.size() != 18) return false;
        for (int i = 0; i < 18; i++) {
            if (!ingredients.get(i).test(input.getItem(i))) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width == 3 && height == 6;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return EWRecipeTypes.EXTENDED_CRAFTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return EWRecipeTypes.EXTENDED_CRAFTING_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public static class Serializer implements RecipeSerializer<ExtendedCraftingRecipe> {
        public static final MapCodec<ExtendedCraftingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                Codec.STRING.listOf().fieldOf("pattern").forGetter(r -> null), 
                Codec.unboundedMap(Codec.STRING, Ingredient.CODEC).fieldOf("key").forGetter(r -> null) 
        ).apply(inst, (result, pattern, key) -> {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(18, Ingredient.EMPTY);
            
            for (int row = 0; row < pattern.size() && row < 6; row++) {
                String line = pattern.get(row);
                for (int col = 0; col < line.length() && col < 3; col++) {
                    String symbol = String.valueOf(line.charAt(col));
                    if (!symbol.equals(" ")) {
                        Ingredient ing = key.get(symbol);
                        if (ing != null) {
                            ingredients.set(col + (row * 3), ing);
                        }
                    }
                }
            }
            return new ExtendedCraftingRecipe(result, ingredients);
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedCraftingRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, r) -> {
                    ItemStack.STREAM_CODEC.encode(buf, r.result);
                    buf.writeInt(r.ingredients.size());
                    for (Ingredient ing : r.ingredients) Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ing);
                },
                buf -> {
                    ItemStack res = ItemStack.STREAM_CODEC.decode(buf);
                    int size = buf.readInt();
                    NonNullList<Ingredient> ings = NonNullList.withSize(size, Ingredient.EMPTY);
                    for (int i = 0; i < size; i++) ings.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    return new ExtendedCraftingRecipe(res, ings);
                }
        );

        @Override public MapCodec<ExtendedCraftingRecipe> codec() { return CODEC; }
        @Override public StreamCodec<RegistryFriendlyByteBuf, ExtendedCraftingRecipe> streamCodec() { return STREAM_CODEC; }
    }
}