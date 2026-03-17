package org.aussiebox.ccosmo.client.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.apache.commons.lang3.mutable.MutableInt;
import org.aussiebox.ccosmo.CCOSMO;
import org.aussiebox.ccosmo.block.ModBlocks;
import org.aussiebox.ccosmo.recipe.ShimmeringRecipe;
import org.aussiebox.ccosmo.util.CCOSMOUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ShimmeringIRecipeCategory implements IRecipeCategory<ShimmeringRecipe> {
    private final IDrawable icon;
    private final IDrawable background;
    private final IDrawable slotBackground;
    private final List<Vector2f> inputPositions;

    public ShimmeringIRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableItemStack(new ItemStack(ModBlocks.SHIMMERING_ALTAR));
        background = guiHelper.createDrawable(CCOSMO.id("textures/gui/jei/shimmering.png"), 0, 0, 116, 56);
        slotBackground = guiHelper.createDrawable(CCOSMO.id("textures/gui/jei/shimmering.png"), 3, 2, 18, 18);

        inputPositions = new ArrayList<>();
        inputPositions.add(new Vector2f(4, 3));
        inputPositions.add(new Vector2f(22, 3));
        inputPositions.add(new Vector2f(4, 21));
        inputPositions.add(new Vector2f(22, 21));
        inputPositions.add(new Vector2f(4, 39));
        inputPositions.add(new Vector2f(22, 39));
    }

    @Override
    public @NotNull RecipeType<ShimmeringRecipe> getRecipeType() {
        return JEIPlugin.SHIMMERING_TYPE;
    }

    @Override
    public @NotNull Text getTitle() {
        return Text.translatable("jei.category.ccosmo.shimmering");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShimmeringRecipe recipe, IFocusGroup iFocusGroup) {
        List<Pair<Ingredient, MutableInt>> ingredients = CCOSMOUtil.condenseIngredients(recipe.getIngredients());
        int iterations = Math.min(6, ingredients.size());

        for (int i = 0; i < iterations; i++) {
            Pair<Ingredient, MutableInt> pair = ingredients.get(i);

            List<ItemStack> stacks = new ArrayList<>();
            for (ItemStack itemStack : pair.getLeft().getMatchingStacks()) {
                ItemStack copy = itemStack.copy();
                copy.setCount(pair.getRight().getValue());
                stacks.add(copy);
            }

            builder.addSlot(RecipeIngredientRole.INPUT, (int) inputPositions.get(i).getX(), (int) inputPositions.get(i).getY())
                    .setBackground(slotBackground, -1, -1)
                    .addItemStacks(stacks);
        }

        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack itemStack : recipe.getAffectedIngredient().getMatchingStacks()) {
            ItemStack copy = itemStack.copy();
            stacks.add(copy);
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 59, 3)
                .addItemStacks(stacks);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 3)
                .addItemStack(recipe.getOutput());
    }

    @Override
    public int getWidth() {
        return 116;
    }

    @Override
    public int getHeight() {
        return 56;
    }

    @Override
    public void draw(ShimmeringRecipe recipe, IRecipeSlotsView recipeSlotsView, DrawContext context, double mouseX, double mouseY) {
        background.draw(context);
        if (mouseX >= 50 && mouseX <= 83 && mouseY >= 28 && mouseY <= 53) {
            context.drawTooltip(
                    MinecraftClient.getInstance().textRenderer,
                    Text.translatable("jei.tip.ccosmo.border_proximity", recipe.getBorderProximity()).withColor(0x55FFFF),
                    (int) mouseX,
                    (int) mouseY
            );
        }
    }
}
