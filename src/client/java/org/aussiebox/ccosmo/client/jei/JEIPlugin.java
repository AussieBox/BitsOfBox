package org.aussiebox.ccosmo.client.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.aussiebox.ccosmo.CCOSMO;
import org.aussiebox.ccosmo.block.ModBlocks;
import org.aussiebox.ccosmo.item.ModItems;
import org.aussiebox.ccosmo.recipe.ModRecipes;
import org.aussiebox.ccosmo.recipe.ShimmeringRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JEIPlugin implements IModPlugin {
    public static RecipeType<ShimmeringRecipe> SHIMMERING_TYPE = RecipeType.create(CCOSMO.MOD_ID, "shimmering", ShimmeringRecipe.class);

    @Override
    public @NotNull Identifier getPluginUid() {
        return CCOSMO.id("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ShimmeringIRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (MinecraftClient.getInstance().world != null) {
            List<ShimmeringRecipe> recipes = MinecraftClient.getInstance().world.getRecipeManager().listAllOfType(ModRecipes.SHIMMERING_TYPE).stream().map(RecipeEntry::value).toList();
            registration.addRecipes(SHIMMERING_TYPE, recipes);
        }

        registration.addIngredientInfo(
                new ItemStack(ModItems.DRAGONFLAME_CACTUS),
                VanillaTypes.ITEM_STACK,
                Text.translatable("item.ccosmo.dragonflame_cactus.jei.description")
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SHIMMERING_ALTAR), SHIMMERING_TYPE);
    }
}
