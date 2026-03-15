package org.aussiebox.bitsofbox.recipe;

import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.recipe.inventory.ShimmeringTableInventory;

public class ShimmeringRecipe implements Recipe<ShimmeringTableInventory> {
    @Getter
    private final Ingredient affectedIngredient;
    private final DefaultedList<Ingredient> ingredients;
    @Getter private final ItemStack output;

    public ShimmeringRecipe(DefaultedList<Ingredient> ingredients,Ingredient affectedStack, ItemStack output) {
        this.affectedIngredient = affectedStack;
        this.ingredients = ingredients;
        this.output = output;
    }

    @Override
    public boolean matches(ShimmeringTableInventory input, World world) {
        if (!affectedIngredient.test(input.getAffectedStack())) return false;
        return input.getRecipeMatcher().match(this, null);
    }

    @Override
    public ItemStack craft(ShimmeringTableInventory input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output.copy();
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(ingredients);
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SHIMMERING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.SHIMMERING_TYPE;
    }
}
