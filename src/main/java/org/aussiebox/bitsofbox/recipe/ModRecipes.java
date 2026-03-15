package org.aussiebox.bitsofbox.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.recipe.serializer.ShimmeringRecipeSerializer;

public class ModRecipes {
    public static final RecipeType<ShimmeringRecipe> SHIMMERING_TYPE = Registry.register(
            Registries.RECIPE_TYPE,
            BOB.id("shimmering"),
            new RecipeType<ShimmeringRecipe>() {
                @Override
                public String toString() { return "shimmering"; }
            }
    );

    public static final RecipeSerializer<ShimmeringRecipe> SHIMMERING_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER,
            BOB.id("shimmering"),
            new ShimmeringRecipeSerializer()
    );

    public static void init() {
        BOB.LOGGER.info("Registering Recipes for " + BOB.MOD_ID);
    }
}
